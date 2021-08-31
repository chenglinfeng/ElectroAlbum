package jssz.archives.tool;

public class PhotoInfo {
	
	private String FileNo;
	private String author;
	private String name;
	private String photoTakenTime;
	private String description;
	private String classificationName;
	private String classificationNO;
	
	


	
	public PhotoInfo(String fileNo, String author, String name, String photoTakenTime, String description,
			String classificationName, String classificationNO) {
		super();
		FileNo = fileNo;
		this.author = author;
		this.name = name;
		this.photoTakenTime = photoTakenTime;
		this.description = description;
		this.classificationName = classificationName;
		this.classificationNO = classificationNO;
	}

	public String getFileNo() {
		return FileNo;
	}

	public void setFileNo(String fileNo) {
		FileNo = fileNo;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getDesp() {
		return name;
	}

	public void setDesp(String desp) {
		this.name = desp;
	}
	


	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhotoTakenTime() {
		return photoTakenTime;
	}

	public void setPhotoTakenTime(String photoTakenTime) {
		this.photoTakenTime = photoTakenTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	
	public String getClassificationName() {
		return classificationName;
	}

	public void setClassificationName(String classificationName) {
		this.classificationName = classificationName;
	}

	public String getClassificationNO() {
		return classificationNO;
	}

	public void setClassificationNO(String classificationNO) {
		this.classificationNO = classificationNO;
	}

	@Override
	public String toString() {
		return "PhotoInfo [档案号=" + FileNo + ", 分类号=" + classificationNO + ", 分类名称=" + classificationName +", 拍摄者=" + author + ", 题名=" + name + ", 拍摄时间="
				+ photoTakenTime + ", 文字描述=" + description + "]";
	}


}
