package apk.bookmyShow.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

//import WhatsApp.interfaces.GetDataInterface;
import apk.bookmyShow.annotations.Constructor;
import apk.bookmyShow.annotations.VariablesFor;
import apk.bookmyShow.interfaces.GetDataInterface;

public class GetData extends Exception implements GetDataInterface {

	@VariablesFor(Author = "Velan", VariableFor = "ExceptionString")
	String ExceptionValue = null;

	@VariablesFor(Author = "Velan", VariableFor = "fromProperties()")
	File file = null;
	FileInputStream fis = null;
	Properties prop = null;

	@VariablesFor(Author = "Velan", VariableFor = "fromExcel()")
	public String filepath = null;
	public String sheetName = null;
	public int rowIndex = 0;
	public int columnIndex = 0;
	static GetData g = null;

	private Workbook create;

	private Sheet sheet;

	private Row row;

	private Cell cell;

	@Constructor(Author = "Velan", ConstructorType = "DefaultConst")
	public GetData()

	{

	}

	@Constructor(Author = "Velan", ConstructorType = "Argumentized")
	public GetData(String ExceptionValue) {
		this.ExceptionValue = ExceptionValue;
	}

	public static GetData newInstance() {
		return new GetData();
	}

	public String toString()

	{
		return ExceptionValue;

	}

	public String fromProperties(String fileName, String Keys, String className) {
		String fileName1 = fileName.trim();
		if (fileName1 == null || fileName1 == "") {

			try {
				throw new GetData("PropertyFileEmptyExceptionIn" + Handler.ClassDetails(className));
			} catch (GetData e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else {
			if (fileName1.endsWith(".properties")) {

			} else {
				try {
					throw new GetData("FileIsNotPropertiesFileExceptionIn" + Handler.ClassDetails(className));
				} catch (GetData e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		String value = null;

		file = new File(fileName);
		try {
			fis = new FileInputStream(file);

		} catch (Exception e) {
			System.out.println(e);
		}
		prop = new Properties();

		try {
			prop.load(fis);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		value = prop.getProperty(Keys);
		System.out.println("valueis" + value);
		if (value == null || value == "" || value.isEmpty()) {
			System.out.println(value);
			try {
				throw new GetData("nullValueKeyExceptionIn" + Handler.ClassDetails(className));
			} catch (GetData e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return value;

	}

	public ArrayList fromExcel(String filePath, String sheetName, int rowIndex, int cellIndex) throws Exception {
		this.filepath = filePath;
		this.sheetName = sheetName;
		this.rowIndex = rowIndex;
		this.columnIndex = cellIndex;
		ExcelUtlityClass<ArrayList> class1 = new ExcelUtlityClass<ArrayList>();
		ArrayList list = class1.excelReading("apk.bookmyShow.helper.GetData", "fromExcel", g);

		return list;
	}

	public ArrayList fromExcelRangeData(String filePath, String sheetName, int startRow, int startColumn, int endRow,
			int endColumn) throws Exception {
		ArrayList list = new ArrayList();
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		create = WorkbookFactory.create(fis);
		sheet = create.getSheet(sheetName);
		String cellvalues = "";
		for (int startRead = startRow; startRead <= endRow; startRead++) {
			for (int columnRead = startColumn; columnRead <= endColumn; columnRead++) {
				try {
					cellvalues = sheet.getRow(startRead).getCell(columnRead).toString();
					list.add(cellvalues);
				} catch (Exception e) {
					list.add(cellvalues);
				}

			}
		}
		return list;

	}

	public String fromExcelOneData(String filePath, String sheetName, int rowNo, int intCellNo) throws Exception {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		create = WorkbookFactory.create(fis);
		sheet = create.getSheet(sheetName);
		String value = sheet.getRow(rowNo).getCell(intCellNo).toString();
		return value;
	}

	public int fromExcelusedRangeRow(String filePath, String sheetName, int columnNumber) throws Exception {
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		create = WorkbookFactory.create(fis);
		sheet = create.getSheet(sheetName);
		int lastrow = sheet.getLastRowNum();
		return lastrow;

	}

	// @DataProvider(name="AllDatasDp")
	public static String[][] fromExcelDp(String filePath, String sheetName, int rowIndex, int cellIndex)
			throws Exception {
		g.filepath = filePath;
		g.sheetName = sheetName;
		g.rowIndex = rowIndex;
		g.columnIndex = cellIndex;
		ExcelUtlityClass<String[][]> class1 = new ExcelUtlityClass<String[][]>();
		String[][] list = class1.excelReading("apk.bookmyShow.helper.GetData", "fromExcelDp", g);
		System.out.println("In main Class:" + list[1][1]);

		return null;

	}

	@Override
	public String fromExcelOneData() {
		// TODO Auto-generated method stub
		return null;
	}

	//@SuppressWarnings("rawtypes")
	public ArrayList fromExcelValueExtractor(String filePath,String configsheet,String className,String inputSheet,GetData g,int valuePickerColumn) throws Exception {
		//g = new GetData();
		
		//System.out.println(g.fromExcelusedRangeRow("C:\\Users\\Indumathi\\Desktop\\Velan.xls","ConfigSheet", 0));
		//@SuppressWarnings("rawtypes")
		ArrayList list=g.fromExcelRangeData(filePath,configsheet, 0, 0, g.fromExcelusedRangeRow(filePath,configsheet, 0), 0);
		
		if(list.contains(className)==true)
		{
			 int index=list.indexOf(className);
			 String startrowString=g.fromExcelOneData(filePath,configsheet, index, 1);
		     //System.out.println("Stringnumber"+startrowString);
		     String endrowString=g.fromExcelOneData(filePath,configsheet, index, 2);
		     //System.out.println("StringEndcol"+endrowString);
		     Integer startrow=Integer.parseInt(startrowString);
		     //System.out.println("number"+startrow);
		     Integer endRow=Integer.parseInt(endrowString);
		     //System.out.println("endRow"+endRow);
		     ArrayList list1=g.fromExcelRangeData(filePath,inputSheet, 0, 0, g.fromExcelusedRangeRow(filePath,inputSheet, 0), 0);
		     //System.out.println("SecondList is"+list1);
		     
		     if(list1.contains(className));
		     {
		       int index2=list1.indexOf(className);
		       int startInput=index2+startrow;
		       //System.out.println("startInput"+startInput);
		       int endRowInput=index2+endRow;
		       //System.out.println("endRowInput"+endRowInput);
		       ArrayList list3= g.fromExcelRangeData(filePath,inputSheet, startInput, valuePickerColumn,endRowInput , valuePickerColumn);
		       //System.out.println(list3);
		       return list3;
		     }
		     
		}
		return null;
		/*public static void main(String[] args) throws Exception {
		
		GetData g1=new GetData();
		g1.fromExcelValueExtractor("C:\\Users\\Indumathi\\Desktop\\Velan.xls", "ConfigSheet","GetData", "InputSheet", g1,2);
		
	}*/
	}
}
