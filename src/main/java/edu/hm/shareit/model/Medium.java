/**
 * 
 */
package edu.hm.shareit.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 * Medium class that represent a Medium of some kind.
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
@Entity
@Table (name = "TMedium")
@Inheritance (strategy = InheritanceType.JOINED)
public abstract class Medium implements Serializable {

    @Id
    private String id; 
    
	@Column
    private String title;
	
	/**
	 * Empty Ctor needed for Hibernate.
	 */
	public Medium() {
		title = "";
		id = "";
	}

	/**
	 * Base of medium. Useless without concrete derived classe's Ctor.
	 * 
	 * @param title This medium's title.
	 * @param id This medium's ID, which is Barcode for Discs, ISBN for Books.
	 */
    public Medium(String title, String id) {
        this.title = title;
        this.id = id;
    };
    
    /**
     * Getter for ID.
     * 
     * @return This ID.
     */
    public String getID() {
    	return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Medium other = (Medium) obj;
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }

    /**
     * gets the title.
     * 
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Medium [title=" + title + "]";
    }

}
