package com.morris.json.util;

import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.json.JSONObject;

import org.junit.Test;

import com.morris.util.json.Json2Tree;

public class Json2TreeTest {
	
	
	@Test
	public void test(){
		
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
		Json2Tree tree = new Json2Tree();
		
		JSONObject jsonObject = JSONObject.fromObject(json);
		DefaultMutableTreeNode root = tree.root(jsonObject , new DefaultMutableTreeNode());
		
		System.out.println(root);
	}

}
