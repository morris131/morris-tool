package com.morris.json.bean;
import java.io.Serializable;
import java.util.List;
public class Result implements Serializable  {
	public List<Data> data;


	public void setData(List<Data> data){
		this.data = data;
	}

	public List<Data> getData(){
		return data;
	}

	@Override
	public String toString() {
		return "Result [data=" + data + "]";
	}
	
	
	

}
