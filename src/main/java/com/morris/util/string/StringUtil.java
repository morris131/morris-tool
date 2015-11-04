package com.morris.util.string;

public class StringUtil {
	
	/**
	 * 将字符串每行首部拼接前缀
	 * @param str 待拼接的字符串
	 * @param prefix 前缀
	 * @return
	 */
	public static String appendPrefix(String str, String prefix){
		
		StringBuilder sb  = new StringBuilder();
		
		String[] splits = str.split("\n");
		
		for (String string : splits) {
			sb.append(prefix + string + "\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 将字符串每行首部拼接后缀
	 * @param str 待拼接的字符串
	 * @param suffix 后缀
	 * @return
	 */
	public static String appendSuffix(String str, String suffix){
		StringBuilder sb  = new StringBuilder();
		
		String[] splits = str.split("\n");
		
		for (String string : splits) {
			sb.append(string + suffix + "\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 将字符串每行首部拼接前缀和后缀
	 * @param str 待拼接的字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 */
	public static String append(String str, String prefix, String suffix){
		
		StringBuilder sb  = new StringBuilder();
		
		String[] splits = str.split("\n");
		
		for (String string : splits) {
			sb.append(prefix + string + suffix + "\n");
		}
		
		return sb.toString();
	}
	
	/**
	 * 将字符串每行首部移除前缀
	 * @param str 待移除的字符串
	 * @param prefix 前缀
	 * @return
	 */
	public static String removePrefix(String str, String prefix){
		
		StringBuilder sb  = new StringBuilder();
		String[] splits = str.split("\n");
		
		for (String string : splits) {
			if(string.startsWith(prefix)){
				sb.append(string.replaceFirst(prefix, "") + "\n");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 将字符串每行首部移除后缀
	 * @param str 待移除的字符串
	 * @param suffix 后缀
	 * @return
	 */
	public static String removeSuffix(String str, String suffix){
		StringBuilder sb  = new StringBuilder();
		
		String[] splits = str.split("\n");
		
		for (String string : splits) {
			if(str.endsWith(suffix)){
				sb.append(string.substring(0, str.lastIndexOf(suffix)) + "\n");
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * 将字符串每行首部移除前缀和后缀
	 * @param str 待移除的字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 */
	public static String remove(String str, String prefix, String suffix){
		
		StringBuilder sb  = new StringBuilder();
		
		String[] splits = str.split("\n");
		
		for (String string : splits) {
			sb.append(string.replaceFirst(prefix, "").substring(0, str.lastIndexOf(suffix)) + suffix + "\n");
		}
		
		return sb.toString();
	}

}
