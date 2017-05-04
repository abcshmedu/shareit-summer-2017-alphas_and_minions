package edu.hm.shareit.model;

/**
 * Owner class. Not used yet.
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
public class Copy {

    private final Medium medium;
    private final String owner;

    /**
     * Creates a new owner.
     * 
     * @param owner
     *            Name of the owner.
     * @param medium
     *            The medium he/she owns.
     */
    Copy(String owner, Medium medium) {
        this.owner = owner;
        this.medium = medium;
    }

    /**
     * Gets the medium.
     * 
     * @return the medium.
     */
    public Medium getMedium() {
        return medium;
    }

    /**
     * gets the owner.
     * 
     * @return the owner
     */
    public String getOwner() {
        return owner;
    }

}
