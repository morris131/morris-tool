package com.morris.util.mybatis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
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

public class MybatisUtilTest{

	@Test
	public void test(String[] args) throws InvalidConfigurationException, SQLException, IOException, InterruptedException {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		Configuration config = new Configuration();
		
		Context context = new Context(null);
		
		context.setId("Table2");
		
		// 数据库连接
		JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
		jdbc.setDriverClass("oracle.jdbc.driver.OracleDriver");
		jdbc.setConnectionURL("jdbc:oracle:thin:@172.16.92.24:1521:neikong");
		jdbc.setUserId("tcpmain1");
		jdbc.setPassword("tcpmain9");
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
		model.setTargetPackage("model");
		model.setTargetProject("src/main/java");
		context.setJavaModelGeneratorConfiguration(model );
		
		// xml文件的生成
		SqlMapGeneratorConfiguration mapper = new SqlMapGeneratorConfiguration();
		mapper.setTargetPackage("xml");
		mapper.setTargetProject("src/main/java");
		context.setSqlMapGeneratorConfiguration(mapper);
		
		// dao的生成
		JavaClientGeneratorConfiguration dao = new JavaClientGeneratorConfiguration();
		dao.setTargetPackage("dao");
		dao.setTargetProject("src/main/java");
		dao.setConfigurationType("XMLMAPPER");
		context.setJavaClientGeneratorConfiguration(dao);
//		
		
		// 表的配置
		TableConfiguration tableConfiguration = new TableConfiguration(context);
		tableConfiguration.setTableName("REFUND_AGENT_ORDER");
		tableConfiguration.setDomainObjectName("RefundAgentOrder");
		context.addTableConfiguration(tableConfiguration);
		
		
		config.addContext(context);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		
		ProgressCallback progressCallback = new MyProgressCallback();
		
		myBatisGenerator.generate(progressCallback);
	}

}
