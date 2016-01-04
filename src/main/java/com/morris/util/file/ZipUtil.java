package com.morris.util.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * 压缩和解压缩文件
 * @author Morris
 *
 */
public class ZipUtil {

	// ------------------------------压缩文件begin----------------------------------------
	/**
	 * 执行压缩文件操作，支持文件和文件夹
	 * 
	 * @param srcPathName
	 *            被压缩的文件名（带路径），压缩到当前目录下，压缩文件名与被压缩文件名一致
	 */
	public static void zip(String srcPathName) {
		File srcFile = new File(srcPathName);
		zip(srcFile);
	}

	/**
	 * 执行压缩文件操作，支持文件和文件夹
	 * 
	 * @param srcFile
	 *            被压缩的文件，压缩到当前目录下，压缩文件名与被压缩文件名一致
	 */
	public static void zip(File srcFile) {
		if (!srcFile.exists()) {
			throw new RuntimeException(srcFile.getAbsolutePath() + "不存在！");
		}
		String desFileName = srcFile.getName();
		if (!srcFile.isDirectory()) {
			desFileName = desFileName.substring(0, desFileName.lastIndexOf("."));
		}
		desFileName += ".zip";
		String desFile = srcFile.getParent() + "/" + desFileName;
		zip(srcFile, desFile);
	}

	/**
	 * 执行压缩文件操作，支持文件和文件夹
	 * 
	 * @param srcPathName
	 *            被压缩的文件名（带路径）
	 * @param desPathName
	 *            压缩后的文件名（带路径）
	 */
	public static void zip(String srcPathName, String desPathName) {
		File srcFile = new File(srcPathName);
		zip(srcFile, desPathName);

	}

	/**
	 * 执行压缩文件操作，支持文件和文件夹
	 * 
	 * @param srcPathName
	 *            被压缩的文件
	 * @param desPathName
	 *            压缩后的文件名（带路径）
	 */
	public static void zip(File srcFile, String desPathName) {
		if (!srcFile.exists()) {
			throw new RuntimeException(srcFile.getAbsolutePath() + "不存在！");
		}
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(desPathName);
			CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());
			ZipOutputStream out = new ZipOutputStream(cos);
			zipByType(srcFile, out, "");
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 判断是目录还是文件，根据类型（文件/文件夹）执行不同的压缩方法
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private static void zipByType(File file, ZipOutputStream out, String basedir) {
		/* 判断是目录还是文件 */
		if (file.isDirectory()) {
			zipDirectory(file, out, basedir);
		} else {
			zipFile(file, out, basedir);
		}
	}

	/**
	 * 压缩一个目录
	 * 
	 * @param dir
	 * @param out
	 * @param basedir
	 */
	private static void zipDirectory(File dir, ZipOutputStream out, String basedir) {
		if (!dir.exists()) {
			return;
		}

		File[] files = dir.listFiles();
		for (int i = 0; i < files.length; i++) {
			/* 递归 */
			zipByType(files[i], out, basedir + dir.getName() + "/");
		}
	}

	/**
	 * 压缩一个文件
	 * 
	 * @param file
	 * @param out
	 * @param basedir
	 */
	private static void zipFile(File file, ZipOutputStream out, String basedir) {
		if (!file.exists()) {
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			out.putNextEntry(entry);
			int count;
			byte data[] = new byte[8192];
			while ((count = bis.read(data, 0, 8192)) != -1) {
				out.write(data, 0, count);
			}
			bis.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// ------------------------------压缩文件end------------------------------------------

	// ------------------------------解压缩文件begin--------------------------------------
	/**
	 * 解压缩文件操作，解压到到当前目录下
	 * 
	 * @param zipFile
	 *            被解压的压缩文件名（带路径）
	 */
	public static void unzip(String zipFileName) {
		File zipFile = new File(zipFileName);
		unzip(zipFile);
	}

	/**
	 * 解压缩文件操作，解压到到当前目录下
	 * 
	 * @param zipFile
	 *            被解压的压缩文件
	 */
	public static void unzip(File zipFile) {
		if (!zipFile.exists()) {
			throw new RuntimeException(zipFile.getAbsolutePath() + "不存在！");
		}
		String targetPath = zipFile.getParent() + "/";
		unzip(zipFile, targetPath);
	}

	/**
	 * 解压缩文件操作
	 * 
	 * @param srcPathName
	 *            被解压的压缩文件名（带路径）
	 * @param desPathName
	 *            解压后的目录
	 */
	public static void unzip(String zipFileName, String desPathName) {
		File zipFile = new File(zipFileName);
		unzip(zipFile, desPathName);
	}

	/**
	 * 解压缩文件操作
	 * 
	 * @param zipFile
	 *            被解压的压缩文件
	 * @param targetPath
	 *            解压后的目录
	 */
	public static void unzip(File zipFile, String targetPath) {
		if (!zipFile.exists()) {
			throw new RuntimeException(zipFile.getAbsolutePath() + "不存在！");
		}

		try {

			File pathFile = new File(targetPath);
			if (!pathFile.exists()) {
				pathFile.mkdirs();
			}
			ZipFile zip = new ZipFile(zipFile);
			for (Enumeration<? extends ZipEntry> entries = zip.entries(); entries.hasMoreElements();) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				
				String zipEntryName = entry.getName();
				InputStream in = zip.getInputStream(entry);
				String outPath = (targetPath + zipEntryName).replaceAll("\\*", "/");
				;
				// 判断路径是否存在,不存在则创建文件路径
				if (outPath.indexOf('/') > 0) {
					File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
					if (!file.exists()) {
						file.mkdirs();
					}
				}
				// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
				if (new File(outPath).isDirectory()) {
					continue;
				}

				OutputStream out = new FileOutputStream(outPath);
				byte[] buf1 = new byte[1024];
				int len;
				while ((len = in.read(buf1)) > 0) {
					out.write(buf1, 0, len);
				}
				in.close();
				out.close();
			}
			zip.close();

		} catch (IOException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// ------------------------------解压缩文件end----------------------------------------
}
