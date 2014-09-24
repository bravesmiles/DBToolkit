package com.xml.helper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Export the sql result as a excel file into a user-defined direction.
 * @author shihe
 */
public class TableToExcel {
	
	public static void export(String filename, List<Map<String, Object>> list) throws IOException, RowsExceededException, WriteException {
		int row = 0, col = 0;
		// 打开文件
		WritableWorkbook book = Workbook.createWorkbook(new File(filename));

		// 生成名第页工作表参数0表示第页
		WritableSheet sheet = book.createSheet("Result", 0);
		
		List<Integer> borderList = new ArrayList<Integer>();
		for(int i=0; i<list.get(0).size(); i++)
			borderList.add(-1);
		
		Integer data = 0;
		for (Iterator<Map<String, Object>> li = list.iterator(); li  
                .hasNext();) {  
			int i=0;
            Map<String, Object> m = li.next();  
            for (Iterator<Entry<String, Object>> mi = m.entrySet()  
                    .iterator(); mi.hasNext();) {  
                Entry<String, Object> e1 = mi.next();
                data = e1.getValue().toString().length()+3;
                if(data>borderList.get(i))
                	borderList.set(i, data);
                i++;
            } 
        } 
		
		//printf the body of hearder.
		for (Iterator<Map<String, Object>> li = list.iterator(); li  
                .hasNext();) {  
            Map<String, Object> m = li.next();  
            
            Set<String> keySet = m.keySet();
            Object[] keys = keySet.toArray();
            for(int i=0; i<keys.length; i++)
            {
            	Label label = new Label(row++, col, ""+keys[i]);
            	sheet.addCell(label);
            }
            col++;
            break;
        } 
		//printf the body of result.
		for (Iterator<Map<String, Object>> li = list.iterator(); li  
                .hasNext();) {  
            Map<String, Object> m = li.next();  
            row = 0;
            for (Iterator<Entry<String, Object>> mi = m.entrySet()  
                    .iterator(); mi.hasNext();) {  
                Entry<String, Object> e1 = mi.next();
//	                System.out.printf(": %s  ",e1.getValue());  
    			/*
    			 * 生成保存数字单元格 必须使用Number完整包路径否则有语法歧义 单元格位置第二列第行值789.123
    			 */
    			sheet.addCell(new Label(row++, col, ""+e1.getValue()));
            }
            col++;
        } 
		// 写入数据并关闭文件
		book.write();
		book.close();
	}
}
