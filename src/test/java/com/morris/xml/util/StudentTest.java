package com.morris.xml.util;

import java.io.IOException;

import javax.xml.bind.JAXBException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;

public class StudentTest {

	@Test
	public void test() throws JAXBException, IOException {
		
//		JAXBContext jaxbContext = JAXBContext.newInstance(StudentType.class);
//		
//		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//		
//		File file = new File("student.xml");
//		
//		System.out.println(file.exists());
//		
//		StudentType unmarshal = (StudentType) unmarshaller.unmarshal(new File("student.xml"));
//		
//		System.out.println(unmarshal);
		
		Document document = Jsoup.connect("http://www.xmlforasp.net/CodeBank/System_Xml_Schema/BuildSchema/BuildXMLSchema.aspx")
		.header("Connection", "keep-alive")
		.header("Cache-Control", " max-age=0")
		.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
		.header("Origin", "http://www.xmlforasp.net")
		.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36")
		.header("Content-Type", "application/x-www-form-urlencoded")
		.header("Referer", "http://www.xmlforasp.net/CodeBank/System_Xml_Schema/BuildSchema/BuildXMLSchema.aspx")
		.header("Accept-Encoding", "gzip,deflate,sdch")
		.header("Accept-Language", "zh-CN,zh;q=0.8")
		.data("__VIEWSTATE", "%2FwEPDwULLTE0NDc4ODY1NjZkZA2Y4g8Eh%2BnSAMGdfKHIl6K%2FLL68")
		.data("__VIEWSTATEGENERATOR", "1BA98693")
		.data("__EVENTVALIDATION", "%2FwEWBgLc4IqkDwKj%2B%2Fr1AwKM1c%2F8AwKkyPGDAQK7yPGDAQK0p9vtDRDnfx8io%2FkLjNAMiwPuUPl0frtl")
		.data("txtXML", "%3C%3Fxml+version%3D%221.0%22+encoding%3D%22UTF-8%22+standalone%3D%22yes%22%3F%3E%0D%0A%3Cstudent+id%3D%221%22%3E%0D%0A++++%3Cage%3E22%3C%2Fage%3E%0D%0A++++%3Cname%3Emorris%3C%2Fname%3E%0D%0A++++%3Cobjects%3E%0D%0A++++++++%3Cobject%3Ejava%3C%2Fobject%3E%0D%0A++++++++%3Cobject%3Ephp%3C%2Fobject%3E%0D%0A++++++++%3Cobject%3Epython%3C%2Fobject%3E%0D%0A++++%3C%2Fobjects%3E%0D%0A%3C%2Fstudent%3E")
		.data("btnGenerateSchema", "Generate Schema")
		.data("rdoList", "0").post();
		
		String text = document.text();
		
		
		System.out.println(text);
//		User-Agent: 
//		Content-Type: application/x-www-form-urlencoded
//		Referer: http://www.xmlforasp.net/CodeBank/System_Xml_Schema/BuildSchema/BuildXMLSchema.aspx
//		Accept-Encoding: gzip,deflate,sdch
//		Accept-Language: zh-CN,zh;q=0.8
		
		
//		txtXML=&
//		btnGenerateSchema=&
//		rdoList=0
//		
		
	}

}
