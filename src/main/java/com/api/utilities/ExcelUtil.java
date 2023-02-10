package com.api.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hamcrest.core.IsNull;

public class ExcelUtil {

	private XSSFWorkbook wb;
	private String wbpath;
	private XSSFSheet st;
	private XSSFRow rw;
	private XSSFCell cl;
	public ExcelUtil(String path) {
		wbpath = path;
		try {
			wb = new XSSFWorkbook(new FileInputStream(wbpath));
			evaluateFormula();
			//st = wb.getSheetAt(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void setAvtiveSheet(String sheetname) {
		st  =wb.getSheet(sheetname);
	}
	public XSSFWorkbook getWorbook() {
		return wb;
	}
	public void evaluateFormula() {
		FormulaEvaluator eval = wb.getCreationHelper().createFormulaEvaluator();
		eval.evaluateAll();
	}
	public String getParam(String paramname,int rownum) {
		evaluateFormula();
		int paramIndex=0;
		rw = st.getRow(0);
		for (int i=0;i<rw.getLastCellNum();i++) {
			if(st.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(paramname)) {
				paramIndex=i;
			}
		}
		if(st.getRow(rownum).getCell(paramIndex) == null) {
			return "";
		}else if(st.getRow(rownum).getCell(paramIndex).getCellType().name().equalsIgnoreCase("STRING")) {
			return st.getRow(rownum).getCell(paramIndex).getStringCellValue();
		}else {
			return st.getRow(rownum).getCell(paramIndex).getRawValue();
		}
	}
	public String getParam(int columnnum, int rownum) {
		if(st.getRow(rownum).getCell(columnnum) == null) {
			return "";
		}else if(st.getRow(rownum).getCell(columnnum).getCellType().name().equalsIgnoreCase("STRING")) {
			return st.getRow(rownum).getCell(columnnum).getStringCellValue();
		}else {
			return st.getRow(rownum).getCell(columnnum).getRawValue();
		}
	}
	public void setParam(String paramname, int rownum, Object paramValue) {
		int paramIndex=0;
		rw = st.getRow(0);
		for (int i=0;i<rw.getLastCellNum();i++) {
			if(st.getRow(0).getCell(i).getStringCellValue().equalsIgnoreCase(paramname)) {
				paramIndex=i;
			}
		}
		if(st.getRow(rownum)==null) {
			st.createRow(rownum);
		}
		XSSFCell cell = st.getRow(rownum).createCell(paramIndex);
		if(paramValue instanceof String) {
			cell.setCellValue((String)paramValue);
		}else if(paramValue instanceof Integer) {
			cell.setCellValue((Integer)paramValue);	
		}else if(paramValue instanceof Double) {
			cell.setCellValue((Double)paramValue);	
		}else if(paramValue instanceof Float) {
			cell.setCellValue((Float)paramValue);	
		}
		evaluateFormula();
		//Not writing ot the file just updating to the workbook object
		/*FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(wbpath);
			wb.write(outputStream);
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	public void setParam(int columnnum, int rownum, Object paramValue) {
		int paramIndex=columnnum;
		if(st.getRow(rownum)==null) {
			st.createRow(rownum);
		}
		XSSFCell cell = st.getRow(rownum).createCell(paramIndex);
		if(paramValue instanceof String) {
			cell.setCellValue((String)paramValue);
		}else if(paramValue instanceof Integer) {
			cell.setCellValue((Integer)paramValue);	
		}else {
			cell.setCellValue((Double)paramValue);	
		}
		evaluateFormula();
		/*FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(wbpath);
			wb.write(outputStream);
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/

	}
	/**
	 * displays the parameter data in tabular with the number of rows and columns specified
	 * @param colnames 
	 * comma separated column names
	 * @param rownums
	 */
	public void DisplayParamTable(String colnames,int rownums) {
		evaluateFormula();
		String[] cols = colnames.split(",");

		for(int i=0;i<=rownums;i++) {
			for(int j=0;j<cols.length;j++) {
				System.out.println("|");
				if(i==0) {
					System.out.println(cols[j]);
				}else {
					System.out.println(this.getParam(cols[j], i));
				}
				System.out.println("|");
			}
		}
	}
	public int getNumberOfDataRows() {
		return st.getLastRowNum();
	}
	public void closeWorkbook() {
		try {
			if(isWorkBookOpen()) {
				CopyWorkbook("temp.xlsx");
				wb.close();
				CopyWorkbook("temp.xlsx",wbpath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Boolean isWorkBookOpen() throws IOException {
		File file = new File(wbpath);
		FileChannel channel  = new RandomAccessFile(file, "rw").getChannel();
		FileLock lock = channel.lock();
		try {
			lock = channel.tryLock();
			lock.release();
			channel.close();
			return false;
		} catch (OverlappingFileLockException e) {
			lock.release();
			channel.close();
			return true;
		}
	}
	public static void CreateWorkbook(String filePath){
		FileOutputStream outputStreamTemp;
		File file = new File(filePath);
		XSSFWorkbook temp = new XSSFWorkbook();
		try {
			outputStreamTemp  =new FileOutputStream(file);
			temp.write(outputStreamTemp);
			outputStreamTemp.close();
			temp.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void CopyWorkbook(String destinationFilePath) {
		FileOutputStream outputStreamTemp;
		File file = new File(destinationFilePath);
		try {
			outputStreamTemp  =new FileOutputStream(file);
			wb.write(outputStreamTemp);
			outputStreamTemp.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void CopyWorkbook(String sourceFilePath,String destinationFilePath) {
		XSSFWorkbook sWB;
		FileOutputStream outputStreamTemp;
		File file = new File(destinationFilePath);
		try {
			sWB = new XSSFWorkbook(new File(sourceFilePath));
			outputStreamTemp  =new FileOutputStream(file);
			sWB.write(outputStreamTemp);
			outputStreamTemp.close();
			sWB.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
