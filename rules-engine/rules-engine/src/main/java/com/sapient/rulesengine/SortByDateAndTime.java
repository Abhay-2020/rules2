package com.sapient.rulesengine;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import com.sapient.model.HackerDataFetch;

public class SortByDateAndTime implements Comparator<HackerDataFetch>{

	@Override
	public int compare(HackerDataFetch o1, HackerDataFetch o2) {
		
		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		
		
		LocalTime firstTime = null;
		LocalTime secondTime=null;
		
		LocalDate firstDate = LocalDate.parse(o1.getEndDateTime().split(" ")[0],formatter);
		
		firstTime = LocalTime.parse(o1.getEndDateTime().split(" ")[1]);
		
		LocalDate secondDate = LocalDate.parse(o2.getEndDateTime().split(" ")[0],formatter);
		secondTime = LocalTime.parse(o2.getEndDateTime().split(" ")[1]);
		
		if(firstDate.isBefore(secondDate)) {
			
			return -1;
		}
		else if(firstDate.equals(secondDate)) {
			if(firstTime.isBefore(secondTime)) {
				return -1;
			}
			else return 1;
		}
		else return 1;
		
		
		
	}
}
