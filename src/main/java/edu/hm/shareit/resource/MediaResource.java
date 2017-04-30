/**
 * 
 */
package edu.hm.shareit.resource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Medium;
import edu.hm.shareit.service.MediaService;
import edu.hm.shareit.service.MediaServiceImpl;
import edu.hm.shareit.service.MediaServiceResult;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
@Path("media")
public class MediaResource {
	
	MediaService service;
	/**
	 * MediaResource creates media resource.
	 */
	public MediaResource() {
		service = new MediaServiceImpl();
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
		MediaServiceResult result = service.addBook(book);
		
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
		Medium[] books = service.getBooks();
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
	
	/**
	 * gets a book with isbn.
	 * @return book.
	 */
	@GET
	@Consumes({"text/plain", "application/json"})
	@Produces("application/json")
	@Path("books/{isbn}")
	public Response getBook(@PathParam("isbn") String isbn) {
		System.out.println("kalled get with isbn param");
		Optional<Medium> book = service.getBook(isbn);
		ObjectMapper mapper = new ObjectMapper();
		
		if (book.isPresent()) {
			System.out.println("book present!"); // TODO kill debug
			ObjectNode node = mapper.valueToTree(book.get());
			return Response
					.status(MediaServiceResult.OK.getErrorNum())
					.entity(node)
					.build();
		} else {
			return Response
					.status(MediaServiceResult.NOT_FOUND.getErrorNum())
					.entity(errorMessageJSON(MediaServiceResult.NOT_FOUND).toString())
					.build();
		}
	}
	
	
	/**
	 * updates book information.
	 * @param book
	 * @return response
	 */
	@PUT
	@Consumes("application/json")
	@Produces({"application/json", "text/plain"})
	@Path("books/{isbn}")
	public Response updateBook(@PathParam("isbn") String isbn, Book book) {
		System.out.println("Entered updateBook");
		Response response;
		if (!isbn.equals(book.getIsbn())) {
			response = Response.status(MediaServiceResult.ISBN_NOT_EQUAL.getErrorNum())
			.entity(errorMessageJSON(MediaServiceResult.ISBN_NOT_EQUAL).toString())
			.build();
		}
		else {
			System.out.println("Entered first else");
			MediaServiceResult result = service.updateBook(book);
			System.out.println("just got result back");
			if (result == MediaServiceResult.OK) {
				response = Response.status(MediaServiceResult.OK.getErrorNum())
						.entity(errorMessageJSON(MediaServiceResult.OK).toString())
						.build();
			}
			else if (result == MediaServiceResult.NOT_FOUND) {
				response = Response.status(MediaServiceResult.NOT_FOUND.getErrorNum())
						.entity(errorMessageJSON(MediaServiceResult.NOT_FOUND).toString())
						.build();
			}
			else {
				response = Response.status(MediaServiceResult.BAD_REQUEST.getErrorNum()).entity("ERROR!").build();
			}			
		}
		return response;
	}
	
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
