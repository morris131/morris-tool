package com.morris.util.json;
import net.sf.json.JSONObject;

public class ResultInfo {
	private int code;
	private String msg;
	private String remark;

	public ResultInfo() {

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return JSONObject.fromObject(this).toString();
	}
}
