package jssz.archives.tool;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {

	public static final String DEFAULT_RES = "*";

	public static String getFileNo(String photoName, String prefix) {
		if (photoName.contains("+")) {
			String regrex = prefix + "-\\d+-\\d+\\+";
			Pattern pattern = Pattern.compile(regrex);
			Matcher matcher = pattern.matcher(photoName);
			if (matcher.find()) {
				return matcher.group(0).substring(0, (matcher.group(0).length() - 1));
			}
		} else {
			String regrex = prefix + "-\\d+-\\d+";
			Pattern pattern = Pattern.compile(regrex);
			Matcher matcher = pattern.matcher(photoName);
			if (matcher.find()) {
				return matcher.group(0);
			}
		}
		return DEFAULT_RES;

	}
}
