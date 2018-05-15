package com.example.demo;

import java.util.Calendar;

public class MainClass {
	public static void main(String[] args) {
//		2018-10-31 02:23:45.3580
//		2018-11-01 02:23:45.9190
//		2018-11-12 02:23:45.8370
//		2019-01-12 02:23:45.9690
//		2018-12-12 02:23:45.1060
		
		
//		1523469600000   1523473200000 
		Calendar calendar = Calendar.getInstance();
		String year = "2018";
		String month = "3";
		String date = "12";
		String hourOfDay = "3";
		String minute = "0";
		String second = "0";
		calendar.set(
				Integer.parseInt(year), 
				Integer.parseInt(month), 
				Integer.parseInt(date),
				Integer.parseInt(hourOfDay),
				Integer.parseInt(minute), 
				Integer.parseInt(second));
		
		
		long millis = (calendar.getTimeInMillis()/1000)*1000;
		System.out.println(millis);
		
		//---------------------------------------------
//		calendar.setTimeInMillis(1524132188749L);
//		System.out.println(calendar.getTime());
	}
}
