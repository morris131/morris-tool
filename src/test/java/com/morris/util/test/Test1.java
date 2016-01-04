package com.morris.util.test;

public class Test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String str = "andImportTimeGreaterThan";
		
		boolean matches = str.matches("^and\\w+Than$");
		
		System.out.println(matches);

	}

}
