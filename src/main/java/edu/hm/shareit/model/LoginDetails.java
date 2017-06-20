package edu.hm.shareit.model;

/**
 * Represents login credentials.
 * 
 * @author Rebecca Brydon, Michael Eggers
 *
 */
public class LoginDetails {
    
    private String username;
    private String password;
    
    /**
     * Creates a login set.
     * 
     * @param username Username.
     * @param password Password.
     */
    public LoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
        
    }
    
    /**
     * Default Ctor.
     */
    public LoginDetails() {
        
    }
    
    
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }


    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

}
