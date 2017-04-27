/**
 * 
 */
package edu.hm.shareit.resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import edu.hm.shareit.model.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
@Path("media")
public class MediaResource {
	
	MediaService mediaServiceImpl;
	/**
	 * MediaResource creates media resource.
	 */
	public MediaResource() {
		mediaServiceImpl = new MediaServiceImpl();
	}
	
	/**
	 * creates a new book.
	 * @param book
	 * @return response
	 */
	@POST
	@Path("books")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createBook(Book book) {
		System.out.println("POST called!");
		System.out.println(book);
		MediaServiceResult result = mediaServiceImpl.addBook(book);
		
		if (result == MediaServiceResult.OK) {
			ObjectMapper mapper = new ObjectMapper();
			//mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker().withFieldVisibility(JsonAutoDetect.Visibility.ANY));
			ObjectNode node = mapper.valueToTree(book);
			return Response
					.status(MediaServiceResult.OK.getErrorNum())
					.entity(node)
					.build();
		} 
		else {
			return Response
					.status(result.getErrorNum())
					.entity(errorMessageJSON(result).toString())
					.build();
		}
	}
	
	// Tester
//	@GET
//	@Path("books")
//	@Produces("text/plain")
//	public Response doGet() {
//		return Response.status(200).entity("GET called!").build();
//	}
	
	/**
	 * gets all books.
	 * @return response
	 * @throws JsonProcessingException 
	 */
	@GET
	@Path("books")
	@Produces("application/json")
	public Response getBooks() throws JsonProcessingException {
		MediaServiceResult result = MediaServiceResult.OK;
		Medium[] books = mediaServiceImpl.getBooks();
		//System.out.println("books array: " + Arrays.toString(books));
		ObjectMapper mapper = new ObjectMapper();
		
		if (result == MediaServiceResult.OK) {
			List<Medium> bookList = Arrays.stream(books).collect(Collectors.toList());
			String node = mapper.writeValueAsString(bookList);
			return Response
					.status(result.getErrorNum())
					.entity(node)
					.build();			
		} else {
			JSONObject obj = errorMessageJSON(result);
			return Response
					.status(result.getErrorNum())
					.entity(obj)
					.build();
		}

		
	}
//
//	
//	/**
//	 * gets a book with isbn.
//	 * @return book.
//	 */
//	@GET
//	@Path("/books/{isbn}")
//	public Response getBook(@PathParam("isbn")String isbn) {
//		MediaServiceResult result = MediaServiceResult.OK;
//		Book book = (Book) mediaServiceImpl.getBook(isbn,result);
//		ObjectMapper mapper = new ObjectMapper();
//		
//		if (result == MediaServiceResult.OK) {
//			ObjectNode node = mapper.valueToTree(book);
//			return Response
//					.status(result.getErrorNum())
//					.entity(node)
//					.build();
//		} else {
//			return Response
//					.status(result.getErrorNum())
//					.entity(errorMessageJSON(result).toString())
//					.build();
//		}
//	}
//	
//	
//	/**
//	 * updates book information.
//	 * @param book
//	 * @return response
//	 */
//	public Response updateBook(Book book) {
//		
//		// find book and replace book with updated book
//		MediaServiceResult result = mediaServiceImpl.updateBook(book);
//		ObjectMapper mapper = new ObjectMapper();
//		if (result == MediaServiceResult.OK) {
//			
//			ObjectNode node = mapper.valueToTree(book);
//			return Response
//					.status(MediaServiceResult.OK.getErrorNum())
//					.entity(node)
//					.build();
//		} else {
//			return Response.status(result.getErrorNum()).entity(errorMessageJSON(result)).build();
//		}
//		
//	}
//	
	/**
	 * Creates a JSON obj with error message the form of simpleGeo (see 03-REST p.41).
	 * @param result
	 * @return
	 */
	private JSONObject errorMessageJSON(MediaServiceResult result) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("code", result);
		jsonObj.put("detail", MediaServiceResult.getErrorMessage(result));
		return jsonObj;
	}
	
	
}
