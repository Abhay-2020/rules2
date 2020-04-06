package com.sapient.rulesengine;

import java.util.Comparator;

import com.sapient.model.LeaderBoardBean;

public class SortFinalLeaderBoard implements Comparator<LeaderBoardBean>{

	@Override
	public int compare(LeaderBoardBean o1, LeaderBoardBean o2) {
		
		if(o1.getTotalscore()>o2.getTotalscore())
		{
			return -1;
		}
		else if(o1.getTotalscore()<o2.getTotalscore())
		{
			return 1;
		}
		else {
			if(o1.getUdemyProgress()>o2.getUdemyProgress())
			{
				return -1;
			}
			else
			{
				return 1;
			}
		}
	}

}
