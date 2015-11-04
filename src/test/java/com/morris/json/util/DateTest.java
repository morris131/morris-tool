package com.morris.json.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {

	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		
		String str1 = "2015-01-02";
		String str2 = "2015-1-02";
		String str3 = "2015-01-2";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date1 = sdf.parse(str1);
		Date date2 = sdf.parse(str2);
		Date date3 = sdf.parse(str3);
		
		System.out.println(date1);
		System.out.println(date2);
		System.out.println(date3);

	}

}
