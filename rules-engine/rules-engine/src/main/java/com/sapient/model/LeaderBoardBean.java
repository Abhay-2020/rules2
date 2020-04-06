package com.sapient.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
public class LeaderBoardBean {
	
	private Integer rank;
	private String emailid;
	private String name;
	private Integer totalscore;
	private List<ContestBean> contestdetails;
	private Double udemyProgress;
	
	public LeaderBoardBean(){
		
		rank=0;
		emailid="";
		name="";
		totalscore=0;
		contestdetails= new ArrayList<ContestBean>();
		udemyProgress = 0.0;
	}
	
}
