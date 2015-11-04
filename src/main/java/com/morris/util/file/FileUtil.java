package com.morris.util.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件工具类
 * @author Morris
 *
 */
public class FileUtil {
	
	private static final transient Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	//----------------------删除文件（支持文件夹）begin-----------------------------
	/**
	 * 删除文件（支持文件夹）
	 * @param filePath 待删除的文件名（带路径）
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		return deleteFile(new File(filePath));
	}
	
	/**
	 * 删除文件（支持文件夹）
	 * @param dir 待删除的文件
	 * @return
	 */
	public static boolean deleteFile(File dir) {
		
		if(!dir.exists()) {
			logger.error("文件{}不存在", dir.getAbsoluteFile());
			return false;
		}
		
        if (dir.isDirectory()) {
            String[] children = dir.list();

            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteFile(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
	//----------------------删除文件（支持文件夹）end-----------------------------
	
	//----------------------拷贝文件 begin-----------------------------
	/**
	 * 拷贝文件（支持文件夹）
	 * @param sourcefileName 源文件名
	 * @param targetFileName 目标文件名
	 */
	public static void copyFile(String sourcefileName, String targetFileName) {
		File sourcefile = new File(sourcefileName);
		File targetFile = new File(targetFileName);
		copyFile(sourcefile, targetFile);
	}
	
	/**
	 * 拷贝文件（支持文件夹）
	 * @param sourcefile 源文件
	 * @param targetFileName 目标文件名
	 */
	public static void copyFile(File sourcefile, String targetFileName) {
		File targetFile = new File(targetFileName);
		copyFile(sourcefile, targetFile);
	}
	
	/**
	 * 拷贝文件（支持文件夹）
	 * @param sourcefileName 源文件名
	 * @param targetFileName 目标文件
	 */
	public static void copyFile(String sourcefileName, File targetFile) {
		File sourcefile = new File(sourcefileName);
		copyFile(sourcefile, targetFile);
	}
	
	/**
	 * 拷贝文件（支持文件夹）
	 * @param sourcefile 源文件
	 * @param targetFile 目标文件
	 */
	public static void copyFile(File sourcefile, File targetFile) {

		if (!sourcefile.exists()) {
			logger.error("文件{}不存在", sourcefile.getAbsoluteFile());
			return;
		}

		if (sourcefile.isDirectory()) {

			// 新建目标目录
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}

			// 获取源文件夹当下的文件或目录
			File[] file = sourcefile.listFiles();

			if (file == null) {
				return;
			}

			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 源文件
					File sourceChildFile = file[i];
					// 目标文件
					File targetChildFile = new File(targetFile.getAbsolutePath(), file[i].getName());

					copySingleFile(sourceChildFile, targetChildFile);
				}

				if (file[i].isDirectory()) {
					// 准备复制的源文件夹
					File sourceChildFile = new File(sourcefile, file[i].getName());
					// 准备复制的目标文件夹
					File targetChildFile = new File(targetFile, file[i].getName());

					copyFile(sourceChildFile, targetChildFile);
				}
			}

		} else {
			copySingleFile(sourcefile, targetFile);
		}

	}
	
	/**
	 * 拷贝单个文件，不支持文件夹
	 * @param sourcefile 源文件
	 * @param targetFile 目标文件
	 */
	private static void copySingleFile(File sourcefile, File targetFile) {

		try {
			if(!sourcefile.exists()) {
				logger.error("文件{}不存在", sourcefile.getAbsoluteFile());
				return;
			}
			File parentFile = targetFile.getParentFile();
			if(!parentFile.exists()) {
				parentFile.mkdirs();
			}

			// 新建文件输入流并对它进行缓冲
			FileInputStream input = new FileInputStream(sourcefile);
			BufferedInputStream inbuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			FileOutputStream out = new FileOutputStream(targetFile);
			BufferedOutputStream outbuff = new BufferedOutputStream(out);

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len = 0;
			while ((len = inbuff.read(b)) != -1) {
				outbuff.write(b, 0, len);
			}

			// 刷新此缓冲的输出流
			outbuff.flush();

			// 关闭流
			inbuff.close();
			outbuff.close();
			out.close();
			input.close();
		} catch (IOException e) {
			logger.error("拷贝文件异常：" + e);
		}

	}
	//----------------------拷贝文件 end-----------------------------
	
