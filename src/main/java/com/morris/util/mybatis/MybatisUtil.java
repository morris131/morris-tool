package com.morris.util.mybatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map.Entry;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.config.CommentGeneratorConfiguration;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.PluginConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * mybatis自动生成xml,entity,entityExample,dao文件
 * @author morris
 *
 */
public class MybatisUtil {
	
	public static void generate(MybatisBean bean) {
	
		Configuration config = new Configuration();
		
		Context context = new Context(null);
		
		context.setId("table");
		context.setTargetRuntime("com.morris.util.mybatis.IntrospectedTableImpl");
		
		// 数据库连接
		JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
		jdbc.setDriverClass(bean.getDriverClass());
		jdbc.setConnectionURL(bean.getConnectionUrl());
		jdbc.setUserId(bean.getUserName());
		jdbc.setPassword(bean.getPassword());
		jdbc.addProperty("remarksReporting","true"); // oracle加上此字段才能访问到注释
		context.setJdbcConnectionConfiguration(jdbc);
		
		// 去掉注释
		CommentGeneratorConfiguration comment = new CommentGeneratorConfiguration();
		comment.addProperty("suppressAllComments", "true");
		context.setCommentGeneratorConfiguration(comment);
		
		// 插件
		// 序列化插件
		PluginConfiguration serializablePlugin = new PluginConfiguration();
		serializablePlugin.setConfigurationType("com.morris.util.mybatis.plugin.SerializablePlugin");
		context.addPluginConfiguration(serializablePlugin);
		
		// 注释插件
		PluginConfiguration tableCommentPlugin = new PluginConfiguration();
		tableCommentPlugin.setConfigurationType("com.morris.util.mybatis.plugin.TableCommentPlugin");
		context.addPluginConfiguration(tableCommentPlugin);
		
		// 分页插件
		PluginConfiguration rowBoundsPlugin = new PluginConfiguration();
		rowBoundsPlugin.setConfigurationType("org.mybatis.generator.plugins.RowBoundsPlugin");
		context.addPluginConfiguration(rowBoundsPlugin);
		
		// toString插件
		PluginConfiguration toStringPlugin = new PluginConfiguration();
		toStringPlugin.setConfigurationType("org.mybatis.generator.plugins.ToStringPlugin");
		context.addPluginConfiguration(toStringPlugin);
		
		// like插件 生成like查询
		PluginConfiguration likePlugin = new PluginConfiguration();
		likePlugin.setConfigurationType("org.mybatis.generator.plugins.CaseInsensitiveLikePlugin");
		context.addPluginConfiguration(likePlugin);
		
		// service接口插件
		PluginConfiguration serviceInterPluginPlugin = new PluginConfiguration();
		serviceInterPluginPlugin.setConfigurationType("com.morris.util.mybatis.plugin.ServiceInterPlugin");
		context.addPluginConfiguration(serviceInterPluginPlugin);
		
		// service接口插件
		PluginConfiguration serviceImplPluginPlugin = new PluginConfiguration();
		serviceImplPluginPlugin.setConfigurationType("com.morris.util.mybatis.plugin.ServiceImplPlugin");
		context.addPluginConfiguration(serviceImplPluginPlugin);
		
		// dao 插件
		PluginConfiguration daoPlugin = new PluginConfiguration();
		daoPlugin.setConfigurationType("com.morris.util.mybatis.plugin.DaoPlugin");
		context.addPluginConfiguration(daoPlugin);
		
		// 实体类的生成
		JavaModelGeneratorConfiguration model = new JavaModelGeneratorConfiguration();
		model.setTargetPackage(bean.getEntityPackage());
		model.setTargetProject(bean.getEntityPath());
		context.setJavaModelGeneratorConfiguration(model );
		
		// xml文件的生成
		SqlMapGeneratorConfiguration mapper = new SqlMapGeneratorConfiguration();
		mapper.setTargetPackage(bean.getXmlPackage());
		mapper.setTargetProject(bean.getXmlPath());
		context.setSqlMapGeneratorConfiguration(mapper);
		
		// dao的生成
		JavaClientGeneratorConfiguration dao = new JavaClientGeneratorConfiguration();
		dao.setTargetPackage(bean.getDaoPackage());
		dao.setTargetProject(bean.getDaoPath());
		dao.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(dao);
		
		// 添加多个表
		TableConfiguration tableConfiguration = null;
		for (Entry<String, String> entry : bean.getTableMap().entrySet()) {
			tableConfiguration = new TableConfiguration(context);
			tableConfiguration.setTableName(entry.getKey());
			tableConfiguration.setDomainObjectName(entry.getValue());
			context.addTableConfiguration(tableConfiguration);
		}
		
		context.addProperty("entityPackage", bean.getEntityPackage());
		context.addProperty("entityPath", bean.getEntityPath());
		context.addProperty("daoPackage", bean.getDaoPackage());
		context.addProperty("daoPath", bean.getDaoPath());
		context.addProperty("serviceInterPackage", bean.getServiceInterPackage());
		context.addProperty("serviceInterPath", bean.getServiceInterPath());
		context.addProperty("serviceImplPackage", bean.getServiceImplPackage());
		context.addProperty("serviceImplPath", bean.getServiceImplPath());
		
		config.addContext(context);
		DefaultShellCallback callback = new DefaultShellCallback(true);
		MyBatisGenerator myBatisGenerator;
		try {
			myBatisGenerator = new MyBatisGenerator(config,callback, null);
			ProgressCallback progressCallback = new MyProgressCallback();
			
			myBatisGenerator.generate(progressCallback);
		} catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
