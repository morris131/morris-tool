package com.morris.util.xml;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;
import net.sf.json.xml.XMLSerializer;

public class Xml2Json {
	

	/**
	 * xml to json
	 * @param xml
	 * @return
	 */
	public static String xml2Json(String xml){
		XMLSerializer xmlSerializer = new XMLSerializer();
		return xmlSerializer.read(xml).toString();
	}
	
	/**
	 * json to xml
	 * @param json
	 * @return
	 */
	public static String json2Xml(String json){
		XMLSerializer xmlSerializer = new XMLSerializer();
		JSON jsonObject = JSONSerializer.toJSON(json);
		return xmlSerializer.write(jsonObject);
	}

}
