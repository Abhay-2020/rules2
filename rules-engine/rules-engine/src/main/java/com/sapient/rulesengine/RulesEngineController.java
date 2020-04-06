package com.sapient.rulesengine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.model.LeaderBoardBean;
import com.sapient.model.RulesBean;

@RestController
//check global level crossorigin
public class RulesEngineController {

	@Autowired
	RulesEngine rulesengine;

	@GetMapping(value="/welcome")
	public String HelloWorld() {

		return "hello world";
	}
	//leaderboard ui will post request to admin team
	// admin team will recieve batch etc.
	// they will call our getactivity end point and send data.. thats it

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
		email = "dhoni@abcd.com";
		List<LeaderBoardBean> adminLeaderBoard = getActivityData(activityData);
		List<LeaderBoardBean> userLeaderBoard = new ArrayList<LeaderBoardBean>();
		int size = adminLeaderBoard.size();
		for(LeaderBoardBean leader:adminLeaderBoard)
		{

			if(leader.getEmailid().equals(email))
			{
				System.out.println("here");
				if(leader.getRank()<=5)
				{
					System.out.println("here");
					if(size<=5) {
						userLeaderBoard = adminLeaderBoard.subList(0, size);
						System.out.println(userLeaderBoard.size());
						return userLeaderBoard;
					}
					else
					{
						userLeaderBoard = adminLeaderBoard.subList(0, 5);
						System.out.println(userLeaderBoard.size());
						return userLeaderBoard;
					}

				}
				else
				{
					userLeaderBoard = adminLeaderBoard.subList(0, 5);
					userLeaderBoard.add(leader);
					System.out.println(userLeaderBoard.size());
					return userLeaderBoard;

				}
			}
			
			
		}

		System.out.println(userLeaderBoard.size());
		return new ArrayList<LeaderBoardBean>();
	}

	
	
	
	@PostMapping(value="/get-activity-data")
	public List<LeaderBoardBean> getActivityData(@RequestBody List<JSONObject> activityData){

		List<LeaderBoardBean> toLeaderBoard = new ArrayList<LeaderBoardBean>(rulesengine.IterateOverActivities(activityData)); 
		rulesengine.computeFinalProgress();
		Collections.sort(toLeaderBoard, new SortFinalLeaderBoard());
		rulesengine.masterLeader.clear();
		rulesengine.masterUdemy.clear();

		return rulesengine.assignRanks(toLeaderBoard);
	}
}
