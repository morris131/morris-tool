package com.morris.xml.thread.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class Test {
	
	public static void main(String[] args) throws ParseException {
		//System.out.println(System.currentTimeMillis());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = sdf.parse("2015-11-4");
		
		 Calendar calendar = new GregorianCalendar();
		 calendar.setTime(date);
		 calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
		 date=calendar.getTime();
		 
		 System.out.println(date.toLocaleString());
	}

}
