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

/**
 * 生成controller插件
 * @author lian.chen
 *
 */
public class ControllerPlugin extends PluginAdapter{
	
	
	@Override
	public boolean validate(List<String> warnings) {
		return true;
	}

	
	@Override
	public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
		
		properties = this.getContext().getProperties();
		
		TableConfiguration tableConfiguration = introspectedTable.getTableConfiguration();
		String domainObjectName = tableConfiguration.getDomainObjectName();
		String className = tableConfiguration.getDomainObjectName() + "Controller";
		
		// 首字母小写
		String serviceClassName = domainObjectName + "WsService";
		String serviceName = serviceClassName.substring(0, 1).toLowerCase() + serviceClassName.substring(1);
		
		String controllerPackage = properties.getProperty("controllerPackage");
		String controllerPath = properties.getProperty("controllerPath");
		String entityPackage = properties.getProperty("entityPackage");
		String serviceInterPackage = properties.getProperty("serviceInterPackage");
		
		IntrospectedColumn primaryColumn = introspectedTable.getPrimaryKeyColumns().get(0);
		String keyName = primaryColumn.getJavaProperty();
		FullyQualifiedJavaType keyType = primaryColumn.getFullyQualifiedJavaType();
		
		//String fullEntityName = entityPackage + "." + domainObjectName;
		//String fullEntityExampleName = entityPackage + "." + domainObjectName + "Example";
		
		//FullyQualifiedJavaType fullEntityNameType = new FullyQualifiedJavaType(fullEntityName);
		//FullyQualifiedJavaType fullEntityExampleNameType = new FullyQualifiedJavaType(fullEntityExampleName);
		
		Parameter keyParamter = new Parameter(keyType, keyName);
		Parameter entityParamter = new Parameter(new FullyQualifiedJavaType(entityPackage + "." + domainObjectName), "record");
		//Parameter fullEntityExampleParamter = new Parameter(fullEntityExampleNameType, "example");
		//Parameter keyParamter = new Parameter(keyType, keyName);
		
		
		
		List<GeneratedJavaFile> result = new ArrayList<GeneratedJavaFile>();
		
		TopLevelClass serviceClass = new TopLevelClass(controllerPackage + "." + className);
		
		// 导入包
		serviceClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		serviceClass.addImportedType(serviceInterPackage + "." + serviceClassName);
		serviceClass.addImportedType(entityPackage + "." +domainObjectName);
		serviceClass.addImportedType("org.springframework.stereotype.Controller");

		serviceClass.addImportedType("javax.annotation.Resource");
		
		serviceClass.setVisibility(JavaVisibility.PUBLIC);
		
		// 添加实现接口
		//serviceClass.addSuperInterface(new FullyQualifiedJavaType(serviceInterPackage + "." + domainObjectName + "WsService"));
		
		// 添加注解
		serviceClass.addAnnotation("@Controller");
		
		Field field = new Field();
		field.addAnnotation("@Resource");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setName(serviceName);
		field.setType(new FullyQualifiedJavaType(serviceInterPackage + "." + serviceClassName));
		
		serviceClass.addField(field);
		
		Method toList = new Method("toList");
		toList.setVisibility(JavaVisibility.PUBLIC);
		toList.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		toList.addParameter(new Parameter(new FullyQualifiedJavaType("org.springframework.ui.Model"), "modal"));
		//toList.addBodyLine(serviceName + ".insert(record);");
		toList.addBodyLine("return \"\";");
		serviceClass.addMethod(toList);

		Method insert = new Method("insert");
		insert.setVisibility(JavaVisibility.PUBLIC);
		insert.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		insert.addParameter(entityParamter);
		insert.addBodyLine(serviceName + ".insert(record);");
		insert.addBodyLine("return \"\";");
		serviceClass.addMethod(insert);
		
		Method findById = new Method("findById");
		findById.setVisibility(JavaVisibility.PUBLIC);
		findById.setReturnType(new FullyQualifiedJavaType(entityPackage + "." + domainObjectName));
		findById.addParameter(keyParamter);
		findById.addBodyLine("return " + serviceName + ".selectByPrimaryKey(" + keyName + ");");
		serviceClass.addMethod(findById);
		
		Method deleteById = new Method("deleteById");
		deleteById.setVisibility(JavaVisibility.PUBLIC);
		deleteById.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		deleteById.addParameter(keyParamter);
		deleteById.addBodyLine(serviceName + ".deleteByPrimaryKey(" + keyName + ");");
		deleteById.addBodyLine("return \"\";");
		serviceClass.addMethod(deleteById);
		
		Method updateById = new Method("updateById");
		updateById.setVisibility(JavaVisibility.PUBLIC);
		updateById.setReturnType(new FullyQualifiedJavaType("String"));
		updateById.addParameter(entityParamter);
		updateById.addBodyLine("return \"\";");
		serviceClass.addMethod(updateById);
		
		JavaFormatter javaFormatter = introspectedTable.getContext().getJavaFormatter();
		GeneratedJavaFile serviceFile = new GeneratedJavaFile(serviceClass, controllerPath, javaFormatter);
		
		
		result.add(serviceFile);
		
		return result;
	}
	
	
	
}
