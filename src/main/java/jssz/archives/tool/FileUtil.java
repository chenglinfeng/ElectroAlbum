package jssz.archives.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {

	private String directoryPath;

	public FileUtil(String directoryPath, List<String> excelFileNolist) {
		super();
		this.directoryPath = directoryPath;
		parse(excelFileNolist);
	}

	Map<jssz.archives.tool.FileNo, String> intersection = new HashMap<jssz.archives.tool.FileNo, String>();
	private List<String> indirecNotInExcel = new ArrayList<String>();

	public void parse(List<String> excelFileNolist) {
//		File file = new File(directoryPath);
//		File[] fs = file.listFiles();
//		int fileNum = fs.length;
//		for (int i = 0; i < fileNum; i++) {
//			if (fs[i].isDirectory()) {
//				continue;
//			}
//			String fileName = fs[i].getName();
//			boolean contains = false;
//
//			for (String s : excelFileNolist) {
//				if (fileName.startsWith(s)) {
//					// jiaoji.add(new FileNo(s));
//					intersection.put(new FileNo(s), fs[i].getAbsolutePath());
//					contains = true;
//				}
//			}
//
//			if (contains == false) {
//				indirecNotInExcel.add(fileName);
//			}
//		}
		

		File file = new File(directoryPath);
		File[] fs = file.listFiles();
		int fileNum = fs.length;
		for (int i = 0; i < fileNum; i++) {
			if (fs[i].isDirectory()) {
				continue;
			}
			String fileName = fs[i].getName();
			boolean contains = false;

			for (String s : excelFileNolist) {
				String prefix=s.substring(0, s.substring(0, s.lastIndexOf("-")).lastIndexOf("-"));

				String fileNoFromPhoto= jssz.archives.tool.ParseUtil.getFileNo(fileName, prefix);
				if (fileNoFromPhoto.equals(s)) {
					// jiaoji.add(new FileNo(s));
					intersection.put(new jssz.archives.tool.FileNo(s), fs[i].getAbsolutePath());
					contains = true;
				}
			}

			if (contains == false) {
				indirecNotInExcel.add(fileName);
			}
		}
	
	}

	public Map<jssz.archives.tool.FileNo, String> getIntersection() {
		return intersection;
	}

	

	public List<String> getIndirecNotInExcel() {
		return indirecNotInExcel;
	}

//打开Word文件	
	
	public static void openFile(String path) {
		
			Runtime run =Runtime.getRuntime();
		    String cmd = "rundll32 url.dll FileProtocolHandler file://"+path;
			try {
				run.exec(cmd);
			} catch (Exception e) {
				e.printStackTrace();
				//JOptionPane.showMessageDialog(null, "Erroe:"+path, "路径错误", JOptionPane.ERROR_MESSAGE); 
			}
		
	}
	
	
	public static void main(String args[]) {
		String string1 = "10080205-ZP2018-0-001-002+退休礼合影照片20180131";
		String string2="BSZCS203301-ZSDQ-1-16+";
		String regrex="10080205-ZP2018-0-\\d+-\\d+\\+";
		Pattern pattern = Pattern.compile(regrex);
		
        //System.out.println(pattern.pattern());
        Matcher matcher = pattern.matcher(string1);
        if (matcher.find()) {
    		System.out.println(matcher.group(0));
    		}

        Matcher matcher2 = pattern.matcher(string2);
        if (matcher2.find()) {
    		System.out.println(matcher2.group(0));
    		}
		
		

				
	}
	
}
