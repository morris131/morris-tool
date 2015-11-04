package com.morris.json.bean;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class JsonBean implements Serializable  {
	public String resultcode;
	public String reason;
	public Result result;
	public int error_code;
	
	public Date birthday;

	public void setResultcode(String resultcode){
		this.resultcode = resultcode;
	}

	public String getResultcode(){
		return resultcode;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return reason;
	}

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setError_code(Integer error_code){
		this.error_code = error_code;
	}

	public Integer getError_code(){
		return error_code;
	}
	
	

	@Override
	public String toString() {
		return "JsonBean [resultcode=" + resultcode + ", reason=" + reason
				+ ", result=" + result + ", error_code=" + error_code + "]";
	}
	
	

}
