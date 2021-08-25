package jssz.archives.tool;

import org.apache.poi.xwpf.usermodel.BreakType;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;

public class WordGenerator extends Thread {

	private static final int DEFAULT_WIDTH = 520;
	private static final int DEFAULT_HEIGHT = 350;

	private volatile int total = 0;
	private volatile int current = 0;
	private volatile String currentPhoto = "";

	private String targetFilePath;
	private String tatgetfileHead;
	private double picCompressedSize;

	private jssz.archives.tool.ExcelUtil excelUtil;

	private jssz.archives.tool.FileUtil fileUtil;

	private List<jssz.archives.tool.FileNo> inExcelNotInPhotoDirectory = new ArrayList<jssz.archives.tool.FileNo>();
	private List<String> inPhotoDirectoryNotInExcel = new ArrayList<String>();

	private TreeMap<jssz.archives.tool.FileNo, jssz.archives.tool.PhotoInfo> photoInfoMap = new TreeMap<jssz.archives.tool.FileNo, jssz.archives.tool.PhotoInfo>();
	private Map<jssz.archives.tool.FileNo, String> photoNameListFromPhotoDirectory = new TreeMap<jssz.archives.tool.FileNo, String>();

	public WordGenerator(String excelFilePath, String photoFilePath, String targetFilePath,String tatgetfileHead,double picCompressedSize) throws IOException {
		this.targetFilePath = targetFilePath;
		this.tatgetfileHead = tatgetfileHead;
		this.picCompressedSize = picCompressedSize;

		excelUtil = new jssz.archives.tool.ExcelUtil(excelFilePath);
		TreeMap<jssz.archives.tool.FileNo, jssz.archives.tool.PhotoInfo> photoInfoMapFromExcel = excelUtil.getPhotopMap();
		fileUtil = new jssz.archives.tool.FileUtil(photoFilePath, excelUtil.getFileNoList());

		photoNameListFromPhotoDirectory = fileUtil.getIntersection();

		// photoNameListFromPhotoDirectory = new
		// FileUtil(photoFilePath,excelUtil.getFileNoList());
		photoInfoMap = new TreeMap<jssz.archives.tool.FileNo, jssz.archives.tool.PhotoInfo>();
		// 取Excel表格和照片文件的交集
		for (jssz.archives.tool.FileNo s : fileUtil.intersection.keySet()) {
			if (photoInfoMapFromExcel.get(s) != null) {
				photoInfoMap.put(s, photoInfoMapFromExcel.get(s));
			}
		}

		inPhotoDirectoryNotInExcel = fileUtil.getIndirecNotInExcel();
		// 取有照片但没有对应的excel记录的档案号
		// for (FileNo s : fileUtil.getIndirecNotInExcel()) {
		// if (photoInfoMap.get(s) == null) {
		// inPhotoDirectoryNotInExcel.add(s);
		// }
		// }
		// 取有对应的excel记录但没有对应照片的档案号
		for (jssz.archives.tool.FileNo s : photoInfoMapFromExcel.keySet()) {
			if (photoInfoMap.get(s) == null) {
				inExcelNotInPhotoDirectory.add(s);
			}
		}

		Collections.sort(inExcelNotInPhotoDirectory);
		Collections.sort(inPhotoDirectoryNotInExcel);
		total = photoInfoMap.size();

		System.out.println("待插入Word的文件信息--" + photoInfoMap);
		System.out.println("文件名和对应的路径信息---" + photoNameListFromPhotoDirectory);
	}

