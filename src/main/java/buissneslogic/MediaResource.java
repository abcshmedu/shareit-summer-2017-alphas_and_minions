/**
 * 
 */
package buissneslogic;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
@Path("media")
class MediaResource {
	
	MediaService mediaServiceImpl;
	/**
	 * MediaResource creates media resource.
	 */
	protected MediaResource() {
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
		MediaServiceResult result = mediaServiceImpl.addBook(book);
		
		if (result == MediaServiceResult.OK) {
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.valueToTree(book);
			return Response
					.status(MediaServiceResult.OK.getErrorNum())
					.entity(node)
					.build();
		} else {
			return Response
					.status(result.getErrorNum())
					.entity(errorMessageJSON(result).toString())
					.build();
		}
	}
	
//	/**
//	 * gets all books.
//	 * @return response
//	 */
//	@GET
//	@Path("/books")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response getBooks() {
//		MediaServiceResult result = MediaServiceResult.OK;
//		Book [] books = (Book [])mediaServiceImpl.getBooks(result);
//		ObjectMapper mapper = new ObjectMapper();
//		
//		if (result == MediaServiceResult.OK) {
//			ObjectNode node = mapper.valueToTree(books);
//			return Response
//					.status(result.getErrorNum())
//					.entity(node)
//					.build();			
//		} else {
//			JSONObject obj = errorMessageJSON(result);
//			return Response
//					.status(result.getErrorNum())
//					.entity(obj)
//					.build();
//		}
//
//		
//	}
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
