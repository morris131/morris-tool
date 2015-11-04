package com.morris.json.bean;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String jsonString = "{\"resultcode\":\"200\",\n" + 
				"\"reason\":\"Return Successd!\",\n" + 
				"\"result\":{\"data\":[\n" + 
				"         {\"MCC\":\"0\",\n" + 
				"          \"MNC\":\"0\",\n" + 
				"          \"LAC\":\"12\",\n" + 
				"          \"CELL\":\"12\",\n" + 
				"          \"LNG\":\"118.73232482817\", \n" + 
				"          \"LAT\":\"31.993439698986\",\n" + 
				"          \"OO_LNG\":\"118.73749\",\n" + 
				"          \"OO_LAT\":\"31.991368\",\n" + 
				"          \"PRECISION\":\"1500\",\n" + 
				"          \"ADDRESS\":\"江苏省南京市建邺区富春江东街\"}]},\n" + 
				"    \"error_code\":0}\n";
		
		
		
		
		JsonConfig jsonConfig = new JsonConfig();
		
		
		Map<String,Class<?>> classMap = new HashMap<String,Class<?>>();
		classMap.put("data", Data.class);
		classMap.put("result", Result.class);
		jsonConfig.setClassMap(classMap );
		
		
		JsonBean bean = (JsonBean) JSONObject.toBean(JSONObject.fromObject(jsonString),JsonBean.class,classMap);
		
		String address = bean.getResult().getData().get(0).getADDRESS();
		
		System.out.println(address);
		
		System.out.println(bean.toString());
		
		
		
		//JSONObject.fromObject(jsonString,jsonConfig);

	}

}
