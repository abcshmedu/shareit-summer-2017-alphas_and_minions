package models;

public class Copy {

	Medium medium;
	String owner;
	
	Copy(String owner, Medium medium) {
		this.owner = owner;
		this.medium = medium;
	}

	/**
	 * gets the medium.
	 * @return the medium
	 */
	public Medium getMedium() {
		return medium;
	}

	/**
	 * gets the owner.
	 * @return the owner
	 */
	public String getOwner() {
		return owner;
	}
	
	
}