	public void run() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		jssz.archives.tool.WordUtil wordUtil = new jssz.archives.tool.WordUtil(new jssz.archives.tool.CustomizeDocument());
		wordUtil.setMargin();
		wordUtil.generateContent(photoInfoMap, excelUtil.getGroupNameMap());
		wordUtil.addBlankRow(1);
		int pictureNum = 0;
		for (Map.Entry<jssz.archives.tool.FileNo, jssz.archives.tool.PhotoInfo> entry : photoInfoMap.entrySet()) {
			if (Thread.currentThread().isInterrupted()) {
				return;
			}
			jssz.archives.tool.FileNo name = entry.getKey();
			jssz.archives.tool.PhotoInfo photoInfo = photoInfoMap.get(name);
			currentPhoto = photoInfo.getFileNo();
			String photoPath = photoNameListFromPhotoDirectory.get(name);
			try {
				int pictureType = jssz.archives.tool.PictureUtil.getPicType(photoPath);
				byte[] jpgByte = null;
				int desSize = 0;
				if(picCompressedSize==200){
					desSize = 200;
					System.out.println("des pic size: 200k");
				}else if(picCompressedSize==100){
					desSize = 100;
					System.out.println("des pic size: 100k");

				}
				if (pictureType == XWPFDocument.PICTURE_TYPE_JPEG) {
					jpgByte = jssz.archives.tool.PictureUtil.getFileByte(photoPath);
					System.out.println("解析图片：" + photoPath + ",图片类型：JPG");
				} else if (pictureType == XWPFDocument.PICTURE_TYPE_TIFF) {
					System.out.println("解析图片：" + photoPath + ",图片类型：TIFF");
					jpgByte = jssz.archives.tool.PictureUtil.tifToJpg(photoPath);
					// tiff格式压缩到300K
					if(picCompressedSize==200){
						desSize = 300;
					}else if(picCompressedSize==100){
						desSize = 150;
					}
				} else if (photoPath.endsWith(".pdf")) {
					System.out.println("解析图片：" + photoPath + ",图片类型：PDF");
					jpgByte = jssz.archives.tool.PictureUtil.pdfToJPG(photoPath);

				} else {

					jpgByte = jssz.archives.tool.CustomizePicture.generatePicture("无法插入图片,不属于(JPG,TIFF,PDF)格式：" + name.getFileNo());

				}
				if (jpgByte == null) {

					jpgByte = jssz.archives.tool.CustomizePicture.generatePicture("插入图片失败" + name.getFileNo());
				}

				BufferedImage src = ImageIO.read(new ByteArrayInputStream(jpgByte));
				int width = getWidth(src.getWidth(), src.getHeight());
				wordUtil.insertPicture(desSize, XWPFDocument.PICTURE_TYPE_JPEG, jpgByte, photoPath,
						ParagraphAlignment.CENTER, width, DEFAULT_HEIGHT);
				System.out.println("插入图片：" + photoPath);
				pictureNum++;
				current++;
				if (pictureNum % 2 == 1) {
					wordUtil.createPhotoInfoTable(photoInfo);
				} else {
					wordUtil.createPhotoInfoTable(photoInfo);
					wordUtil.addBlankRow(1);
					wordUtil.addSimpleParagraph("第" + (int) Math.ceil(pictureNum / 2.0) + "页", "宋体", 9, "808080", false,
							false, "Heading2", ParagraphAlignment.CENTER, 0);
					if (pictureNum != photoInfoMap.entrySet().size()) {
						wordUtil.addNewPage(BreakType.PAGE);
						wordUtil.addBlankRow(1);
					}
				}
				if (pictureNum % 8 == 0) {
					wordUtil.saveDocument(targetFilePath);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		try {
			wordUtil.createHeader(tatgetfileHead);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			if (pictureNum % 2 == 1) {
				wordUtil.addBlankRow(23);
				wordUtil.addSimpleParagraph("第" + (int) Math.ceil(pictureNum / 2.0) + "页", "宋体", 9, "808080", false,
						false, "Heading2", ParagraphAlignment.CENTER, 0);
			}

			wordUtil.saveDocument(targetFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 限制高度，宽度等比例缩放，如果缩放后大于默认宽度，则变成默认宽度
	public static int getWidth(int width, int height) {
		int resWidth = DEFAULT_HEIGHT * width / height;
		return resWidth < DEFAULT_WIDTH ? resWidth : DEFAULT_WIDTH;
	}

	public String getProcessPercent() {
		return String.format("%.2f", 100.0 * current / total) + "%";
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrent() {
		return current;
	}

	public void setCurrent(int current) {
		this.current = current;
	}

	public String getCurrentPhoto() {
		return currentPhoto;
	}

	public void setCurrentPhoto(String currentPhoto) {
		this.currentPhoto = currentPhoto;
	}

	public List<jssz.archives.tool.FileNo> getInExcelNotInPhotoDirectory() {
		return inExcelNotInPhotoDirectory;
	}

	public List<String> getInPhotoDirectoryNotInExcel() {
		return inPhotoDirectoryNotInExcel;
	}

	public static void main(String args[]) throws IOException {
		new jssz.archives.tool.ProcessBar().init("/Users/sherry/Documents/电子相册生成/问题常熟照片册/案卷已整理(含卷盒内文件).xls",
				"/Users/sherry/Documents/电子相册生成/问题常熟照片册/同和变工程照片",
				"/Users/sherry/Desktop/" + System.currentTimeMillis() + ".docx", "国网苏州供电公司",200);
	}
}
