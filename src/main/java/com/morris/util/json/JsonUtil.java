package com.morris.util.json;

import java.util.ArrayList;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

public class JsonUtil {

	/**
	 * 将中文标点符号转换为英文标点符号包括，【】｛｝’‘”“：
	 * 
	 * @param json
	 * @return
	 */
	public String cnChar2EnChar(String json) {
		return json.replaceAll("，", ",").replaceAll("【", "[")
				.replaceAll("】", "]").replaceAll("｛", "{").replaceAll("｝", "}")
				.replaceAll("’", "'").replaceAll("‘", "'")
				.replaceAll("“", "\"").replaceAll("”", "\"")
				.replaceAll("：", ":");
	}

	/**
	 * 将Unicode码转成中文
	 * 
	 * @param json
	 * @return
	 */
	public String unicode2Chinese(String json) {

		char aChar;
		int len = json.length();
		StringBuffer outBuffer = new StringBuffer(len);

		for (int x = 0; x < len;) {

			aChar = json.charAt(x++);
			if (aChar == '\\') {

				aChar = json.charAt(x++);

				if (aChar == 'u') {

					// Read the xxxx
					int value = 0;
					for (int i = 0; i < 4; i++) {

						aChar = json.charAt(x++);

						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}

					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';

					else if (aChar == 'n')

						aChar = '\n';

					else if (aChar == 'f')

						aChar = '\f';

					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}

	/**
	 * 把中文转成Unicode码
	 * 
	 * @param json
	 * @return
	 */
	public String chinese2Unicode(String json) {

		String result = "";
		for (int i = 0; i < json.length(); i++) {
			int chr1 = (char) json.charAt(i);
			if (chr1 >= 19968 && chr1 <= 171941) {// 汉字范围 \u4e00-\u9fa5 (中文)
				result += "\\u" + Integer.toHexString(chr1);
			} else {
				result += json.charAt(i);
			}
		}
		return result;
	}

	/**
	 * 验证json是否合法
	 * 
	 * @param json
	 * @return 如果合法则返回JSON_OK 如果不合法则返回错误信息,如果为""则返回""
	 */
	public String validate(String json) {

		ResultInfo resultInfo = new ResultInfo();

		if (null == json || "".equals(json)) {
			resultInfo.setCode(0);
			resultInfo.setMsg("");
			resultInfo.setRemark("");
			return resultInfo.toString();
		}
		try {
			JSONObject.fromObject(json);
		} catch (JSONException e) {
			resultInfo.setCode(0);
			resultInfo.setMsg("Json string is validate fail!");
			resultInfo.setRemark(e.getMessage());
			return resultInfo.toString();
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("Json string is validate success!");
		resultInfo.setRemark("");
		return resultInfo.toString();
	}

	/**
	 * 去除转义json，将"和\去除转义,并将java字符串中json还原成json格式
	 * 
	 * @param json
	 * @return
	 */
	public String removeEscapeJson(String json) {

		// 删除tab
		json  = json.replaceAll("\t", "");
		
		StringBuilder sb = new StringBuilder();
		
		String[] split = json.split("\n");
		
		for (int i = 0; i < split.length - 1;i++) {
			String string = split[i];
			sb.append(string.substring(1, string.length() - 6) + "\n");
		}
		
		String endLine = split[split.length - 1];
		endLine = endLine.substring(1, endLine.length()-3);
		sb.append(endLine);
		
		return sb.toString().replaceAll("\\\\\"", "\"").replaceAll("\\\\\\\\", "\\\\");
	}

	/**
	 * 转义json，将"和\转义，将json拼接成java字符串
	 * 
	 * @param json
	 * @return
	 */
	public String escapeJson(String json) {
		
		json = json.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"");
		
		System.out.println(json);
		
		StringBuilder sb = new StringBuilder();
		
		String[] split = json.split("\n");
		
		for (String string : split) {
			sb.append("\"" + string + "\\n\" + \n");
		}
		
		return sb.substring(0, sb.length() - 4);
	}

	/**
	 * 压缩json，取出所有的空白字符，包括空格，制表符，换行符
	 * 
	 * @param json
	 * @return
	 */
	public String compressionJson(String json) {
		return json.replaceAll("\\s", "");
	}

	/**
	 * json字符串的格式化
	 * 
	 * @param json
	 *            需要格式的json串
	 * @param fillStringUnit每一层之前的占位符号比如空格
	 *            制表符
	 * @return
	 */
	public String formatJson(String json, String fillStringUnit) {

		// 格式化前先判断json是否合法
		String validate = validate(json);

		JSONObject jsonObject = JSONObject.fromObject(validate);
		String code = jsonObject.getString("code");

		// 如果校验不合法
		if ("0".equals(code)) {
			return validate;
		}

		ResultInfo resultInfo = new ResultInfo();

		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			// 预读取
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}
		}

		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < tokenList.size(); i++) {

			String token = tokenList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count, fillStringUnit);
				continue;
			} else if (token.equals(":")) {
				buf.append(token).append(" ");
				continue;
			} else if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			} else if (token.equals("}")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			} else if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count, fillStringUnit);
				}
				continue;
			} else if (token.equals("]")) {
				count--;
				doFill(buf, count, fillStringUnit);
				buf.append(token);
				continue;
			}

			buf.append(token);

		}

		resultInfo.setCode(1);
		resultInfo.setMsg("Json format success!");
		resultInfo.setRemark(buf.toString());

		return resultInfo.toString();
	}

	/**
	 * 获取元素
	 * 
	 * @param json
	 * @return
	 */
	private String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);

			if (!isInYinHao
					&& (token.equals(":") || token.equals("{")
							|| token.equals("}") || token.equals("[")
							|| token.equals("]") || token.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(token);
				}

				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(token);
		}
		return buf.toString();
	}

	/**
	 * 填充空白
	 * 
	 * @param buf
	 * @param count
	 * @param fillStringUnit
	 */
	private void doFill(StringBuilder buf, int count, String fillStringUnit) {
		buf.append("\n");
		for (int i = 0; i < count; i++) {
			buf.append(fillStringUnit);
		}
	}

}
