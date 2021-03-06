package jssz.archives.tool;

public class ContentItem {
	//案卷号
	private String id;
	//对应的名称
	private String name;
	//对应的页码
	private String pageNum;
	public String getId() {
		return id;
	}
	
	
	
	public ContentItem(String id, String name, String pageNum) {
		super();
		this.id = id;
		this.name = name;
		this.pageNum = pageNum;
	}



	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPageNum() {
		return pageNum;
	}
	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}



	@Override
	public String toString() {
		return "ContentItem [id=" + id + ", name=" + name + ", pageNum=" + pageNum + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((pageNum == null) ? 0 : pageNum.hashCode());
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
		ContentItem other = (ContentItem) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (pageNum == null) {
			if (other.pageNum != null)
				return false;
		} else if (!pageNum.equals(other.pageNum))
			return false;
		return true;
	}
	
	
	

}
