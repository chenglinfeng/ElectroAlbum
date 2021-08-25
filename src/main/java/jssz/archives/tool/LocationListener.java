package jssz.archives.tool;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class LocationListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(WordGeneratorForm.excelFileLocationBrowseButton)) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("请选择Excel文件");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel(*.xls;*.xlsx)", "xls", "xlsx");
			fc.setFileFilter(filter);
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filepath = fc.getSelectedFile().getAbsolutePath();
				WordGeneratorForm.excelFileLocation.setText(filepath);
			}
		} else if (e.getSource().equals(WordGeneratorForm.photoFileDirectoryBrowseButton)) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("请选择照片所在目录");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filepath = fc.getSelectedFile().getAbsolutePath();
				File file = new File(filepath);
				if (!file.isDirectory()) {
					String string = filepath.substring(0, filepath.lastIndexOf(File.separator));
					filepath = string;
				}
				WordGeneratorForm.photoFileDirectory.setText(filepath);
			}
		} else if (e.getSource().equals(WordGeneratorForm.targetFileDirectoryBrowseButton)) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("存储的位置");
			fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnVal = fc.showOpenDialog(null);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				String filepath = fc.getSelectedFile().getAbsolutePath();
				File file = new File(filepath);
				if (!file.isDirectory()) {
					String string = filepath.substring(0, filepath.lastIndexOf(File.separator));
					filepath = string;
				}
				WordGeneratorForm.targetFileDirectory.setText(filepath);
			}
		}
	}
}