	//----------------------搜索文件 begin-----------------------------
	/**
	 * 在指定的目录下搜索指定文件名的文件, 默认文件是唯一的，找到第一个文件就返回，不再继续搜索
	 * @param targetPath 待搜索的路径
	 * @param fileName 待搜索的文件名
	 * @return 返回第一个找到的文件，如果没有找到则返回null
	 */
	public static File searchSingleFile(String targetPath, String fileName) {
		
		File targetFile = null;
		
		File targetDir = new File(targetPath);
		if(targetDir.exists()){
			
			File[] childFiles = targetDir.listFiles();
			
			if(childFiles != null) {
				for (File file : childFiles) {
					if(file.isDirectory()) {
						targetFile = searchSingleFile(file.getAbsolutePath(), fileName);
						if(targetFile != null){
							break;
						}
					}
					
					if(fileName.equals(file.getName())) {
						targetFile = file;
						break;
					}
					
				}
			}			
		}
		return targetFile;
	}
	
	/**
	 * 在指定的目录下搜索指定文件名的文件, 返回找到的所有的文件
	 * @param targetPath 待搜索的路径
	 * @param fileName 待搜索的文件名
	 * @return 返回所有找到的文件，如果没有找到则返回null
	 */
	public static void searchFile(String targetPath, String fileName, List<File> resultFiles) {
		
		File targetFile = null;
		
		File targetDir = new File(targetPath);
		if(targetDir.exists()){
			
			File[] childFiles = targetDir.listFiles();
			
			if(childFiles != null) {
				for (File file : childFiles) {
					if(file.isDirectory()) {
						targetFile = searchSingleFile(file.getAbsolutePath(), fileName);
						if(targetFile != null){
							resultFiles.add(targetFile);
						}
					}
					
					if(fileName.equals(file.getName())) {
						targetFile = file;
						resultFiles.add(targetFile);
					}
					
				}
			}			
		}
	}
	//----------------------搜索文件 end-----------------------------
	
	//----------------------下载远程文件并保存到本地 begin-----------------------------
	/**  
     * 下载远程文件并保存到本地  
     * @param remoteFile 远程文件（带路径）   
     * @param localFile 本地文件（带路径）  
     */
    public static void downloadFile(String remoteFile, String localFile)
    {
        try {
			URL url = new URL(remoteFile);
			downloadFile(url, localFile);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
          
    }
    
	/**  
     * 下载远程文件并保存到本地  
     * @param remoteFile 远程文件（带路径）   
     * @param localFile 本地文件（带路径）  
     */
    public static void downloadFile(URL remoteUrl, String localFile)
    {
        HttpURLConnection httpUrl = null;
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        File f = new File(localFile);
        
        File parentFile = f.getParentFile();
        
        if(!parentFile.exists()) {
        	parentFile.mkdirs();
        }
        
        try
        {
            httpUrl = (HttpURLConnection)remoteUrl.openConnection();
            httpUrl.connect();
            bis = new BufferedInputStream(httpUrl.getInputStream());
            bos = new BufferedOutputStream(new FileOutputStream(f));
            int len = 2048;
            byte[] b = new byte[len];
            while ((len = bis.read(b)) != -1)
            {
                bos.write(b, 0, len);
            }
            bos.flush();
            bis.close();
            httpUrl.disconnect();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                bis.close();
                bos.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
  //----------------------下载远程文件并保存到本地 end-----------------------------

}
