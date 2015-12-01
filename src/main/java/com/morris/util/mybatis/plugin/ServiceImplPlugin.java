package com.morris.util.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.TableConfiguration;

public class ServiceImplPlugin extends PluginAdapter{
	
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		
		properties = this.getContext().getProperties();
		
		TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
		String domainObjectName = tableConfiguration.getDomainObjectName();
		String className = tableConfiguration.getDomainObjectName() + "Service";
		
		// 首字母小写
		String daoClassName = domainObjectName + "Dao";
		String daoName = daoClassName.substring(0, 1).toLowerCase() + daoClassName.substring(1);
		
		String serviceInterPackage = properties.getProperty("serviceInterPackage");
		String targetPackage = properties.getProperty("serviceImplPackage");
		String targetProject = properties.getProperty("serviceImplPath");
		String entityPackage = properties.getProperty("entityPackage");
		String daoPackage = properties.getProperty("daoPackage");
		
		IntrospectedColumn primaryColumn = introspectedTable.getPrimaryKeyColumns().get(0);
		String keyName = primaryColumn.getJavaProperty();
		FullyQualifiedJavaType keyType = primaryColumn.getFullyQualifiedJavaType();
		
		String fullEntityName = entityPackage + "." + domainObjectName;
		String fullEntityExampleName = entityPackage + "." + domainObjectName + "Example";
		
		FullyQualifiedJavaType fullEntityNameType = new FullyQualifiedJavaType(fullEntityName);
		FullyQualifiedJavaType fullEntityExampleNameType = new FullyQualifiedJavaType(fullEntityExampleName);
		
		Parameter fullEntityParamter = new Parameter(fullEntityNameType, "record");
		Parameter fullEntityExampleParamter = new Parameter(fullEntityExampleNameType, "example");
		Parameter keyParamter = new Parameter(keyType, keyName);
		
		
		
		List<GeneratedJavaFile> result = new ArrayList<GeneratedJavaFile>();
		
		TopLevelClass serviceClass = new TopLevelClass(targetPackage + "." + className);
		
		// 导入包
		serviceClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		serviceClass.addImportedType(fullEntityNameType);
		serviceClass.addImportedType(fullEntityExampleNameType);
		serviceClass.addImportedType(daoPackage + "." + daoClassName);
		serviceClass.addImportedType("org.apache.ibatis.session.RowBounds");
		serviceClass.addImportedType("org.springframework.stereotype.Service");
		serviceClass.addImportedType("org.springframework.transaction.annotation.Transactional");

		serviceClass.addImportedType("javax.annotation.Resource");
		
		serviceClass.setVisibility(JavaVisibility.PUBLIC);
		
		// 添加实现接口
		serviceClass.addSuperInterface(new FullyQualifiedJavaType(serviceInterPackage + "." + domainObjectName + "WsService"));
		
		// 添加注解
		serviceClass.addAnnotation("@Service");
		serviceClass.addAnnotation("@Transactional");
		
		Field field = new Field();
		field.addAnnotation("@Resource");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setName(daoName);
		field.setType(new FullyQualifiedJavaType(daoPackage + "." + daoClassName));
		
		serviceClass.addField(field);

		Method countByExample = new Method("countByExample");
		countByExample.setVisibility(JavaVisibility.PUBLIC);
		countByExample.addAnnotation("@Override");
		countByExample.setReturnType(new FullyQualifiedJavaType("int"));
		countByExample.addParameter(fullEntityExampleParamter);
		countByExample.addBodyLine("return "+ daoName+".countByExample(example);");
		serviceClass.addMethod(countByExample);
		
		Method deleteByExample = new Method("deleteByExample");
		deleteByExample.setVisibility(JavaVisibility.PUBLIC);
		deleteByExample.addAnnotation("@Override");
		deleteByExample.setReturnType(new FullyQualifiedJavaType("int"));
		deleteByExample.addParameter(fullEntityExampleParamter);
		deleteByExample.addBodyLine("return "+ daoName+".deleteByExample(example);");
		serviceClass.addMethod(deleteByExample);
		
		Method deleteByPrimaryKey = new Method("deleteByPrimaryKey");
		deleteByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
		deleteByPrimaryKey.addAnnotation("@Override");
		deleteByPrimaryKey.setReturnType(new FullyQualifiedJavaType("int"));
		deleteByPrimaryKey.addParameter(keyParamter);
		deleteByPrimaryKey.addBodyLine("return "+ daoName+".deleteByPrimaryKey(" + keyName + ");");
		serviceClass.addMethod(deleteByPrimaryKey);
		
		Method insert = new Method("insert");
		insert.setVisibility(JavaVisibility.PUBLIC);
		insert.addAnnotation("@Override");
		insert.setReturnType(new FullyQualifiedJavaType("int"));
		insert.addParameter(fullEntityParamter);
		insert.addBodyLine("return "+ daoName+".insert(record);");
		serviceClass.addMethod(insert);
		
