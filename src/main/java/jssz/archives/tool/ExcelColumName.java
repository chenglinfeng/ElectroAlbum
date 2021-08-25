package jssz.archives.tool;

public enum ExcelColumName {

	FILE_NO("档号", -1), AUTHOR("摄影者", 40), PHOTO_TAKEN_TIME("成文日期", 35), NAME("题名", -1), DESCRIPTION("文字说明",
			200), CLASSIFICATION_NAME("分类名称", -1), CLASSIFICATION_NO("分类号", -1);
	//Excel表格中各个字段的中文名称
	private String name;
	//Excel表格中各个字段的长度限制，如果超出这个长度，就用*进行省略，-1代表该字段不可以省略，必须显示完全
	private int maxLength;

	ExcelColumName(String name, int maxLength) {
		this.name = name;
		this.maxLength = maxLength;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

}
