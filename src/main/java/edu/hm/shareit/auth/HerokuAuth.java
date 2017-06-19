package edu.hm.shareit.auth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HerokuAuth implements AuthInterface{

    private final String httpsURL = "https://shareit-auth.herokuapp.com/auth/users/";
    
    public HerokuAuth() {
        
    }
    
    @Override
    public String authenticate(final String token) throws IOException {
        String result = "";
        URL targetURL = new URL(httpsURL + token);
        HttpsURLConnection connection = (HttpsURLConnection)targetURL.openConnection();

        try (InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr)) {

            result = in.readLine();
        }

        return result;
    }

}
