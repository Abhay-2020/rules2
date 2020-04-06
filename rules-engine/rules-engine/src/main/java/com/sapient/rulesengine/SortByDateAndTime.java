package com.sapient.rulesengine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;

import com.sapient.model.HackerDataFetch;

public class SortByDateAndTime implements Comparator<HackerDataFetch>{

	@Override
	public int compare(HackerDataFetch o1, HackerDataFetch o2) {
		System.out.println("Sorting");
		DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		
		
		LocalTime firstTime = null;
		LocalTime secondTime=null;
		
		LocalDate firstDate = LocalDate.parse(o1.getEndDateTime().split(" ")[0],formatter);
		System.out.println(o1.getEndDateTime().split(" ")[1]);
		firstTime = LocalTime.parse(o1.getEndDateTime().split(" ")[1]);
		
		LocalDate secondDate = LocalDate.parse(o2.getEndDateTime().split(" ")[0],formatter);
		secondTime = LocalTime.parse(o2.getEndDateTime().split(" ")[1]);
		System.out.println("date "+ firstDate + " __ "+ secondDate);
		System.out.println(firstTime+" ** " +secondTime);
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
