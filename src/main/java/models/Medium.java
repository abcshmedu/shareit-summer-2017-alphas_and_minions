/**
 * 
 */
package models;

/**
 * @author 
 *
 */
public abstract class Medium {
	
	private String title;
	
	Medium(String title){
		this.title = title;
	};
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Medium other = (Medium) obj;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	String getTitle() {
		return title;
	}


	@Override
	public String toString() {
		return "Medium [title=" + title + "]";
	}
	
}