		Method insertSelective = new Method("insertSelective");
		insertSelective.setVisibility(JavaVisibility.PUBLIC);
		insertSelective.addAnnotation("@Override");
		insertSelective.setReturnType(new FullyQualifiedJavaType("int"));
		insertSelective.addParameter(fullEntityParamter);
		insertSelective.addBodyLine("return "+ daoName+".insert(record);");
		serviceClass.addMethod(insertSelective);
		
		Method selectByExampleWithRowbounds = new Method("selectByExampleWithRowbounds");
		selectByExampleWithRowbounds.setVisibility(JavaVisibility.PUBLIC);
		selectByExampleWithRowbounds.addAnnotation("@Override");
		selectByExampleWithRowbounds.setReturnType(new FullyQualifiedJavaType("java.util.List<" + fullEntityName + ">"));
		selectByExampleWithRowbounds.addParameter(fullEntityExampleParamter);
		selectByExampleWithRowbounds.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "begin"));
		selectByExampleWithRowbounds.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "end"));
		selectByExampleWithRowbounds.addBodyLine("return " + daoName + ".selectByExampleWithRowbounds(example, new RowBounds(begin, end));");
		serviceClass.addMethod(selectByExampleWithRowbounds);
		
		Method selectByExample = new Method("selectByExample");
		selectByExample.setVisibility(JavaVisibility.PUBLIC);
		selectByExample.addAnnotation("@Override");
		selectByExample.setReturnType(new FullyQualifiedJavaType("java.util.List<" + fullEntityName + ">"));
		selectByExample.addParameter(fullEntityExampleParamter);
		selectByExample.addBodyLine("return " + daoName + ".selectByExample(example);");
		serviceClass.addMethod(selectByExample);
		
		Method selectByPrimaryKey = new Method("selectByPrimaryKey");
		selectByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
		selectByPrimaryKey.addAnnotation("@Override");
		selectByPrimaryKey.setReturnType(fullEntityNameType);
		selectByPrimaryKey.addParameter(keyParamter);
		selectByPrimaryKey.addBodyLine("return " + daoName + ".selectByPrimaryKey(" + keyName + ");");
		serviceClass.addMethod(selectByPrimaryKey);
		
		Method updateByExampleSelective = new Method("updateByExampleSelective");
		updateByExampleSelective.setVisibility(JavaVisibility.PUBLIC);
		updateByExampleSelective.addAnnotation("@Override");
		updateByExampleSelective.setReturnType(new FullyQualifiedJavaType("int"));
		updateByExampleSelective.addParameter(fullEntityParamter);
		updateByExampleSelective.addParameter(fullEntityExampleParamter);
		updateByExampleSelective.addBodyLine("return " + daoName + ".updateByExampleSelective(record, example);");
		serviceClass.addMethod(updateByExampleSelective);
		
		Method updateByExample = new Method("updateByExample");
		updateByExample.setVisibility(JavaVisibility.PUBLIC);
		updateByExample.addAnnotation("@Override");
		updateByExample.setReturnType(new FullyQualifiedJavaType("int"));
		updateByExample.addParameter(fullEntityParamter);
		updateByExample.addParameter(fullEntityExampleParamter);
		updateByExample.addBodyLine("return " + daoName + ".updateByExample(record, example);");
		serviceClass.addMethod(updateByExample);
		
		Method updateByPrimaryKeySelective = new Method("updateByPrimaryKeySelective");
		updateByPrimaryKeySelective.setVisibility(JavaVisibility.PUBLIC);
		updateByPrimaryKeySelective.addAnnotation("@Override");
		updateByPrimaryKeySelective.setReturnType(new FullyQualifiedJavaType("int"));
		updateByPrimaryKeySelective.addParameter(fullEntityParamter);
		updateByPrimaryKeySelective.addBodyLine("return " + daoName +".updateByPrimaryKeySelective(record);");
		serviceClass.addMethod(updateByPrimaryKeySelective);
		
		Method updateByPrimaryKey = new Method("updateByPrimaryKey");
		updateByPrimaryKey.setVisibility(JavaVisibility.PUBLIC);
		updateByPrimaryKey.addAnnotation("@Override");
		updateByPrimaryKey.setReturnType(new FullyQualifiedJavaType("int"));
		updateByPrimaryKey.addParameter(fullEntityParamter);
		updateByPrimaryKey.addBodyLine("return " + daoName +".updateByPrimaryKey(record);");
		serviceClass.addMethod(updateByPrimaryKey);
		
		JavaFormatter javaFormatter = introspectedTable.getContext().getJavaFormatter();
		GeneratedJavaFile serviceFile = new GeneratedJavaFile(serviceClass, targetProject, javaFormatter);
		
		
		result.add(serviceFile);
		
		return result;
	}
	
	
	
}
