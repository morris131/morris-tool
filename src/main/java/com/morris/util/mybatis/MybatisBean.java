package com.morris.util.mybatis;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MybatisBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String driverClass;

	private String connectionUrl;

	private String userName;

	private String password;

	private String entityPackage;

	private String entityPath;

	private String daoPackage;

	private String daoPath;

	private String xmlPackage;

	private String xmlPath;

	private String serviceInterPackage;

	private String serviceInterPath;

	private String serviceImplPackage;

	private String serviceImplPath;

	private String controllerPackage;

	private String controllerPath;

	private Map<String, String> tableMap;

	public void addTable(String tableName, String objName) {
		if (tableMap == null) {
			tableMap = new HashMap<String, String>();
		}
		tableMap.put(tableName, objName);
	}

	public String getDriverClass() {
		return driverClass;
	}

	public void setDriverClass(String driverClass) {
		this.driverClass = driverClass;
	}

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEntityPackage() {
		return entityPackage;
	}

	public void setEntityPackage(String entityPackage) {
		this.entityPackage = entityPackage;
	}

	public String getEntityPath() {
		return entityPath;
	}

	public void setEntityPath(String entityPath) {
		this.entityPath = entityPath;
	}

	public String getDaoPackage() {
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage) {
		this.daoPackage = daoPackage;
	}

	public String getDaoPath() {
		return daoPath;
	}

	public void setDaoPath(String daoPath) {
		this.daoPath = daoPath;
	}

	public String getXmlPackage() {
		return xmlPackage;
	}

	public void setXmlPackage(String xmlPackage) {
		this.xmlPackage = xmlPackage;
	}

	public String getXmlPath() {
		return xmlPath;
	}

	public void setXmlPath(String xmlPath) {
		this.xmlPath = xmlPath;
	}

	public Map<String, String> getTableMap() {
		return tableMap;
	}

	public void setTableMap(Map<String, String> tableMap) {
		this.tableMap = tableMap;
	}

	public String getServiceInterPackage() {
		return serviceInterPackage;
	}

	public void setServiceInterPackage(String serviceInterPackage) {
		this.serviceInterPackage = serviceInterPackage;
	}

	public String getServiceInterPath() {
		return serviceInterPath;
	}

	public void setServiceInterPath(String serviceInterPath) {
		this.serviceInterPath = serviceInterPath;
	}

	public String getServiceImplPackage() {
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage) {
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getServiceImplPath() {
		return serviceImplPath;
	}

	public void setServiceImplPath(String serviceImplPath) {
		this.serviceImplPath = serviceImplPath;
	}

	public String getControllerPackage() {
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage) {
		this.controllerPackage = controllerPackage;
	}

	public String getControllerPath() {
		return controllerPath;
	}

	public void setControllerPath(String controllerPath) {
		this.controllerPath = controllerPath;
	}

}
