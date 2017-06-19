package edu.hm.shareit.resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.hm.shareit.auth.AuthInterface;
import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;
import edu.hm.shareit.service.MediaService;
import edu.hm.shareit.service.MediaServiceResult;

/**
 * The interface that talks to the clients requests.
 * 
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
@Path("media")
public class MediaResource {

	/**
	 * Service does all the logic.
	 */
	private MediaService service;
	private AuthInterface auth;

	/**
	 * MediaResource creates media resource.
	 */
	@Inject
	public MediaResource(MediaService service, AuthInterface auth) {
	    this.service = service;
	    this.auth = auth;
	}


	/**
	 * Getter for service.
	 * 
	 * @return A mediaservice.
	 */
	public MediaService getService() {
		return service;
	}

	/**
	 * Creates a new book.
	 * 
	 * @param book
	 *            The book data in json.
	 * 
	 * @return response Feedback to caller.
	 * @throws IOException 
	 */
	@POST
	@Path("books/{token}")
	@Consumes({"application/json","text/plain"})
	@Produces("application/json")
	public Response createBook(Book book, @PathParam("token") final String token) throws IOException {
	   
		MediaServiceResult result;
	    String authResponse = authenticate(token);
	        
	    if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
	        result = service.addBook(book);
	    } else {
	        result = MediaServiceResult.IM_A_TEAPOT;
	    }

		return Response.status(result.getErrorNum()).entity(errorMessageJSON(result).toString()).build();

	}
	
	// start auth server with:
	// mvn -Djetty.http.port=9999 jetty:run
	private String authenticate(final String token) throws IOException {
		return auth.authenticate(token);
	}

	/**
	 * Creates a new Disc.
	 * 
	 * @param disc
	 *            The disc data in json.
	 * 
	 * @return response Feedback to caller.
	 * @throws IOException
	 */
	@POST
	@Path("discs/{token}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createDisc(Disc disc, @PathParam("token") final String token) throws IOException {

		MediaServiceResult result;
		String authResponse = authenticate(token);
		
		if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
			result = service.addDisc(disc);
		} else {
			result = MediaServiceResult.IM_A_TEAPOT;
		}

		return Response.status(result.getErrorNum()).entity(errorMessageJSON(result).toString()).build();

	}

	/**
	 * Gets all books.
	 * 
	 * @return response All the books in json.
	 * @throws IOException 
	 */
	@GET
	@Path("books/{token}")
	@Consumes("text/plain")
	@Produces("application/json")
	public Response getBooks( @PathParam("token") final String token ) throws IOException {
	    String authResponse = authenticate(token);
	    
	    if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
	        MediaServiceResult result = MediaServiceResult.OK;
	        Medium[] books = service.getBooks();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        if (result == MediaServiceResult.OK) {
	            List<Medium> bookList = Arrays.stream(books).collect(Collectors.toList());
	            String node = mapper.writeValueAsString(bookList);
	            return Response.status(result.getErrorNum()).entity(node).build();
	        } else {
	            JSONObject obj = errorMessageJSON(result);
	            return Response.status(result.getErrorNum()).entity(obj).build();
	        }
	        
	    } else {
	        JSONObject obj = errorMessageJSON(MediaServiceResult.IM_A_TEAPOT);
            return Response.status(MediaServiceResult.IM_A_TEAPOT.getErrorNum()).entity(obj).build();
	    }
	    
	}

	/**
	 * Gets all discs.
	 * 
	 * @return All the discs in json.
	 * @throws JsonProcessingException
	 *             If object could not be serialized to json file.
	 */
	@GET
	@Path("discs/{token}")
	@Produces("application/json")
	public Response getDiscs(@PathParam("token") String token) throws JsonProcessingException, IOException {
	    String authResponse = authenticate(token);
	    if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
	        MediaServiceResult result = MediaServiceResult.OK;
	        Medium[] discs = service.getDiscs();
	        ObjectMapper mapper = new ObjectMapper();
	        
	        if (result == MediaServiceResult.OK) {
	            List<Medium> discList = Arrays.stream(discs).collect(Collectors.toList());
	            String node = mapper.writeValueAsString(discList);
	            return Response.status(result.getErrorNum()).entity(node).build();
	        } else {
	            JSONObject obj = errorMessageJSON(result);
	            return Response.status(result.getErrorNum()).entity(obj).build();
	        }
	        
	    } else {
            JSONObject obj = errorMessageJSON(MediaServiceResult.IM_A_TEAPOT);
            return Response.status(MediaServiceResult.IM_A_TEAPOT.getErrorNum()).entity(obj).build();
	    }
	}

	/**
	 * Gets a book with isbn.
	 * 
	 * @param isbn
	 *            ISBN of the wanted book.
	 * 
	 * @return response Response to caller.
	 * @throws IOException 
	 */
	@GET
	@Consumes({ "text/plain", "text/plain" })
	@Produces("application/json")
	@Path("book/{isbn}/{token}")
	public Response getBook(@PathParam("isbn") String isbn, @PathParam("token") String token) throws IOException {
	    
	    String authResponse = authenticate(token);
	    
	    if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
	        Optional<Medium> book = service.getBook(isbn);
	        ObjectMapper mapper = new ObjectMapper();
	        
	        if (book.isPresent()) {
	            ObjectNode node = mapper.valueToTree(book.get());
	            return Response.status(MediaServiceResult.OK.getErrorNum()).entity(node).build();
	        } else {
	            return Response.status(MediaServiceResult.NOT_FOUND.getErrorNum())
	                    .entity(errorMessageJSON(MediaServiceResult.NOT_FOUND).toString()).build();
	        }	        
	    } else {
	        JSONObject obj = errorMessageJSON(MediaServiceResult.IM_A_TEAPOT);
            return Response.status(MediaServiceResult.IM_A_TEAPOT.getErrorNum()).entity(obj).build();
	    }

	    
	}

	/**
	 * Gets a disc by its barcode.
	 * 
	 * @param barcode
	 *            Barcode of the wanted disc.
	 * 
	 * @return Response to caller.
	 * @throws IOException 
	 */
	@GET
	@Consumes({ "text/plain", "text/plain"})
	@Produces("application/json")
	@Path("disc/{barcode}/{token}")
	public Response getDisc(@PathParam("barcode") String barcode, @PathParam("token") String token) throws IOException {
	    String authResponse = authenticate(token);
        if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
            Optional<Medium> disc = service.getDisc(barcode);
            ObjectMapper mapper = new ObjectMapper();
            
            if (disc.isPresent()) {
                ObjectNode node = mapper.valueToTree(disc.get());
                return Response.status(MediaServiceResult.OK.getErrorNum()).entity(node).build();
            } else {
                return Response.status(MediaServiceResult.NOT_FOUND.getErrorNum())
                        .entity(errorMessageJSON(MediaServiceResult.NOT_FOUND).toString()).build();
            }
        } else {
            JSONObject obj = errorMessageJSON(MediaServiceResult.IM_A_TEAPOT);
            return Response.status(MediaServiceResult.IM_A_TEAPOT.getErrorNum()).entity(obj).build();
        }
	    
	}

	/**
	 * Updates book information.
	 * 
	 * @param isbn
	 *            ISBN of book to update.
	 * 
	 * @param book
	 *            New book data.
	 * 
	 * @return response Response to caller.
	 * @throws IOException 
	 */
	@PUT
	@Consumes("application/json")
	@Produces("application/json")
	@Path("book/update/{isbn}/{token}")
	public Response updateBook(Book book, @PathParam("isbn") String isbn, @PathParam("token") String token) throws IOException {
	    String authResponse = authenticate(token);
        System.out.println("here???");
        if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
            MediaServiceResult result;
            if (!isbn.equals(book.getID())) {
                result = MediaServiceResult.ISBN_NOT_EQUAL;
            } else {
                result = service.updateBook(book);
            }
            
            return Response.status(result.getErrorNum()).entity(errorMessageJSON(result).toString()).build();
            
        } else {
            JSONObject obj = errorMessageJSON(MediaServiceResult.IM_A_TEAPOT);
            return Response.status(MediaServiceResult.IM_A_TEAPOT.getErrorNum()).entity(obj).build();
        }
	    
	    
	}

	/**
	 * Updates disc information.
	 * 
	 * @param barcode
	 *            Barcode of disc to update.
	 * 
	 * @param disc
	 *            New disc data.
	 * 
	 * @return response Response to caller.
	 * @throws IOException 
	 */
	@PUT
	@Consumes({"text/plain","text/plain","application/json"})
	@Produces({ "application/json"})
	@Path("disc/update/{barcode}/{token}")
	public Response updateDisc(@PathParam("barcode") String barcode, @PathParam("token") String token, Disc disc) throws IOException {
	    String authResponse = authenticate(token);
        
        if (authResponse.equals(Integer.toString(MediaServiceResult.OK.getErrorNum()))) {
            MediaServiceResult result;
            if (!barcode.equals(disc.getID())) {
                result = MediaServiceResult.BARCODE_NOT_EQUAL;
            } else {
                result = service.updateDisc(disc);
            }
            
            return Response.status(result.getErrorNum()).entity(errorMessageJSON(result).toString()).build();            
        } else {
            JSONObject obj = errorMessageJSON(MediaServiceResult.IM_A_TEAPOT);
            return Response.status(MediaServiceResult.IM_A_TEAPOT.getErrorNum()).entity(obj).build();
        }
	    
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

	public void clearDatabase() {
		service.clearDatabase();
	}

}
