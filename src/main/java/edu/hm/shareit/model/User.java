package edu.hm.shareit.model;

/**
 * Represents a User.
 * 
 * @author Rebecca Brydon
 * @author Michael Eggers
 *
 */
public class User {

	private String forename;
	private String surename;
	private String username;
	private String pwd;
	
	/**
	 * Default Ctor.
	 */
	public User() {
	    
	}
	
	/**
	 * @param forename Forename.
	 * @param surename Surename.
	 * @param username Username.
	 * @param pwd Password.
	 */
	public User(final String forename, final String surename, final String username, final String pwd) {
		this.forename = forename;
		this.surename = surename;
		this.username = username;
		this.setPwd(pwd);
	}

	/**
	 * @return This forename.
	 */
	public String getForename() {
		return forename;
	}

	/**
	 * @return This surename.
	 */
	public String getSurename() {
		return surename;
	}

	/**
	 * @return This username.
	 */
	public String getUsername() {
		return username;
	}

    /**
     * @return This password.
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * @param pwd Set the password for this user.
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    
    @Override
    public String toString() {
    	
    	return String.format("[ forename: %s, surename: %s, username: %s, password: %s ]", getForename(), getSurename(), getUsername(), getPwd());
    }
	
	
	
	
}
