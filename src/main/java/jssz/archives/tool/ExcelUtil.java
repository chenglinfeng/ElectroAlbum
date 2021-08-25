package jssz.archives.tool;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

public class ExcelUtil {

	private TreeMap<FileNo, jssz.archives.tool.PhotoInfo> photopMap = new TreeMap<FileNo, jssz.archives.tool.PhotoInfo>();
	private List<String> fileNoList = new ArrayList<String>();
	//private TreeMap<String, String> eachClassificationNameMap = new TreeMap<String, String>();
	private TreeMap<String, String> groupNameMap = new TreeMap<String, String>();
	private static final String groupSheetName="案卷";
	private static final String photoSheetName="卷盒";

	public ExcelUtil(String filePath) throws IOException {
		super();
		parseExcel(filePath);
	}

	private  void parseExcel(String filePath) throws IOException {
		Workbook wb = null;
		FileInputStream fileInputStream = null;
		try {
			// 兼容xls,xlsx格式的Excel
			fileInputStream = new FileInputStream(filePath);
			if (filePath.endsWith("xls")) {
				wb = new HSSFWorkbook(fileInputStream);
			}
			if (filePath.endsWith("xlsx")) {
				wb = new XSSFWorkbook(fileInputStream);
			}
			Sheet groupSheet = null;
			Sheet fileSheet = null;
			
			for (int i = 0; i < wb.getNumberOfSheets(); i++) {// 获取每个Sheet表
				Sheet sheet = wb.getSheetAt(i);
				String name = sheet.getSheetName();
				if (name.contains(groupSheetName)) {
					groupSheet = sheet;
				} else if (name.contains(photoSheetName)) {
					fileSheet = sheet;
				}
			}
			if(groupSheet==null) {
				throw new IllegalArgumentException("Excel表中缺少案卷工作表");
			}
			if(fileSheet==null) {
				throw new IllegalArgumentException("Excel表中缺少卷内文件工作表");

			}
			parseGroupName(groupSheet);

			parseFileSheet(fileSheet);
		} finally {
			if (wb != null) {
				try {
					if (wb instanceof HSSFWorkbook) {
						wb.close();
					} else {
						if (fileInputStream != null) {
							fileInputStream.close();
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public TreeMap<FileNo, jssz.archives.tool.PhotoInfo> getPhotopMap() {
		return photopMap;
	}

	public void setPhotopMap(TreeMap<FileNo, jssz.archives.tool.PhotoInfo> photopMap) {
		this.photopMap = photopMap;
	}

//	public TreeMap<String, String> getEachClassificationNameMap() {
//		return eachClassificationNameMap;
//	}
//
//	public void setEachClassificationNameMap(TreeMap<String, String> eachClassificationNameMap) {
//		this.eachClassificationNameMap = eachClassificationNameMap;
//	}

	public static void main(String args[]) throws IOException {
		ExcelUtil excelUtil = new ExcelUtil("/Users/sherry/Desktop/常熟照片档案测试/案卷已归档(含卷盒内文件).xls");
		System.out.println(excelUtil.getPhotopMap());
		//System.out.println(excelUtil.getEachClassificationNameMap());

	}

	private void parseGroupName(Sheet sheet) {
		int rowNum = sheet.getLastRowNum();
		// 解析标题，<列名，所在第几列>
		Row row1 = sheet.getRow(0);
		int columnNum = row1.getPhysicalNumberOfCells();
		HashMap<String, Integer> columnNameIndex = new HashMap<String, Integer>();
		for (int i = 0; i < columnNum; i++) {
			columnNameIndex.put(row1.getCell(i).getStringCellValue(), i);
		}

		for (int j = 1; j <= rowNum; j++) {
			Row row = sheet.getRow(j);
			String groupNo = row.getCell(columnNameIndex.get(jssz.archives.tool.ExcelColumName.FILE_NO.getName())).getStringCellValue();
			String name = getStringValue(row, jssz.archives.tool.ExcelColumName.NAME, columnNameIndex);
			//String name = row.getCell(columnNameIndex.get(ExcelColumName.NAME.getName())).getStringCellValue();
			groupNameMap.put(groupNo, name);
		}
	}
	
	
	private void parseFileSheet(Sheet sheet) {
		int rowNum = sheet.getLastRowNum();
		// 解析标题，<列名，所在第几列>
		Row row1 = sheet.getRow(0);
		int columnNum = row1.getPhysicalNumberOfCells();
		HashMap<String, Integer> columnNameIndex = new HashMap<String, Integer>();
		for (int i = 0; i < columnNum; i++) {
			columnNameIndex.put(row1.getCell(i).getStringCellValue(), i);
		}

		for (int j = 1; j <= rowNum; j++) {
			Row row = sheet.getRow(j);
			String fileNo = row.getCell(columnNameIndex.get(jssz.archives.tool.ExcelColumName.FILE_NO.getName())).getStringCellValue();
			fileNoList.add(fileNo);
			
			
			
			String author = getStringValue(row, jssz.archives.tool.ExcelColumName.AUTHOR, columnNameIndex);
			String desp = getStringValue(row, jssz.archives.tool.ExcelColumName.DESCRIPTION, columnNameIndex);
			String name = getStringValue(row, jssz.archives.tool.ExcelColumName.NAME, columnNameIndex);
			String photoTakenTime= getStringValue(row, jssz.archives.tool.ExcelColumName.PHOTO_TAKEN_TIME, columnNameIndex);
			String classificationName = getStringValue(row, jssz.archives.tool.ExcelColumName.CLASSIFICATION_NAME, columnNameIndex);
			String classificationNO = getStringValue(row, jssz.archives.tool.ExcelColumName.CLASSIFICATION_NO, columnNameIndex);
			jssz.archives.tool.PhotoInfo photoInfo = new jssz.archives.tool.PhotoInfo(fileNo, author, name, photoTakenTime, desp, classificationName,
					classificationNO);
			photopMap.put(new FileNo(fileNo), photoInfo);

			//eachClassificationNameMap.put(classificationNO, classificationName);

		}
	}

	public TreeMap<String, String> getGroupNameMap() {
		return groupNameMap;
	}

	public void setGroupNameMap(TreeMap<String, String> groupNameMap) {
		this.groupNameMap = groupNameMap;
	}

	public List<String> getFileNoList() {
		return fileNoList;
	}

	
	private String getStringValue(Row row, jssz.archives.tool.ExcelColumName excelColumName, HashMap<String, Integer> columnNameIndex) {
		String reString="";
		Integer colum=columnNameIndex.get(excelColumName.getName());
		if(colum==null) {
			reString="";
		}else {
			Cell cell=row.getCell(colum);
			if(cell==null) {
				reString="";
			}else {
				String string=cell.getStringCellValue();
				if(string==null) {
					reString="";
				}else {
					reString=string;
				}
			}
		}
		return reString;
	}
	
}
