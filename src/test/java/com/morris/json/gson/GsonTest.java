package com.morris.json.gson;

import static org.junit.Assert.*;

import org.junit.Test;

import com.google.gson.Gson;
import com.morris.json.bean.JsonBean;

public class GsonTest {

	@Test
	public void test() {
		
		String json = "{\n" + 
				"    \"resultcode\": \"200\",\n" + 
				"    \"reason\": \"Return Successd!\",\n" + 
				"    \"result\": {\n" + 
				"        \"data\": [\n" + 
				"            {\n" + 
				"                \"MCC\": \"0\",\n" + 
				"                \"MNC\": \"0\",\n" + 
				"                \"LAC\": \"12\",\n" + 
				"                \"CELL\": \"12\",\n" + 
				"                \"LNG\": \"118.73232482817\",\n" + 
				"                \"LAT\": \"31.993439698986\",\n" + 
				"                \"OO_LNG\": \"118.73749\",\n" + 
				"                \"OO_LAT\": \"31.991368\",\n" + 
				"                \"PRECISION\": \"1500\",\n" + 
				"                \"ADDRESS\": \"江苏省南京市建邺区富春江东街\"\n" + 
				"            }\n" + 
				"        ]\n" + 
				"    },\n" + 
				"    \"error_code\": 0\n" + 
				"}\n";
		
		Gson gson = new Gson();
		JsonBean jsonBean = gson.fromJson(json, JsonBean.class);
		
		System.out.println(jsonBean);
		
		String json2 = gson.toJson(jsonBean);
		
		System.out.println(json2);
		
//		fail("Not yet implemented");
	}

}
