package com.morris.util.mybatis.plugin;

import java.util.List;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.TopLevelClass;

/**
 * dao 增加注解
 * 
 * @author morris
 *
 */
public class DaoPlugin extends PluginAdapter {

	public boolean validate(List<String> warnings) {
		return true;
	}

	@Override
	public boolean clientGenerated(Interface interfaze, TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		
		interfaze.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));
		
		interfaze.addAnnotation("@Component");
		return super.clientGenerated(interfaze, topLevelClass, introspectedTable);
	}

}
