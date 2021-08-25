package jssz.archives.tool;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class PhotoCompressor {

	public static void main(String[] args) throws IOException {
		byte[] bytes = FileUtils
				.readFileToByteArray(new File("/Users/sherry/Desktop/照片文件夹/100802-ZP2018-1-001-008.JPG"));
		bytes = PhotoCompressor.compressPic(bytes, 100, "y");
		FileUtils.writeByteArrayToFile(
				new File("/Users/sherry/Desktop/照片文件夹/100802-ZP2018-1-001-008" + System.currentTimeMillis() + ".JPG"),
				bytes);

	}

	/**
	 * 压缩图片至指定大小
	 * 
	 * @param desPicSize
	 *            指定图片大小，单位kb
	 * @throws IOException
	 */
	public static byte[] compressPic(byte[] image, long desPicSize, String picId) throws IOException {
		if (image == null || image.length <= 0 || image.length < desPicSize * 1024) {
			return image;
		}
		long srcSize = image.length;
		double accuracy = getAccuracy(srcSize / 1024 );

		while (image.length > desPicSize * 1024) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(image);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(image.length);
			Thumbnails.of(inputStream).scale(accuracy).outputQuality(accuracy).toOutputStream(outputStream);
			image = outputStream.toByteArray();
			inputStream.close();
			outputStream.close();
		}
		System.out.println("【图片压缩】| 图片原大小={" + srcSize / 1024 + "}KB | 压缩后大小={" + image.length / 1024 + "}KB | ");
		System.out.println("【图片压缩】" + picId);

		return image;
	}

	/**
	 * 获取图片压缩质量比
	 */
	private static double getAccuracy(long size) {
		double accuracy;
		if (size < 900) {
			accuracy = 0.85;
		} else if (size < 2047) {
			accuracy = 0.75;
		} else if (size < 3275) {
			accuracy = 0.65;
		} else {
			accuracy = 0.5;
		}
		return accuracy;
	}

}
