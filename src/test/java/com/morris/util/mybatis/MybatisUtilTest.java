package com.morris.util.mybatis;

import org.junit.Test;

public class MybatisUtilTest{
	
	@Test
	public void testGenerate() {
		
		MybatisBean bean = new MybatisBean();
		
		bean.setDriverClass("oracle.jdbc.driver.OracleDriver");
		bean.setConnectionUrl("jdbc:oracle:thin:@172.16.92.24:1521:neikong");
		bean.setUserName("tcpmain1");
		bean.setPassword("tcpmain9");
		String xmlPackage = "xml";
		bean.setXmlPackage(xmlPackage);
		String xmlPath = "src/main/java";
		bean.setXmlPath(xmlPath);
		bean.setEntityPackage("cn.tempus.prodcloud.entity.refund");
		bean.setEntityPath(xmlPath);
		bean.setDaoPackage("cn.tempus.prodcloud.dao.refund");
		bean.setDaoPath(xmlPath);
		bean.setServiceInterPackage("cn.tempus.prodcloud.service.refund");
		bean.setServiceInterPath(xmlPath);
		bean.setServiceImplPackage("cn.tempus.prodcloud.service.refund");
		bean.setServiceImplPath(xmlPath);
		
		bean.setControllerPath(xmlPath);
		bean.setControllerPackage("cn.tempus.prodcloud.controller.refund");
		
		bean.addTable("REFUND_TSL_EXTRACT_FILE", "RefundTslExtractFile");
		
		MybatisUtil.generate(bean);
		
	}
}
