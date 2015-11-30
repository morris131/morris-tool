package com.morris.util.excel;

import java.io.OutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtil {
	
	 public static <T> void listToExcel(List<T> list, LinkedHashMap<String,String> fieldMap,String sheetName,  
	            int sheetSize,  
	            OutputStream out  
	            ){  
		 
		/* 
		 Workbook workbook = WorkbookFactory.
	          
	        //创建工作簿并发送到OutputStream指定的地方  
	        WritableWorkbook wwb;  
	        try {  
	            wwb = Workbook.createWorkbook(out);  
	              
	            //因为2003的Excel一个工作表最多可以有65536条记录，除去列头剩下65535条  
	            //所以如果记录太多，需要放到多个工作表中，其实就是个分页的过程  
	            //1.计算一共有多少个工作表  
	            double sheetNum=Math.ceil(list.size()/new Integer(sheetSize).doubleValue());  
	              
	            //2.创建相应的工作表，并向其中填充数据  
	            for(int i=0; i<sheetNum; i++){  
	                //如果只有一个工作表的情况  
	                if(1==sheetNum){  
	                    WritableSheet sheet=wwb.createSheet(sheetName, i);  
	                    fillSheet(sheet, list, fieldMap, 0, list.size()-1);  
	                  
	                //有多个工作表的情况  
	                }else{  
	                    WritableSheet sheet=wwb.createSheet(sheetName+(i+1), i);  
	                      
	                    //获取开始索引和结束索引  
	                    int firstIndex=i*sheetSize;  
	                    int lastIndex=(i+1)*sheetSize-1>list.size()-1 ? list.size()-1 : (i+1)*sheetSize-1;  
	                    //填充工作表  
	                    fillSheet(sheet, list, fieldMap, firstIndex, lastIndex);  
	                }  
	            }  
	              
	            wwb.write();  
	            wwb.close();  
	          
	        }catch (Exception e) {  
	            e.printStackTrace();  
	            //如果是ExcelException，则直接抛出  
	            if(e instanceof ExcelException){  
	                throw (ExcelException)e;  
	              
	            //否则将其它异常包装成ExcelException再抛出  
	            }else{  
	                throw new ExcelException("导出Excel失败");  
	            }  
	        }  
	              
	    }  
*/
	 }
}
