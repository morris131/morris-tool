package com.morris.util.mybatis.plugin;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaFormatter;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.Interface;
import org.mybatis.generator.api.dom.java.JavaVisibility;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.config.TableConfiguration;

/**
 * 生成service接口插件
 * @author lian.chen
 *
 */
public class ServiceInterPlugin extends PluginAdapter{
	
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		
		properties = this.getContext().getProperties();
		
		TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
		String domainObjectName = tableConfiguration.getDomainObjectName();//实体类名
		String className = tableConfiguration.getDomainObjectName() + "WsService";// service接口名
		
		String serviceInterPackage = properties.getProperty("serviceInterPackage");
		String serviceInterPath = properties.getProperty("serviceInterPath");
		String entityPackage = properties.getProperty("entityPackage");
		
		IntrospectedColumn primaryColumn = introspectedTable.getPrimaryKeyColumns().get(0);
		String keyName = primaryColumn.getJavaProperty();//实体类主键属性名
		FullyQualifiedJavaType keyType = primaryColumn.getFullyQualifiedJavaType();//实体类主键类型
		
		String fullEntityName = entityPackage + "." + domainObjectName;
		String fullEntityExampleName = entityPackage + "." + domainObjectName + "Example";
		
		FullyQualifiedJavaType fullEntityNameType = new FullyQualifiedJavaType(fullEntityName);
		FullyQualifiedJavaType fullEntityExampleNameType = new FullyQualifiedJavaType(fullEntityExampleName);
		
		Parameter fullEntityParamter = new Parameter(fullEntityNameType, "record");
		Parameter fullEntityExampleParamter = new Parameter(fullEntityExampleNameType, "example");
		Parameter keyParamter = new Parameter(keyType, keyName);
		
		
		
		List<GeneratedJavaFile> result = new ArrayList<GeneratedJavaFile>();
		
		Interface inter = new Interface(serviceInterPackage + "." + className);
		
		// 导入包
		inter.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		inter.addImportedType(fullEntityNameType);
		inter.addImportedType(fullEntityExampleNameType);
		
		inter.setVisibility(JavaVisibility.PUBLIC);

		Method countByExample = new Method("countByExample");
		countByExample.setReturnType(new FullyQualifiedJavaType("int"));
		countByExample.addParameter(fullEntityExampleParamter);
		inter.addMethod(countByExample);
		
		Method deleteByExample = new Method("deleteByExample");
		deleteByExample.setReturnType(new FullyQualifiedJavaType("int"));
		deleteByExample.addParameter(fullEntityExampleParamter);
		inter.addMethod(deleteByExample);
		
		Method deleteByPrimaryKey = new Method("deleteByPrimaryKey");
		deleteByPrimaryKey.setReturnType(new FullyQualifiedJavaType("int"));
		deleteByPrimaryKey.addParameter(keyParamter);
		inter.addMethod(deleteByPrimaryKey);
		
		Method insert = new Method("insert");
		insert.setReturnType(new FullyQualifiedJavaType("int"));
		insert.addParameter(fullEntityParamter);
		inter.addMethod(insert);
		
		Method insertSelective = new Method("insertSelective");
		insertSelective.setReturnType(new FullyQualifiedJavaType("int"));
		insertSelective.addParameter(fullEntityParamter);
		inter.addMethod(insertSelective);
		
		Method selectByExampleWithRowbounds = new Method("selectByExampleWithRowbounds");
		selectByExampleWithRowbounds.setReturnType(new FullyQualifiedJavaType("java.util.List<" + fullEntityName + ">"));
		selectByExampleWithRowbounds.addParameter(fullEntityExampleParamter);
		selectByExampleWithRowbounds.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "begin"));
		selectByExampleWithRowbounds.addParameter(new Parameter(new FullyQualifiedJavaType("int"), "end"));
		inter.addMethod(selectByExampleWithRowbounds);
		
		Method selectByExample = new Method("selectByExample");
		selectByExample.setReturnType(new FullyQualifiedJavaType("java.util.List<" + fullEntityName + ">"));
		selectByExample.addParameter(fullEntityExampleParamter);
		inter.addMethod(selectByExample);
		
		Method selectByPrimaryKey = new Method("selectByPrimaryKey");
		selectByPrimaryKey.setReturnType(fullEntityNameType);
		selectByPrimaryKey.addParameter(keyParamter);
		inter.addMethod(selectByPrimaryKey);
		
		Method updateByExampleSelective = new Method("updateByExampleSelective");
		updateByExampleSelective.setReturnType(new FullyQualifiedJavaType("int"));
		updateByExampleSelective.addParameter(fullEntityParamter);
		updateByExampleSelective.addParameter(fullEntityExampleParamter);
		inter.addMethod(updateByExampleSelective);
		
		Method updateByExample = new Method("updateByExample");
		updateByExample.setReturnType(new FullyQualifiedJavaType("int"));
		updateByExample.addParameter(fullEntityParamter);
		updateByExample.addParameter(fullEntityExampleParamter);
		inter.addMethod(updateByExample);
		
		Method updateByPrimaryKeySelective = new Method("updateByPrimaryKeySelective");
		updateByPrimaryKeySelective.setReturnType(new FullyQualifiedJavaType("int"));
		updateByPrimaryKeySelective.addParameter(fullEntityParamter);
		inter.addMethod(updateByPrimaryKeySelective);
		
		Method updateByPrimaryKey = new Method("updateByPrimaryKey");
		updateByPrimaryKey.setReturnType(new FullyQualifiedJavaType("int"));
		updateByPrimaryKey.addParameter(fullEntityParamter);
		inter.addMethod(updateByPrimaryKey);
		
		JavaFormatter javaFormatter = introspectedTable.getContext().getJavaFormatter();
		GeneratedJavaFile serviceFile = new GeneratedJavaFile(inter, serviceInterPath, javaFormatter);
		
		
		result.add(serviceFile);
		
		
		return result;
	}
	
	
	
}
