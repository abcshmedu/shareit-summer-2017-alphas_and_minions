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
import edu.hm.shareit.model.Disc;
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
		MediaServiceResult result = service.addBook(book);

		return Response
				.status(result.getErrorNum())
				.entity(errorMessageJSON(result).toString())
				.build();

	}
	
	@POST
	@Path("discs")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createDisc(Disc disc) {
		MediaServiceResult result = service.addDisc(disc);
		
		return Response
				.status(result.getErrorNum())
				.entity(errorMessageJSON(result).toString())
				.build();
		
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
	
	@GET
	@Path("discs")
	@Produces("application/json")
	public Response getDiscs() throws JsonProcessingException {
		MediaServiceResult result = MediaServiceResult.OK;
		Medium[] discs = service.getDiscs();
		//System.out.println("books array: " + Arrays.toString(books));
		ObjectMapper mapper = new ObjectMapper();
		
		if (result == MediaServiceResult.OK) {
			List<Medium> bookList = Arrays.stream(discs).collect(Collectors.toList());
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
	
	@GET
	@Consumes({"text/plain", "application/json"})
	@Produces("application/json")
	@Path("discs/{barcode}")
	public Response getDisc(@PathParam("barcode") String barcode) {
		System.out.println("kalled get with isbn param");
		Optional<Medium> disc = service.getDisc(barcode);
		ObjectMapper mapper = new ObjectMapper();
		
		if (disc.isPresent()) {
			System.out.println("book present!"); // TODO kill debug
			ObjectNode node = mapper.valueToTree(disc.get());
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
		Response response;
		MediaServiceResult result;
		if (!isbn.equals(book.getIsbn())) {
			result = MediaServiceResult.ISBN_NOT_EQUAL;
		}
		else {
			result = service.updateBook(book);
		}
		response = Response.status(result.getErrorNum())
		.entity(errorMessageJSON(result).toString())
		.build();
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
