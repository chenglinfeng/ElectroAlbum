package jssz.archives.tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PhotoAlbumListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
		if (jssz.archives.tool.WordGeneratorForm.excelFileLocation.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择待Excel文件");
		} else if (jssz.archives.tool.WordGeneratorForm.photoFileDirectory.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择照片存放目录");
		} else if (jssz.archives.tool.WordGeneratorForm.targetFileDirectory.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择生成文件的位置");
		} else if (jssz.archives.tool.WordGeneratorForm.targetFileName.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "请选择生成文件的名称");
		} else if (WordGeneratorForm.targetFileHead.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "请选择生成文件的页眉");
		}
		else {
			String excelFileLocation = jssz.archives.tool.WordGeneratorForm.excelFileLocation.getText();
			String photoFileDirectory = jssz.archives.tool.WordGeneratorForm.photoFileDirectory.getText();
			String targetfile3 = jssz.archives.tool.WordGeneratorForm.targetFileDirectory.getText();
			String targetfileName = jssz.archives.tool.WordGeneratorForm.targetFileName.getText();
			String tatgetfileHead = WordGeneratorForm.targetFileHead.getText();
			double picCompressedSize = WordGeneratorForm.picCompressedSize;
			
			if (!targetfileName.endsWith(".doc") && !targetfileName.endsWith(".docx")) {
				targetfileName = targetfileName + ".doc";
			}else  {
				int index = targetfileName.indexOf(".");
				targetfileName=targetfileName.substring(0,index)+ ".doc";
			}
			String targetFileFinal = targetfile3 + File.separator + targetfileName;
			try {
				new jssz.archives.tool.ProcessBar().init(excelFileLocation,photoFileDirectory,targetFileFinal,tatgetfileHead,picCompressedSize);
			
			} catch (Exception e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
		}
	}
}
