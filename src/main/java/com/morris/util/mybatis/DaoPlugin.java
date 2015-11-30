package com.morris.util.mybatis;

import java.util.List;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.TopLevelClass;

public class DaoPlugin extends PluginAdapter{

	@Override
	public boolean validate(List<String> warnings) {
		// TODO Auto-generated method stub
		
		return true;
	}
	
	@Override
	public boolean modelExampleClassGenerated(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
		// TODO Auto-generated method stub
		return super.modelExampleClassGenerated(topLevelClass, introspectedTable);
	}

}
