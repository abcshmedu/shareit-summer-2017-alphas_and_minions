package edu.hm.shareit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a disc.
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 *
 */
@Entity
@Table (name = "TDisc")
public class Disc extends Medium {

    @Column private String director;
    @Column private int fsk;

    /**
     * Creates an empty disc.
     */
    public Disc() {
        super("", ""); // title, ID ( = barcode)
        director = "";
        fsk = -1;
    }

    /**
     * Creates an actual disc.
     * 
     * @param barcode
     *            Barcode of this disc.
     * @param director
     *            Director of this disc.
     * @param fsk
     *            FSK of this disc.
     * @param title
     *            Title of this disc.
     */
    public Disc(String barcode, String director, int fsk, String title) {
        super(title, barcode);
        this.director = director;
        this.fsk = fsk;
    }

    /**
     * gets the Director.
     * 
     * @return the director
     */
    public String getDirector() {
        return director;
    }
    
    @Override
    @JsonProperty("barcode")
    public String getID() {
        return super.getID();
    }

    /**
     * gets the FKS number of the disk.
     * 
     * @return the fsk
     */
    public int getFsk() {
        return fsk;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Disc [barcode=" + getID() + ", director=" + director + ", fsk=" + fsk + ", getTitle()=" + getTitle()
                + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((getID() == null) ? 0 : getID().hashCode());
        result = prime * result + ((director == null) ? 0 : director.hashCode());
        result = prime * result + fsk;
        return result;
    }
    
    private final int size = 10;
    public boolean checkBarcode() {
        return getID().length() < size;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Disc other = (Disc) obj;
        if (getID() == null) {
            if (other.getID()!= null) {
                return false;
            }
        } else if (!getID().equals(other.getID())) {
            return false;
        }
        if (director == null) {
            if (other.director != null) {
                return false;
            }
        } else if (!director.equals(other.director)) {
            return false;
        }
        if (fsk != other.fsk) {
            return false;
        }
        return true;
    }
}
