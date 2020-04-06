package com.sapient.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UdemyDataFetch {

	String emailId;
	Double progressPercentage;
	
	public UdemyDataFetch() {
		emailId="";
		progressPercentage= 0.0;
	}
	
}
