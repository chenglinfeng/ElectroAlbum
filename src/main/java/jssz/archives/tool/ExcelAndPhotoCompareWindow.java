package jssz.archives.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelAndPhotoCompareWindow {
	
	private JFrame frame = new JFrame("excel和文件对比一览表");

	/**
	 * @param
	 * @throws IOException 
	 */
	public  void compare(String excelFilePath, String photoFilePath) throws IOException {
		
		
		 WordGenerator target = new WordGenerator(excelFilePath,photoFilePath,"","国网苏州供电公司",200);
		
		frame.setBounds(600, 600, 500, 700);

		JLabel label = new JLabel("excel和文件对比一览表");
		frame.add(label);
		frame.getContentPane().add(label);
		//frame.setDefaultCloseOperation(JFrame.);// 如果没有这一句就是直接隐藏
		
		frame.addWindowListener(new WindowAdapter() {
			 public void windowClosed(WindowEvent e) {
				 frame.dispose();
			 }
		});

		Map<String,String> map = new HashMap();
		map.put("abc","abc");
		map.clear();
		map.size();
		
		 List<jssz.archives.tool.FileNo> inExcelNotInPhotoDirectory=target.getInExcelNotInPhotoDirectory();
		 List<String> inPhotoDirectoryNotInExcel=target.getInPhotoDirectoryNotInExcel();
		
		Object[][] rowData = getRowData(inExcelNotInPhotoDirectory,inPhotoDirectoryNotInExcel);// 建数据
		System.out.println(rowData);
		String[] columnData = { "Excel有记录，无对应照片文件", "有照片，无对应Excel记录" };// 建一个表头
		JTable table = new JTable(rowData, columnData);
		JScrollPane jScrollpane = new JScrollPane(table);// 带滚动条的面板
		jScrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jScrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(jScrollpane, BorderLayout.CENTER);// 把滚动面板加到frame


		frame.setVisible(true);
		
	}
	
	
	private Object[][] getRowData(List<jssz.archives.tool.FileNo> inExcelNotInPhotoDirectory, List<String> inPhotoDirectoryNotInExcel) {
		int lenth1=inExcelNotInPhotoDirectory.size();
		int lenth2=inPhotoDirectoryNotInExcel.size();
		if(lenth1>=lenth2) {
			Object[][] res= new Object[lenth1][2];
			int i=0;
			while(i<lenth2){
				res[i][0]=inExcelNotInPhotoDirectory.get(i).getFileNo();
				res[i][1]=inPhotoDirectoryNotInExcel.get(i);
				i++;

				}			
			while(i<lenth1) {
				res[i][0]=inExcelNotInPhotoDirectory.get(i).getFileNo();
				i++;
			}
			return res;
		}else {
			Object[][] res= new Object[lenth2][2];
			int i=0;
			while(i<lenth1){
				res[i][0]=inExcelNotInPhotoDirectory.get(i).getFileNo();
				res[i][1]=inPhotoDirectoryNotInExcel.get(i);
				i++;

				}			
			while(i<lenth2) {
				res[i][0]=inExcelNotInPhotoDirectory.get(i).getFileNo();
				i++;
			}
			System.out.println("excel和文件对比一览表"+res);

			return res;
			
		}
	}
	
	
	public static void main(String args[]) throws IOException {
		new ExcelAndPhotoCompareWindow().compare("/Users/sherry/Desktop/查漏/！案卷已归档(含卷盒内文件).xls",
				 "/Users/sherry/Desktop/查漏/2018照片电子档（档号命名20190805）");
	}

}
