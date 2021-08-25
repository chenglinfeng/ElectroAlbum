package jssz.archives.tool;

import com.sun.media.jai.codec.ImageCodec;
import com.sun.media.jai.codec.ImageEncoder;
import com.sun.media.jai.codec.JPEGEncodeParam;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import javax.imageio.ImageIO;
import javax.media.jai.JAI;
import javax.media.jai.RenderedOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


public class PictureUtil {

	private static final int UNKNOWN_TYPE = -1;

	/**
	 * 根据文件流判断图片类型
	 * @throws IOException 
	 */
	public static int getPicType(String pictureAbsolutePath) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(pictureAbsolutePath);
			byte[] b = new byte[4];

			fis.read(b, 0, b.length);
			String type = bytesToHexString(b).toUpperCase();
			if (type.contains("FFD8FF")) {
				return XWPFDocument.PICTURE_TYPE_JPEG;
			} else if (type.contains("89504E47")) {
				return XWPFDocument.PICTURE_TYPE_PNG;
			} else if (type.contains("47494638")) {
				return XWPFDocument.PICTURE_TYPE_GIF;
			} else if (type.contains("424D")) {
				return XWPFDocument.PICTURE_TYPE_BMP;
			} else if (type.contains("4D4D002A")) {
				return XWPFDocument.PICTURE_TYPE_TIFF;
			}else {
				return UNKNOWN_TYPE;
			}
		}  finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder();
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
	
	
//	public static void tifToJpg(String srcFilePath,String descFilePath) throws IOException{
//	    RenderedOp src2 = JAI.create("fileload", srcFilePath);   
//	   // OutputStream os2 = new FileOutputStream(descFilePath);   
//	    ByteArrayOutputStream os2 = new ByteArrayOutputStream(); 
//	    JPEGEncodeParam param2 = new JPEGEncodeParam();   
//	    //指定格式类型，jpg 属于 JPEG 类型   
//	    ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);   
//	    enc2.encode(src2);
//	    byte[]  bytes1= os2.toByteArray();
//	    os2.close();
//	    retu
//	    
//	    BufferedImage src = ImageIO.read(new ByteArrayInputStream(bytes1));
//	 		System.out.println(src);
//	 		System.out.println(src.getHeight());
//	 		System.out.println(src.getWidth());
	 	    
	    
	   // byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002.tiff"));
//	    byte[]  bytes = PhotoCompressor.compressPic(bytes1, 300, "/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-00222.JPEG");// 图片小于300kb
//		FileOutputStream fos = new FileOutputStream(new File("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002222.JPEG"));
//	      fos.write(bytes,0,bytes.length);   
//	      fos.flush();   
//	      fos.close();   
	    
//	}
	
	
	public static byte[] tifToJpg(String srcFilePath) throws IOException{
	    RenderedOp src2 = JAI.create("fileload", srcFilePath);   
	    ByteArrayOutputStream os2 = new ByteArrayOutputStream(); 
	    JPEGEncodeParam param2 = new JPEGEncodeParam();   
	    //指定格式类型，jpg 属于 JPEG 类型   
	    ImageEncoder enc2 = ImageCodec.createImageEncoder("JPEG", os2, param2);   
	    enc2.encode(src2);
	    byte[]  bytes1= os2.toByteArray();
	    os2.close();
	    return bytes1;
//	    
//	    BufferedImage src = ImageIO.read(new ByteArrayInputStream(bytes1));
//	 		System.out.println(src);
//	 		System.out.println(src.getHeight());
//	 		System.out.println(src.getWidth());
	 	    
	    
	   // byte[] bytes = FileUtils.readFileToByteArray(new File("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002.tiff"));
//	    byte[]  bytes = PhotoCompressor.compressPic(bytes1, 300, "/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-00222.JPEG");// 图片小于300kb
//		FileOutputStream fos = new FileOutputStream(new File("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-003-002222.JPEG"));
//	      fos.write(bytes,0,bytes.length);   
//	      fos.flush();   
//	      fos.close();   
	    
	}
	
	
		
		
		
	public static byte[] pdfToJPG(String pdfFilePath) throws Exception {
		byte[] res=null;
			if (pdfFilePath.endsWith(".pdf")) {
				File file = new File(pdfFilePath);
				PDDocument doc = PDDocument.load(file);
				PDFRenderer renderer = new PDFRenderer(doc);
				int pageCount = doc.getNumberOfPages();
				for (int i = 0; i < pageCount; i++) {
					BufferedImage image = renderer.renderImageWithDPI(i, 296);
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					ImageIO.write(image, "JPG", byteArrayOutputStream);
					res=byteArrayOutputStream.toByteArray();
					byteArrayOutputStream.close();
				}
			}
		
		return res;
	}	
	
	
	
	
	
	public static byte[] getFileByte(String filePath) throws IOException {
		byte[] res = FileUtils.readFileToByteArray(new File(filePath));
		return res;
	}	

//public static void main(String args[]) throws IOException {
//	PictureUtil.tifToJpg("/Users/sherry/Desktop/测试数据1/100802-ZP2018-0-001-003.tiff", "");
//}



}
