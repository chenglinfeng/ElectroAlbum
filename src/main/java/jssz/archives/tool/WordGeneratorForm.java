package jssz.archives.tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WordGeneratorForm extends JFrame {

	private static final long serialVersionUID = 1L;

	private JFrame mainForm = new JFrame("数码照片档案册自动封装软件");

	private JLabel label1 = new JLabel("请选择相关照片信息的Excel文件", SwingConstants.CENTER);
	private JLabel label2 = new JLabel("请选择照片文件夹所在位置", SwingConstants.CENTER);
	private JLabel label3 = new JLabel("请选择存储的位置", SwingConstants.CENTER);
	private JLabel label4 = new JLabel("请输入生成的文件名称", SwingConstants.CENTER);
	private JLabel label5 = new JLabel("无需输入文件名后缀，默认doc格式", SwingConstants.CENTER);
	private JLabel labelHead = new JLabel("请输入生成的文件页眉", SwingConstants.CENTER);
	private JLabel labelHeadExample = new JLabel("例如'国网苏州供电公司'", SwingConstants.CENTER);

	private JLabel labelPic = new JLabel("请输入图片压缩大小", SwingConstants.CENTER);

	// private JLabel label4 = new JLabel("位置",SwingConstants.CENTER);
	// private JLabel label5 = new JLabel("名称",SwingConstants.CENTER);

	public static JTextField excelFileLocation = new JTextField();

	public static JTextField photoFileDirectory = new JTextField();

	public static JTextField targetFileDirectory = new JTextField();

	public static JTextField targetFileName = new JTextField();

	public static JTextField targetFileHead = new JTextField();

	public static JButton excelFileLocationBrowseButton = new JButton("浏览");

	public static JButton photoFileDirectoryBrowseButton = new JButton("浏览");

	public static JButton targetFileDirectoryBrowseButton = new JButton("浏览");

	public static JButton startButton = new JButton("确定");
	
	public static JButton compareButton = new JButton("对比文件");


	public static JRadioButton radioBtn01 = new JRadioButton("200KB(用于打印相册)");
	public static JRadioButton radioBtn02 = new JRadioButton("100KB(用于借阅浏览)");
	public static double picCompressedSize = 200;


	public WordGeneratorForm() {
		Container container = mainForm.getContentPane();
		mainForm.setSize(700, 620);
		mainForm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		mainForm.setLocationRelativeTo(null);// 居于屏幕中央
		mainForm.setResizable(false);
		mainForm.setLayout(null);
		label1.setBounds(200, 10, 300, 30);
		// targetFileDirectory.setDisabledTextColor(Color.LIGHT_GRAY);
		excelFileLocation.setBounds(220, 50, 200, 30);
		excelFileLocationBrowseButton.setBounds(430, 50, 60, 30);
		label2.setBounds(200, 90, 300, 30);
		photoFileDirectory.setBounds(220, 130, 200, 30);
		photoFileDirectoryBrowseButton.setBounds(430, 130, 60, 30);
		compareButton.setBounds(290, 170, 120, 30);
		label3.setBounds(200, 210, 300, 30);
		targetFileDirectory.setBounds(220, 250, 200, 30);
		targetFileDirectoryBrowseButton.setBounds(430, 250, 60, 30);
		label4.setBounds(200, 290, 300, 30);
		targetFileName.setBounds(220, 330, 200, 30);
		label5.setBounds(430, 330, 220, 30);

		labelHead.setBounds(200,370,300,30);
		targetFileHead.setBounds(220,410,200,30);
		labelHeadExample.setBounds(400,410,220,30);

//		String s = targetFileHead.getText();

		labelPic.setBounds(200,450,300,30);
		radioBtn01.setBounds(370,490,160,30);
		radioBtn02.setBounds(210,490,160,30);
		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(radioBtn01);
		btnGroup.add(radioBtn02);
		radioBtn02.setSelected(true);

		container.add(labelPic);
		container.add(radioBtn01);
		container.add(radioBtn02);

		radioBtn01.addActionListener(new jssz.archives.tool.RadioButtonListener());
		radioBtn02.addActionListener(new jssz.archives.tool.RadioButtonListener());


		startButton.setBounds(320, 540, 60, 30);
		//compareButton.setBounds(200, 330, 90, 30);

		// 绑定事件监听器
		excelFileLocationBrowseButton.addActionListener(new jssz.archives.tool.LocationListener());
		photoFileDirectoryBrowseButton.addActionListener(new jssz.archives.tool.LocationListener());
		targetFileDirectoryBrowseButton.addActionListener(new jssz.archives.tool.LocationListener());
		startButton.addActionListener(new PhotoAlbumListener());
		compareButton.addActionListener(new ExcelAndPhotoFileCompareListener());
		container.add(label1);
		container.add(label2);
		container.add(label3);
		container.add(label4);
		container.add(label5);

		container.add(labelHead);
		container.add(labelHeadExample);

		container.add(excelFileLocation);
		container.add(photoFileDirectory);
		container.add(targetFileDirectory);
		container.add(targetFileName);
		container.add(targetFileHead);

		container.add(excelFileLocationBrowseButton);
		container.add(photoFileDirectoryBrowseButton);
		container.add(targetFileDirectoryBrowseButton);
		container.add(startButton);
		container.add(compareButton);
		mainForm.addWindowListener(new WindowAdapter() {
			 public void windowClosed(WindowEvent e) {
				 System.exit(0);
			 }
		});
		mainForm.setVisible(true);

	}

	public static void main(String args[]) {
		new WordGeneratorForm();
	}
}