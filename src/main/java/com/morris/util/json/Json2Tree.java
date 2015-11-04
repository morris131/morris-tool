package com.morris.util.json;

import java.util.Iterator;

import javax.swing.tree.DefaultMutableTreeNode;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class Json2Tree {
	
	@SuppressWarnings("unchecked")
	public DefaultMutableTreeNode root(JSONObject jsonObject, DefaultMutableTreeNode node){
		
		Iterator<String> it = jsonObject.keys();
		
		while (it.hasNext()) {
			String key = it.next();
			Object value = jsonObject.get(key);
			if (value instanceof JSONObject) {
				/** 如果子字段是一个JSONObject递归生成类 */
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(key);
				childNode = root((JSONObject) value, childNode);
				node.add(childNode);
			} else if (value instanceof JSONArray) {
				/** 生成List 和JSONArray 中的类 */
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("[] " + key);
				childNode = array((JSONArray)value, childNode);
				node.add(childNode);
			} else {
				node.add(new DefaultMutableTreeNode(key + ":" + value));
				/** 遍历字段 */
			}

		}
		return node;
	}
	
	public DefaultMutableTreeNode array(JSONArray jsonArray, DefaultMutableTreeNode node) {

		for(int i = 0;i < jsonArray.size(); i++) {
			Object value = jsonArray.get(0);

			if (value instanceof JSONObject) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode("["+i+"]");
				childNode = root((JSONObject) value, childNode);
				node.add(childNode);
			} else if (value instanceof JSONArray) {
				DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(value);
				childNode = array((JSONArray)value, childNode);
				node.add(childNode);
			}
		} 
		return node;
	}

}
