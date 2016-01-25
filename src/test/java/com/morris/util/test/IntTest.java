package com.morris.util.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntTest {

	public static void main(String[] args) {

		String string = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/></head><body><form id = \"pay_form\" action=\"http://www.tftpay.com/middleWeb/OMCGPUB2/FormTrans7.dow\" method=\"post\"><input type=\"hidden\" name=\"orderNo\" id=\"orderNo\" value=\"2016011531872388\"/><input type=\"hidden\" name=\"merNo\" id=\"merNo\" value=\"846290045112088\"/></form></body><script type=\"text/javascript\">document.all.pay_form.submit();</script></html>";

		//boolean matches = string.matches("[0-9]{10}");
		
		Pattern p = Pattern.compile("[\\d]{16}");
		Matcher m = p.matcher(string);
		
		while(m.find()){
			System.out.println(m.group());
		}
		
//		int groupCount = m.groupCount();
//		
//		System.out.println(groupCount);
//		
//		for(int i = 0;i< groupCount;i++){
//			System.out.println(m.group());
//		}
		
		
		//boolean b = m.matches();
		
		//System.out.println(m.matches());
	}
}