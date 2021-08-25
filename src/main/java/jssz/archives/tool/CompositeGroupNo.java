package jssz.archives.tool;

public class CompositeGroupNo implements Comparable<CompositeGroupNo> {

	private String compositeFileNo;
	private String prefix;
	private int groupNo;
	

	public CompositeGroupNo(String fileNo) {
		super();
		String[] strings = fileNo.split("-");

		if (strings.length >= 4) {
			groupNo = Integer.valueOf(strings[strings.length - 2]);
			prefix = fileNo.substring(0, fileNo.substring(0, fileNo.lastIndexOf("-")).lastIndexOf("-"));
			compositeFileNo=fileNo.substring(0, fileNo.lastIndexOf("-"));
		}
	}

	@Override
	public String toString() {
		return "盒号 [盒号=" + compositeFileNo + "]";
	}

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((compositeFileNo == null) ? 0 : compositeFileNo.hashCode());
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
		CompositeGroupNo other = (CompositeGroupNo) obj;
		if (compositeFileNo == null) {
			if (other.compositeFileNo != null)
				return false;
		} else if (!compositeFileNo.equals(other.compositeFileNo))
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

	public String getCompositeFileNo() {
		return compositeFileNo;
	}

	public void setCompositeFileNo(String compositeFileNo) {
		this.compositeFileNo = compositeFileNo;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public int getGroupNo() {
		return groupNo;
	}

	public void setGroupNo(int groupNo) {
		this.groupNo = groupNo;
	}

	public int compareTo(CompositeGroupNo o) {
		int res = prefix.compareTo(o.getPrefix());
		if (res != 0) {
			return res;
		} else {
			if (groupNo > o.groupNo) {
				return 1;
			} else if (groupNo < o.groupNo) {
				return -1;
			} else {
				return 0;
			}
		}
	}

}



