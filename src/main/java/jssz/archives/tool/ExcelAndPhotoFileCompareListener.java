package jssz.archives.tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExcelAndPhotoFileCompareListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if (jssz.archives.tool.WordGeneratorForm.excelFileLocation.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择待Excel文件");
		} else if (jssz.archives.tool.WordGeneratorForm.photoFileDirectory.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择照片存放目录");
		} else {
			String excelFileLocation = jssz.archives.tool.WordGeneratorForm.excelFileLocation.getText();
			String photoFileDirectory = jssz.archives.tool.WordGeneratorForm.photoFileDirectory.getText();
			try {
				new ExcelAndPhotoCompareWindow().compare(excelFileLocation, photoFileDirectory);
				
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}
}


