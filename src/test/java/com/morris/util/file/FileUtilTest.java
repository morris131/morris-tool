package com.morris.util.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * 文件工具测试类
 * @author Morris
 *
 */
public class FileUtilTest {
	
	@Test
	public void testDeleteFile() {
		
		String filePath = "E:\\aa\\bb";
		FileUtil.deleteFile(filePath);
		
		//File dir = new File(filePath);
		//FileUtil.deleteFile(dir);
	}
	
	@Test
	public void testCopyFile() {
		String sourceFileName = "E:\\aa\\bb\\03-02.png";
		FileUtil.copyFile(sourceFileName, "e:\\aa\\cc\\aa.png");
	}
	
	@Test
	public void testSearchSingleFile() {
		
		String fileName = "03-02.png";
		File singleFile = FileUtil.searchSingleFile("e:\\aa", fileName);
		System.out.println(singleFile.getAbsolutePath());
	}
	
	@Test
	public void testSearchFile() {
		
		String fileName = "03-02.png";
		List<File> resultFiles = new ArrayList<File>();
		FileUtil.searchFile("e:\\aa", fileName, resultFiles);
		for (File file : resultFiles) {
			System.out.println(file.getAbsolutePath());
		}
	}
	
	@Test
	public void testDownloadFile() {
		String url = "http://www.gssok.com/updownFiles/151028/1510281439156241.docx";
		String targetFileName = url.substring(url.lastIndexOf("/"));
		FileUtil.downloadFile(url, "e:\\aa\\"+targetFileName);
	}

}
