package com.sapient.rulesengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.model.AssignRules;
import com.sapient.model.ContestBean;
import com.sapient.model.HackerDataFetch;
import com.sapient.model.LeaderBoardBean;
import com.sapient.model.RulesBean;
import com.sapient.model.UdemyDataFetch;

@Service
public class RulesEngine {

	public List<LeaderBoardBean> masterLeader = new ArrayList<LeaderBoardBean>();
	public LinkedHashMap<String,List<Double>> masterUdemy = new LinkedHashMap<>();

	@Autowired
	RulesServiceProxy rulesServiceProxy;

	public List<LeaderBoardBean> iterateOverActivities(List<JSONObject> activityData) {
 
		ListIterator<JSONObject> iterator =  activityData.listIterator();
		while(iterator.hasNext()) {
			JSONObject activity = iterator.next();
			String activityName = (String) activity.get("name");
			if(activity.get("category").equals("Learning"))
			{
				consolidateUdemyProgress(activity);
			}
			else 
			{

			computationOfScores(activityName,activity);
			}
			
		}

		return masterLeader;
	}
	
	public void computationOfScores(String activityName,JSONObject activityData) {

		
		ObjectMapper mapper = new ObjectMapper();
		List<AssignRules> assignedRules = mapper.convertValue(activityData.get("assessmentRules"),new TypeReference<List<AssignRules>>() { });

		List<HackerDataFetch> data = mapper.convertValue(activityData.get("progressDataHackerrank"),new TypeReference<List<HackerDataFetch>>() { });



		Collections.sort(data,new SortByDateAndTime());

		List<RulesBean> finalRules = filterRules(assignedRules);

		computeFinalScores(activityName,finalRules, data);
	}

	public List<RulesBean> filterRules(List<AssignRules> assignedRules){

		List<RulesBean> finalRules = new ArrayList<RulesBean>();
		List<RulesBean> rules = rulesServiceProxy.getAllRules();


		for(int i=0;i<assignedRules.size();i++) {

			AssignRules assrule = assignedRules.get(i);
			System.out.println(assrule);
			if(assrule.isStatus()) {
				System.out.println("inside");
				for(int j=0; j<rules.size();j++){
					if(assrule.getName().equals(rules.get(j).getKey())) 
					{
						System.out.println("Equal");
						finalRules.add(rules.get(j));

					}
				}
			}
		}
		System.out.println(finalRules);
		return finalRules; 
	}

	public void computeFinalScores(String activityName,List<RulesBean> rules,List<HackerDataFetch> data){

		Integer rankBasedOnCompletionTime=-1;

		for(HackerDataFetch userData:data){
			Integer userScore = userData.getScore();
			rankBasedOnCompletionTime+=1;

			for(RulesBean rule:rules){
				if(rule.getCategory().equals("Score")){

					if ((userData.getScore()/userData.getActivityScore())*100 >=rule.getOperand1() && (userData.getScore()/userData.getActivityScore())*100<=rule.getOperand2()) {
						userScore += rule.getScore();
					}
					continue;

				}


				if(rule.getCategory().equals("completed_on")) {

					if(rankBasedOnCompletionTime>=rule.getOperand1() && rankBasedOnCompletionTime<rule.getOperand2()) {

						userScore += rule.getScore();

					}
				}
				continue;
			}

			MapToLeaderBoard(activityName, userData,userScore);
		}
		
	}

	public Integer MapToLeaderBoard(String activityName, HackerDataFetch userData,Integer userScore) {

		int i=0;
		int size=masterLeader.size();
		for(i = 0; i < size; i++)
		{
			//LeaderBoardBean tempBean = masterLeader.get(i);

			if(masterLeader.get(i).getEmailid().equals(userData.getEmailId()))
			{
				ContestBean tempContestBean = new ContestBean();
				tempContestBean.setContestname(activityName);
				tempContestBean.setContestscore(userScore);			
				masterLeader.get(i).getContestdetails().add(tempContestBean);
				masterLeader.get(i).setTotalscore(userScore+masterLeader.get(i).getTotalscore());
				return null;	
			}
		}
		//if not found enter new Bean
		ContestBean tempContestBean = new ContestBean();
		tempContestBean.setContestname(activityName);
		tempContestBean.setContestscore(userScore);

		LeaderBoardBean tempLeaderBean = new LeaderBoardBean();
		tempLeaderBean.setEmailid(userData.getEmailId());
		tempLeaderBean.setName(userData.getName());
		tempLeaderBean.setTotalscore(userScore+tempLeaderBean.getTotalscore());

		List<ContestBean> inputList = tempLeaderBean.getContestdetails();
		inputList.add(tempContestBean);
		tempLeaderBean.setContestdetails(inputList);

		masterLeader.add(tempLeaderBean);

		return null;		
	}

	public List<LeaderBoardBean> assignRanks(List<LeaderBoardBean> toLeaderBoard)
	{
		int rank = 1;
		for(LeaderBoardBean bean: toLeaderBoard)
		{
			bean.setRank(rank);
			rank++;
		}
		return toLeaderBoard;
	}

	public void consolidateUdemyProgress(JSONObject activity)
	{
		ObjectMapper mapper = new ObjectMapper();
		List<UdemyDataFetch> udemyData = mapper.convertValue(activity.get("progressDataUdemy"),new TypeReference<List<UdemyDataFetch>>() { });

		int size = udemyData.size();
		for(int i=0; i<size; i++)
		{
			String userEmail = udemyData.get(i).getEmailId();

			if(masterUdemy.containsKey(userEmail))
			{
				masterUdemy.get(userEmail).add(udemyData.get(i).getProgressPercentage());

			}
			else {
				List<Double> progress = new ArrayList<Double>();
				progress.add(udemyData.get(i).getProgressPercentage());
				masterUdemy.put(userEmail, progress);
			}
		}

	}

	public void computeFinalProgress()
	{

		for (Map.Entry<String, List<Double>> entry : masterUdemy.entrySet())
		{
			Double val = entry.getValue().stream().mapToDouble(Double::doubleValue).sum()/entry.getValue().size();	

			int masterLeaderSize = masterLeader.size();
			for(int i=0; i<masterLeaderSize; i++)
			{
				if(masterLeader.get(i).getEmailid().equals(entry.getKey()))
				{
					masterLeader.get(i).setUdemyProgress(val);
				}
			}

		}



	}

}





