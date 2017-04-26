package edu.hm.shareit.model;

public class Disc extends Medium {

	String barcode;
	String director;
	int fsk;
	
	Disc(String title) {
		super(title);
	}

	Disc(String barcode, String director, int fsk, String title) {
		super(title);
		this.barcode = barcode;
		this.director = director;
		this.fsk = fsk;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Disc [barcode=" + barcode + ", director=" + director + ", fsk=" + fsk
				+ ", getTitle()=" + getTitle() + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((barcode == null) ? 0 : barcode.hashCode());
		result = prime * result + ((director == null) ? 0 : director.hashCode());
		result = prime * result + fsk;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Disc other = (Disc) obj;
		if (barcode == null) {
			if (other.barcode != null)
				return false;
		} else if (!barcode.equals(other.barcode))
			return false;
		if (director == null) {
			if (other.director != null)
				return false;
		} else if (!director.equals(other.director))
			return false;
		if (fsk != other.fsk)
			return false;
		return true;
	}

	/**
	 * gets the barcode.
	 * @return the barcode
	 */
	public String getBarcode() {
		return barcode;
	}

	/**
	 * gets the Director.
	 * @return the director
	 */
	public String getDirector() {
		return director;
	}

	/**
	 * gets the FKS number of the disk.
	 * @return the fsk
	 */
	public int getFsk() {
		return fsk;
	}
	
	/**
	 * gets the title of the disc.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return super.getTitle();
	}


	

}
