package jssz.archives.tool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomizePicture {

	public static byte[] generatePicture(String info) throws FileNotFoundException, IOException {
		BufferedImage bufferImage = null;

		int w = 550;// 画布宽度
		int h = 300;// 画布高度
		try {
			bufferImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufferImage.createGraphics();
			Font font = new Font("微软雅黑", Font.PLAIN, 30);
			List<String> line_list = new ArrayList<String>();

			FontRenderContext frc = g.getFontRenderContext();
			g.getFontRenderContext();
			Rectangle2D stringBounds = font.getStringBounds(info, frc);
			double fontWidth = stringBounds.getWidth();
			if (fontWidth <= w) {
				line_list.add(info);
			} else {
				int text_width = w;// 输出文本宽度,这里就以画布宽度作为文本宽度测试
				double bs = fontWidth / text_width;// 文本长度是文本框长度的倍数
				int line_char_count = (int) Math.ceil(info.length() / bs);// 每行大概字数
				int begin_index = 0;
				while (begin_index < info.length()) {
					int end_index = begin_index + line_char_count;
					if (end_index >= info.length()) {
						end_index = info.length();
					}
					String line_str = info.substring(begin_index, end_index);
					Rectangle2D tempStringBounds = font.getStringBounds(line_str, frc);
					int tzzs = 1;// 调整字数,临时文本的字符串长度大于要求的文本宽度时,每次减少临时文本的字数,然后重新测量文本宽度
					while (tempStringBounds.getWidth() > text_width) {
						line_str = line_str.substring(0, line_str.length() - tzzs);// 每次向前tzzs个字符重新计算(待优化)
						tempStringBounds = font.getStringBounds(line_str, frc);
					}
					line_list.add(line_str);
					begin_index = begin_index + line_str.length();
				}
			}

			g.setColor(Color.WHITE);
			g.fillRect(0, 0, w, h);// 填充整个屏幕
			g.setFont(font);
			g.setColor(Color.RED);

			for (int i = 0; i < line_list.size(); i++) {
				String line_str = line_list.get(i);
				g.drawString(line_str, 0, (i + 2) * 35);// 35为每行的高度
			}

			g.dispose();
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			 ImageIO.write(bufferImage, "JPG" , byteArrayOutputStream);
			
//			JPEGImageEncoder en = JPEGCodec.createJPEGEncoder(byteArrayOutputStream);
//			en.encode(bufferImage);
			byteArrayOutputStream.flush();
			return byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null;

	}

	public static void main(String args[]) throws FileNotFoundException, IOException {
		CustomizePicture.generatePicture("");
	}
}
