package jssz.archives.tool;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class ProcessBar {
  Timer timer;
  public void init(String excelFilePath, String photoFilePath,final String targetFilePath,String tatgetfileHead,double picCompressedSize) throws IOException {

   File file =new File(targetFilePath);
   if(file.exists()) {
	   
	   int result = JOptionPane.showConfirmDialog(null, targetFilePath+"文件已经存在，是否要覆盖？", "",
				JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (result == JOptionPane.NO_OPTION) {
			return;
		}
   }
   System.out.println(new Date()+"，任务开始");
	  final WordGenerator target = new WordGenerator(excelFilePath,photoFilePath,targetFilePath,tatgetfileHead,picCompressedSize);
    final Thread targetThread = new Thread(target);
    
    final ProgressMonitor dialog = new ProgressMonitor(null,
        "等待任务完成,任务完成之前请不要关闭窗口或打开生成的Word文件，否则将取消当前操作...", "已完成：0.00%", 0,
        target.getTotal());
		timer = new Timer(300, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 以任务的当前完成量设置进度对话框的完成比例
				dialog.setProgress(target.getCurrent());
				dialog.setNote("正在处理：" + target.getCurrentPhoto() + ",已完成：" + target.getProcessPercent());
				// 如果用户单击了进度对话框的”取消“按钮
				if (dialog.isCanceled()) {
					timer.stop();
					targetThread.interrupt();
				}
				if (target.getProcessPercent().equals("100.00%")) {
					timer.stop();

					try {
						Thread.currentThread().sleep(2000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					dialog.close();
//					FileUtil.openFile(targetFilePath);
					int result = JOptionPane.showConfirmDialog(null, "文件已经生成，位于" + targetFilePath + ",是否退出程序？", "",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (result == JOptionPane.YES_OPTION) {
						System.exit(0);
					}

				}
			}
		});
    timer.start();
    targetThread.start();
  }
  public static void main(String[] args) {
    try {
		new ProcessBar().init("/Users/sherry/Desktop/查漏/！案卷已归档(含卷盒内文件).xls",
				 "/Users/sherry/Desktop/查漏/2018照片电子档（档号命名20190805）",
				 "/Users/sherry/Desktop/" + System.currentTimeMillis() + ".doc","国网苏州供电公司",200);
	} catch (IOException e) {
		e.printStackTrace();
	}
  }
}