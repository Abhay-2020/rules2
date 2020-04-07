package com.sapient.rulesengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.model.LeaderBoardBean;

@RestController
public class RulesEngineController {

	@Autowired
	RulesEngine rulesengine;

	@GetMapping(value="/welcome")
	public String HelloWorld() {

		return "hello world";
	}
	

//	@PostMapping("/get-leaderboard")
//	public List<LeaderBoardBean> sendLeaderBoard(@RequestBody JSONObject fromUI)
//	{
//		Boolean admin = (Boolean) fromUI.get("admin");
//		if(admin)
//		{
//			return getActivityData(/*call feign*/new ArrayList<JSONObject>());
//		}
//		else
//		{
//			String email = (String) fromUI.get("email");
//			return topFive(/*email,*/"",new ArrayList<JSONObject>());
//		}
//
//	}

	@PostMapping("/dummy")
	public List<LeaderBoardBean> topFive(String email, @RequestBody List<JSONObject> activityData) {
		
		List<LeaderBoardBean> adminLeaderBoard = getActivityData(activityData);
		List<LeaderBoardBean> userLeaderBoard;
		int size = adminLeaderBoard.size();
		for(LeaderBoardBean leader:adminLeaderBoard)
		{

			if(leader.getEmailid().equals(email))
			{
				
				if(leader.getRank()<=5)
				{
					
					if(size<=5) {
						userLeaderBoard = adminLeaderBoard.subList(0, size);
					
						return userLeaderBoard;
					}
					else
					{
						userLeaderBoard = adminLeaderBoard.subList(0, 5);
						
						return userLeaderBoard;
					}

				}
				else
				{
					userLeaderBoard = adminLeaderBoard.subList(0, 5);
					userLeaderBoard.add(leader);
				
					return userLeaderBoard;

				}
			}
			
			
		}

		
		return new ArrayList<LeaderBoardBean>();
	}
	
	@PostMapping(value="/get-activity-data")
	public List<LeaderBoardBean> getActivityData(@RequestBody List<JSONObject> activityData){

		List<LeaderBoardBean> toLeaderBoard = new ArrayList<LeaderBoardBean>(rulesengine.iterateOverActivities(activityData)); 
		rulesengine.computeFinalProgress();
		Collections.sort(toLeaderBoard, new SortFinalLeaderBoard());
		rulesengine.masterLeader.clear();
		rulesengine.masterUdemy.clear();

		return rulesengine.assignRanks(toLeaderBoard);
	}
}
