package com.morris.util.json;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public class Json2Bean {
	
	private String packageName;// 包名
	private String className = "JsonBean"; // 类名
	private boolean isSerializable =true; //是否序列化
	
	private StringBuffer sb; // 字符串拼接
	
	private String json; // 待转化的json字符串
	
	private Stack<String> stack = new Stack<String>();// 用栈存储临时数据
	
	private Map<String, String> clazz = new HashMap<String, String>(); // 用hashmap保存所有的类
	
	public Json2Bean(){
	}

	/**
	 * 构造方法 初始化包名和类名
	 * @param packageName 包名
	 * @param className 类名
	 */
	public Json2Bean(String packageName, String className) {
		super();
		if(null != packageName && !"".equals(packageName)){
			this.packageName = packageName;
		}
		if(null != className && !"".equals(className)){
			this.className = className;
		}
	}

	/**
	 * 根据传递进来的json生成一个或多个实体类
	 * @param json
	 * @return
	 * 	Map<String,String> 第一个String代表实体类名，第二个实体代表实体类的内容
	 */
	public Map<String,String> create(String json){
		
		sb = new StringBuffer();
		this.setJson(json);
		
		JSONObject obj = JSONObject.fromObject(json);
		
		root(obj);
		
		return clazz;
		
	}
	
	/**
	 * 如果该节点是JSONObject则生成类
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void root(JSONObject obj) {
		// 保存类
		stack.push(sb.toString());
		
		sb = new StringBuffer("");
		
		if (null != packageName && !"".equals(packageName) ){
			sb.append("package " + packageName + ";");
			sb.append("\n");
		}
		
		sb.append("import java.io.Serializable;\n");
		sb.append("import java.util.List;\n");

		sb.append("public class " + toUpperCaseFirstOne(className) + " "
				+ (isSerializable ? "implements Serializable " : "") + " {\n");
		
		Iterator<String> it = obj.keys();
		
		while (it.hasNext()) {
			String key = it.next();
			Object value = obj.get(key);
			if (value instanceof JSONObject) {
				/** 如果子字段是一个JSONObject递归生成类 */
				object(key, value);
				stack.push(className);
				className = key;
				root((JSONObject) value);
				className = stack.pop();
			} else if (value instanceof JSONArray) {
				/** 生成List 和JSONArray 中的类 */
				array(key, (JSONArray) value);
			} else {
				/** 遍历字段 */
				field(key, value);
			}

		}
		
		// 生成geter和seter方法
		it = obj.keys();
		while (it.hasNext()) {
			String key = it.next();
			Object value = obj.get(key);
			setter(key, value);
			getter(key, value);
		}

		sb.append("\n");
		sb.append("}");
		
		// 保存类
		clazz.put(findClassName(sb.toString()), sb.toString());

		sb = new StringBuffer(stack.pop());
	}
	
	/**
	 * 生成setter方法
	 * @param key
	 * @param value
	 */
	public void setter(String key, Object value){
		
		sb.append("\n\tpublic void set"+ toUpperCaseFirstOne(key) + "(");
		if (value instanceof JSONObject) {
			sb.append(toUpperCaseFirstOne(key));
		} else if (value instanceof JSONArray) {
			sb.append("List<" + toUpperCaseFirstOne(key) + ">");
		} else {
			sb.append(value.getClass().getSimpleName());
		}
		sb.append(" " + key + "){\n\t\t");
		sb.append("this." + key + " = " + key + ";");
		sb.append("\n\t}\n");
		
	}
	
	/**
	 * 生成getter方法
	 * @param key
	 * @param value
	 */
	public void getter(String key, Object value){
		
		if (value instanceof JSONObject) {
			sb.append("\n\tpublic " + toUpperCaseFirstOne(key) + " get" + toUpperCaseFirstOne(key) + "(){\n\t");
		} else if (value instanceof JSONArray) {
			sb.append("\n\tpublic List<" + toUpperCaseFirstOne(key) + "> get" + toUpperCaseFirstOne(key) + "(){\n\t");
		} else {
			sb.append("\n\tpublic " + value.getClass().getSimpleName() + " get" + toUpperCaseFirstOne(key) + "(){\n\t");
		}
		sb.append("\treturn " + key + ";");
		sb.append("\n\t}\n");
		
	}
	
	/**
	 * 生成对象属性
	 * @param key
	 * @param value
	 */
	public void object(String key, Object value){
		sb.append("\tpublic "+ toUpperCaseFirstOne(key) + " " + key + ";\n");
	}
	
	/**
	 * 生成普通属性
	 * @param key
	 * @param value
	 */
	public void field(String key, Object value) {

		sb.append("\tpublic ");
		if (value instanceof Integer) {
			sb.append("int");
		} else {
			sb.append(value.getClass().getSimpleName());
		}
		sb.append(" "+ key + ";\n");

	}

	/**
	 * 如果该节点是JSONArray则生成List
	 * @param key
	 * @param value
	 */
	public void array(String key, JSONArray value) {

		sb.append("\tpublic List<" + toUpperCaseFirstOne(key) + "> " + key + ";\n");
		if (value.size() >= 1) {
			Object obj = value.get(0);

			if (obj instanceof JSONObject) {
				className = key;
				root((JSONObject) obj);
			} else if (obj instanceof JSONArray) {
				className = key;
				array(className,(JSONArray)obj);
			}
		} else {
			className = key;
			sb.append("public class " + toUpperCaseFirstOne(className) + " {");
			sb.append("\n");
			sb.append("\n");
			sb.append("}");
		}
		sb.append("\n");
	}
	
	/**
	 * 将字符串首字母大写
	 * @param str 
	 * @return
	 */
	private String toUpperCaseFirstOne(String str){
		if(null == str){
			return null;
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}
	
	/**
	 * 从实体类中获取类名
	 * @param clazz
	 * @return
	 */
	public String findClassName(String clazz){
		String name = "";
		Pattern pattern = Pattern.compile("public class .+ implements Serializable");
		Matcher matcher = pattern.matcher(clazz);
		while(matcher.find()){
			String group = matcher.group();
			name = (group.split(" "))[2];
		}
		return name+".java";
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
}
