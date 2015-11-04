package com.morris.json.bean;
import java.io.Serializable;
import java.util.List;
public class Data implements Serializable  {
	public String MCC;
	public String MNC;
	public String LAC;
	public String CELL;
	public String LNG;
	public String LAT;
	public String OO_LNG;
	public String OO_LAT;
	public String PRECISION;
	public String ADDRESS;

	public void setMCC(String MCC){
		this.MCC = MCC;
	}

	public String getMCC(){
		return MCC;
	}

	public void setMNC(String MNC){
		this.MNC = MNC;
	}

	public String getMNC(){
		return MNC;
	}

	public void setLAC(String LAC){
		this.LAC = LAC;
	}

	public String getLAC(){
		return LAC;
	}

	public void setCELL(String CELL){
		this.CELL = CELL;
	}

	public String getCELL(){
		return CELL;
	}

	public void setLNG(String LNG){
		this.LNG = LNG;
	}

	public String getLNG(){
		return LNG;
	}

	public void setLAT(String LAT){
		this.LAT = LAT;
	}
	
	

	@Override
	public String toString() {
		return "Data [MCC=" + MCC + ", MNC=" + MNC + ", LAC=" + LAC + ", CELL="
				+ CELL + ", LNG=" + LNG + ", LAT=" + LAT + ", OO_LNG=" + OO_LNG
				+ ", OO_LAT=" + OO_LAT + ", PRECISION=" + PRECISION
				+ ", ADDRESS=" + ADDRESS + "]";
	}

	public String getLAT(){
		return LAT;
	}

	public void setOO_LNG(String OO_LNG){
		this.OO_LNG = OO_LNG;
	}

	public String getOO_LNG(){
		return OO_LNG;
	}

	public void setOO_LAT(String OO_LAT){
		this.OO_LAT = OO_LAT;
	}

	public String getOO_LAT(){
		return OO_LAT;
	}

	public void setPRECISION(String PRECISION){
		this.PRECISION = PRECISION;
	}

	public String getPRECISION(){
		return PRECISION;
	}

	public void setADDRESS(String ADDRESS){
		this.ADDRESS = ADDRESS;
	}

	public String getADDRESS(){
		return ADDRESS;
	}

}
