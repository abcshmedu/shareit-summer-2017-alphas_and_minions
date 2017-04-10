package models;

public class Copy {

	Medium medium;
	String owner;
	
	Copy(String owner, Medium medium) {
		this.owner = owner;
		this.medium = medium;
	}

	/**
	 * @return the medium
	 */
	public Medium getMedium() {
		return medium;
	}

	/**
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	
	
}
