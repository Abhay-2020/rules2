package com.sapient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
public class ContestBean {
	
	private String contestname;
	private Integer contestscore;

	
	public ContestBean()
	{
		contestname = "";
		contestscore=0;
	}
	
	
}
