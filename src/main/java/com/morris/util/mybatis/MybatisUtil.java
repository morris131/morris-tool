package com.morris.util.mybatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

public class MybatisUtil {
	
	public static void generate(MybatisBean bean) {
	
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		Configuration config = new Configuration();
		
		Context context = new Context(null);
		
		context.setId("Table2");
		
		// 数据库连接
		JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
		jdbc.setDriverClass(bean.getDriverClass());
		jdbc.setConnectionURL(bean.getConnectionUrl());
		jdbc.setUserId(bean.getUserName());
		jdbc.setPassword(bean.getPassword());
		context.setJdbcConnectionConfiguration(jdbc);
		
		// 去掉注释
		CommentGeneratorConfiguration comment = new CommentGeneratorConfiguration();
		comment.addProperty("suppressAllComments", "true");
		context.setCommentGeneratorConfiguration(comment);
		
		// 插件
		// 序列化插件
		PluginConfiguration serializablePlugin = new PluginConfiguration();
		serializablePlugin.setConfigurationType("com.morris.mybatis.plugins.SerializablePlugin");
		context.addPluginConfiguration(serializablePlugin);
		
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
		
		config.addContext(context);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator;
		try {
			myBatisGenerator = new MyBatisGenerator(config,callback, warnings);
			ProgressCallback progressCallback = new MyProgressCallback();
			
			myBatisGenerator.generate(progressCallback);
		} catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
