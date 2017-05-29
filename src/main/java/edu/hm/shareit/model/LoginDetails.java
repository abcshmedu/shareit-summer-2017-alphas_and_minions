package edu.hm.shareit.model;

public class LoginDetails {
    
    private String username;
    private String password;
    
    public LoginDetails(String username, String password) {
        this.username = username;
        this.password = password;
        
    }
    
    
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
