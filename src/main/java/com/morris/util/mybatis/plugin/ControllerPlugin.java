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
 * @author Morris
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
		String requestMappingName = domainObjectName.substring(0, 1).toLowerCase()+domainObjectName.substring(1, domainObjectName.length());
		String controllerPackage = properties.getProperty("controllerPackage");
		String controllerPath = properties.getProperty("controllerPath");
		String entityPackage = properties.getProperty("entityPackage");
		String serviceInterPackage = properties.getProperty("serviceInterPackage");
		
		IntrospectedColumn primaryColumn = introspectedTable.getPrimaryKeyColumns().get(0);
		String keyName = primaryColumn.getJavaProperty();
		FullyQualifiedJavaType keyType = primaryColumn.getFullyQualifiedJavaType();
	
		Parameter keyParamter = new Parameter(keyType, keyName);
		Parameter entityParamter = new Parameter(new FullyQualifiedJavaType(entityPackage + "." + domainObjectName), "record");
		
		List<GeneratedJavaFile> result = new ArrayList<GeneratedJavaFile>();
		
		TopLevelClass serviceClass = new TopLevelClass(controllerPackage + "." + className);
		
		// 导入包 
		serviceClass.addImportedType(new FullyQualifiedJavaType("java.util.List"));
		serviceClass.addImportedType(new FullyQualifiedJavaType("org.springframework.ui.Model"));
		serviceClass.addImportedType(serviceInterPackage + "." + serviceClassName);
		serviceClass.addImportedType(entityPackage + "." +domainObjectName);
		serviceClass.addImportedType("org.springframework.stereotype.Controller");
        serviceClass.addImportedType("org.springframework.web.bind.annotation.RequestMapping");
        serviceClass.addImportedType("cn.tempus.prodcloud.entity.refund."+tableConfiguration.getDomainObjectName()+"Example");
		serviceClass.addImportedType("javax.annotation.Resource");
		
		serviceClass.setVisibility(JavaVisibility.PUBLIC);
		
		// 添加注解
		serviceClass.addAnnotation("@Controller");
		serviceClass.addAnnotation("@RequestMapping(value="+'"'+requestMappingName+'"'+")");
		Field field = new Field();
		field.addAnnotation("@Resource");
		field.setVisibility(JavaVisibility.PRIVATE);
		field.setName(serviceName);
		field.setType(new FullyQualifiedJavaType(serviceInterPackage + "." + serviceClassName));
		
		serviceClass.addField(field);
		
		// 分页查询
		Method toList = new Method("toList");
		toList.setVisibility(JavaVisibility.PUBLIC);
		toList.addAnnotation("@RequestMapping(value="+'"'+"to"+tableConfiguration.getDomainObjectName()+"List"+'"'+")");
		toList.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		toList.addParameter(new Parameter(new FullyQualifiedJavaType("org.springframework.ui.Model"), "model"));
		toList.addBodyLine(tableConfiguration.getDomainObjectName()+"Example example = new "+ tableConfiguration.getDomainObjectName()+"Example();");
		toList.addBodyLine("List<"+tableConfiguration.getDomainObjectName()+"> list"+tableConfiguration.getDomainObjectName()+" = "+
		serviceName + ".selectByExampleWithRowbounds(example,0,0);");
		toList.addBodyLine("model.addAttribute("+'"'+"list"+tableConfiguration.getDomainObjectName()+'"'+", list"+tableConfiguration.getDomainObjectName()+");");
		toList.addBodyLine("return \"\";");
		serviceClass.addMethod(toList);

		// 新增
		Method insert = new Method("insert");
		insert.setVisibility(JavaVisibility.PUBLIC);
		insert.addAnnotation("@RequestMapping(value="+'"'+"insert"+tableConfiguration.getDomainObjectName()+'"'+")");
		insert.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		insert.addParameter(entityParamter);
		insert.addBodyLine(serviceName + ".insert(record);");
		insert.addBodyLine("return \"\";");
		serviceClass.addMethod(insert);
		
		// 根据主键查找
		Method findById = new Method("findById");
		findById.setVisibility(JavaVisibility.PUBLIC);
		findById.addAnnotation("@RequestMapping(value="+'"'+"find"+tableConfiguration.getDomainObjectName()+"ById"+'"'+")");
		findById.setReturnType(new FullyQualifiedJavaType(entityPackage + "." + domainObjectName));
		findById.addParameter(keyParamter);
		findById.addBodyLine("return " + serviceName + ".selectByPrimaryKey(" + keyName + ");");
		serviceClass.addMethod(findById);
		
		// 根据主键删除
		Method deleteById = new Method("deleteById");
		deleteById.setVisibility(JavaVisibility.PUBLIC);
		deleteById.addAnnotation("@RequestMapping(value="+'"'+"delete"+tableConfiguration.getDomainObjectName()+"ById"+'"'+")");
		deleteById.setReturnType(new FullyQualifiedJavaType("java.lang.String"));
		deleteById.addParameter(keyParamter);
		deleteById.addBodyLine(serviceName + ".deleteByPrimaryKey(" + keyName + ");");
		deleteById.addBodyLine("return \"\";");
		serviceClass.addMethod(deleteById);
		
		// 根据主键更新
		Method updateById = new Method("updateById");
		updateById.setVisibility(JavaVisibility.PUBLIC);
		updateById.addAnnotation("@RequestMapping(value="+'"'+"update"+tableConfiguration.getDomainObjectName()+"ById"+'"'+")");
		updateById.setReturnType(new FullyQualifiedJavaType("String"));
		updateById.addParameter(entityParamter);
		updateById.addBodyLine(serviceName+".updateByPrimaryKey(record);");
		updateById.addBodyLine("return \"\";");
		serviceClass.addMethod(updateById);
		JavaFormatter javaFormatter = introspectedTable.getContext().getJavaFormatter();
		GeneratedJavaFile serviceFile = new GeneratedJavaFile(serviceClass, controllerPath, javaFormatter);
		
		
		result.add(serviceFile);
		
		return result;
	}
	
	
	
}
