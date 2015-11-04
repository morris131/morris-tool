package com.morris.util.json;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.JDBCConnectionConfiguration;
import org.mybatis.generator.config.JavaClientGeneratorConfiguration;
import org.mybatis.generator.config.JavaModelGeneratorConfiguration;
import org.mybatis.generator.config.SqlMapGeneratorConfiguration;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;

public class MybatisUtil {

	/**
	 * mybatis 自动生成xml,entity，dao
	 * @param params
	 * @throws SQLException
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws InvalidConfigurationException
	 */
	public void generate(Map<String, String> params) throws SQLException, IOException,
			InterruptedException, InvalidConfigurationException {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;
		Configuration config = new Configuration();
		
		Context context = new Context(null);
		context.setId("table2bean");
		
		// 数据库连接
		JDBCConnectionConfiguration jdbc = new JDBCConnectionConfiguration();
		jdbc.setDriverClass(params.get("driverClass"));
		jdbc.setConnectionURL(params.get("connectionUrl"));
		jdbc.setUserId(params.get("userId"));
		jdbc.setPassword(params.get("password"));
		context.setJdbcConnectionConfiguration(jdbc);
		
		// table
		String[] tableNames = params.get("tablesNames").split(",");
		// 多个表
		for (String tableName : tableNames) {
			TableConfiguration table = new TableConfiguration(context);
			table.setTableName(tableName);
			table.setDomainObjectName(getDomainObjectName(tableName));
			context.addTableConfiguration(table);
		}
		
		// xml
		SqlMapGeneratorConfiguration xml = new SqlMapGeneratorConfiguration();
		xml.setTargetPackage(params.get("xml.package"));
		xml.setTargetProject(params.get("xml.project"));
		context.setSqlMapGeneratorConfiguration(xml);
		
		// entity
		JavaModelGeneratorConfiguration model = new JavaModelGeneratorConfiguration();
		model.setTargetPackage(params.get("xml.package"));
		model.setTargetProject(params.get("xml.project"));
		context.setJavaModelGeneratorConfiguration(model);
		
		// dao
		JavaClientGeneratorConfiguration dao = new JavaClientGeneratorConfiguration();
		dao.setTargetPackage(params.get("dao.package"));
		dao.setTargetProject(params.get("dao.project"));
		context.setJavaClientGeneratorConfiguration(dao);
		
		config.getContexts().add(context);


		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		myBatisGenerator.generate(null);
	}
	
	/**
	 * 根据表名获得实体类名 去掉下划线，驼峰命名
	 * @param tableName
	 * @return
	 */
	private String getDomainObjectName(String tableName){
		String domainName = "";
		
		String[] split = tableName.split("_");
		
		for (String str : split) {
			domainName += upperFirst(str);
		}
			
		return domainName;
	}
	
	/**
	 * 将字符串首字母大写
	 * @param c
	 * @return
	 */
	private String upperFirst(String c){
		return c.substring(0, 1).toUpperCase()+c.substring(1).toLowerCase();
	}

}
