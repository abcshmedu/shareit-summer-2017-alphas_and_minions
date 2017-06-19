package edu.hm.shareit.resource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.shareit.model.LoginDetails;
import edu.hm.shareit.model.User;
import edu.hm.shareit.service.MediaServiceResult;



@Path("users")
public class AuthenticationResource {
    ObjectMapper mapper;
    public AuthenticationResource() {
        mapper = new ObjectMapper();
    }
    
    @POST
    @Path("login")
    @Consumes("application/json")
    public Response login(final LoginDetails login) throws IOException, URISyntaxException {
        String httpURI = "http://localhost:9999/auth/users/login";
        URI redirectURI = new URI(httpURI);
        String jsonString = mapper.writeValueAsString(login);
        return Response.temporaryRedirect(redirectURI).entity(jsonString).build();
    }
    
    @POST
    @Path("register")
    @Consumes("application/json")
    public Response register(final User user) throws IOException, URISyntaxException {
        String httpURI = "http://localhost:9999/auth/users/register";
        URI redirectURI = new URI(httpURI);
        String jsonString = mapper.writeValueAsString(user);
        return Response.temporaryRedirect(redirectURI).entity(jsonString).build();
    }
    
    /**
     * Creates a JSON obj with error message the form of simpleGeo (see 03-REST
     * p.41).
     * 
     * @param result
     *            The Response to convert.
     * 
     * @return A JSON object holding the Response data.
     */
    private JSONObject errorMessageJSON(MediaServiceResult result) {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("code", result.getErrorNum());
        jsonObj.put("detail", MediaServiceResult.getErrorMessage(result));
        return jsonObj;
    }
    
    
    

}
