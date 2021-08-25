package jssz.archives.tool;

public class FileNo implements Comparable<FileNo> {

	private String fileNo;
	private String prefix;
	private int groupNo;
	private int FileNoInGroup;
	
	//档号有不同格式，有用0填充和不用0填充的档号，为了方便对各个类型的档号排序，将档号分为三段，prefix，组号，以及组内编号

	public FileNo(String fileNo) {
		super();
		this.fileNo = fileNo;
		String[] strings = fileNo.split("-");

		if (strings.length >= 4) {
			groupNo = Integer.valueOf(strings[strings.length - 2]);
			FileNoInGroup = Integer.valueOf(strings[strings.length - 1]);
			prefix = fileNo.substring(0, fileNo.substring(0, fileNo.lastIndexOf("-")).lastIndexOf("-"));
		}
	}

	@Override
	public String toString() {
		return "FileNo [档号=" + fileNo + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + FileNoInGroup;
		result = prime * result + ((fileNo == null) ? 0 : fileNo.hashCode());
		result = prime * result + groupNo;
		result = prime * result + ((prefix == null) ? 0 : prefix.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileNo other = (FileNo) obj;
		if (FileNoInGroup != other.FileNoInGroup)
			return false;
		if (fileNo == null) {
			if (other.fileNo != null)
				return false;
		} else if (!fileNo.equals(other.fileNo))
			return false;
		if (groupNo != other.groupNo)
			return false;
		if (prefix == null) {
			if (other.prefix != null)
				return false;
		} else if (!prefix.equals(other.prefix))
			return false;
		return true;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFileNo() {
		return fileNo;
	}

	public void setFileNo(String fileNo) {
		this.fileNo = fileNo;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int getFileNoInGroup() {
		return FileNoInGroup;
	}

	public void setFileNoInGroup(int fileNoInGroup) {
		FileNoInGroup = fileNoInGroup;
	}

	public int compareTo(FileNo o) {
		int res = prefix.compareTo(o.getPrefix());
		if (res != 0) {
			return res;
		} else {
			if (groupNo > o.groupNo) {
				return 1;
			} else if (groupNo < o.groupNo) {
				return -1;
			} else {
				if (FileNoInGroup > o.FileNoInGroup) {
					return 1;
				} else if (FileNoInGroup < o.FileNoInGroup) {
					return -1;
				} else {
					return 0;
				}
			}
		}
	}

}
