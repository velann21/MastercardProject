package apk.bookmyShow.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelUtlityClass<T> {
	static Object classObject;
	static Class classdetails;
	static Method[] method;
	static Field[] field;
	static Object filePath;
	static String filePathValue;
	static Object sheetName;
	static String SheetNameValue;
	static Object rowIndex;
	static int rowValue;
	static Object columnIndex;
	static int columnValue;
	static FileInputStream fis;
	static Workbook wb;
	static ArrayList list = new ArrayList();
	static String value;
	static String[][] value1;
	static int excelLastUsedRow;
	static int excelLastUsedCell;
	static int rowRead;
	static int columnRead;

	// static String MethodName="fromExcel";
	public <T> T excelReading(String className, String MethodName, GetData data) throws Exception
	{
		try {
			classdetails = Class.forName(className);
			classObject = classdetails.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		filePath = classdetails.getDeclaredField("filepath").get(data);
		filePathValue = (String) filePath;
		System.out.println(filePath);

		sheetName = classdetails.getDeclaredField("sheetName").get(data);

		SheetNameValue = (String) sheetName;
		System.out.println(SheetNameValue);

		rowIndex = classdetails.getDeclaredField("rowIndex").getInt(data);

		rowValue = (Integer) rowIndex;
		System.out.println("rowValue is:"+rowValue);
		

		columnIndex = classdetails.getDeclaredField("columnIndex").getInt(data);

		columnValue = (Integer) columnIndex;
		System.out.println("columnvalue is :"+columnValue);
		System.out.println("filePath" + filePath);

		if (MethodName.equals("fromExcel") || MethodName.equals("fromExcelDp")) {

			Method mthd = classdetails.getDeclaredMethod(MethodName, String.class, String.class, int.class, int.class);
			String methods = mthd.getName();
			if (methods.equals("fromExcel") || methods.equals("fromExcelDp")) {

				File file = new File(filePathValue);
				try {
					fis = new FileInputStream(file);

				} catch (FileNotFoundException e) {

					e.printStackTrace();
				}
				try {
					wb = WorkbookFactory.create(fis);
				} catch (EncryptedDocumentException e) {

					e.printStackTrace();
				} catch (InvalidFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Sheet sheet = wb.getSheet(SheetNameValue);

				excelLastUsedRow = sheet.getLastRowNum() + 1;
				System.out.println(excelLastUsedRow);
				Row row = sheet.getRow(rowValue);

				excelLastUsedCell = row.getLastCellNum()+1 ;
				System.out.println(excelLastUsedCell);
				// *****************************************************************************************
				// This if block will work only if method is dataProvider
				if (methods.equals("fromExcelDp")) {
					value1 = new String[excelLastUsedRow][excelLastUsedCell];
					System.out.println(value1);
				}
				// ******************************************************************************************
				for (rowRead = rowValue; rowRead <= excelLastUsedRow - 1; rowRead++) {
					for (columnRead = columnValue; columnRead < excelLastUsedCell; columnRead++) {
						// ************************************************************************************
						// This if block will work only if method is
						// DataProvider
						if (methods.equals("fromExcelDp")) {
							try {
								Arrays.sort(value1);
								value1[rowRead][columnRead] = sheet.getRow(rowRead).getCell(columnRead).toString();
								System.out.println(value1[rowRead][columnRead]);
							} catch (java.lang.NullPointerException e) {

							}
						}

						if (methods.equals("fromExcel")) {
							try {
								value = sheet.getRow(rowRead).getCell(columnRead).toString();
								System.out.println(value);
								list.add(value);
								//Collections.sort(list, new Mycomp());
								
							} catch (java.lang.NullPointerException e) {

							}
						}

					}
				}

				System.out.println(list);
			}
		}
		if (MethodName.equals("fromExcel")) {

			return (T) list;
		} else {

			return (T) value1;
		}
	}

}

class Mycomp implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		String s1=(String)arg0;
		String s2=(String)arg1;
		return s2.compareTo(s1);
	}

	

}
