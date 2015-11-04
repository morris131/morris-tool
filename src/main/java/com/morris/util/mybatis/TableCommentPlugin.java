package com.morris.util.mybatis;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.xml.Element;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * 为xml中的字段添加注释
 * @author morris
 *
 */
public class TableCommentPlugin extends PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean sqlMapResultMapWithoutBLOBsElementGenerated(XmlElement element, IntrospectedTable introspectedTable) {

		List<Element> elements = new ArrayList<Element>(element.getElements());
		element.getElements().clear();
		
		for (int i = 0; i < introspectedTable.getAllColumns().size(); i++) {
			IntrospectedColumn column = introspectedTable.getAllColumns().get(i);
			String remarks = column.getRemarks(); //
			
			element.getElements().add(new TextElement("<!--" + remarks + "-->"));
			element.getElements().add(elements.get(i));
			
		}
		
		return super.sqlMapResultMapWithoutBLOBsElementGenerated(element, introspectedTable);
	}
}
