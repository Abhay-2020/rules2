package com.sapient.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HackerDataFetch {
	
	private String batch;
	private String category;
	private String emailId;
	private String name;
	private Integer score;
	private Integer activityScore;
	private String startDateTime;
	private String endDateTime;
	
}
