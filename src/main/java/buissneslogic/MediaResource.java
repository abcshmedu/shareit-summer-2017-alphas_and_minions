/**
 * 
 */
package buissneslogic;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import models.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
@Path("/media")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class MediaResource {
	
	MediaServiceImpl mediaServiceImpl;
	/**
	 * MediaResource creates media resource.
	 */
	protected MediaResource() {
		this.mediaServiceImpl = new MediaServiceImpl();
	}
	
	/**
	 * creates a new book.
	 * @param book
	 * @return response
	 */
	@POST
	@Path("/books")
	@Produces("application/json")
	public Response createBook(@PathParam("book")Book book) {
		MediaServiceResult result = mediaServiceImpl.addBook(book);
		JSONObject jsonObj = new JSONObject();
		
		if (result == MediaServiceResult.OK) {
			jsonObj.put(book.getIsbn(), book); // store books with isbn key
			return Response
					.status(MediaServiceResult.OK.getErrorNum())
					.entity(jsonObj)
					.build();
		} else {
			jsonObj.put("code", result); // build json with code and error message
			jsonObj.put("detail", MediaServiceResult.getErrorMessage(result)); // gives error message back in the form of simpleGeo (see 03-REST p.41)
			return Response.status(result.getErrorNum()).entity(jsonObj).build();
		}
	}
	
	/**
	 * gets a book.
	 * @return response
	 */
	@GET
	@Path("/books")
	public Response getBooks() {
		// todo 
		Book [] books = (Book [])mediaServiceImpl.getBooks();
		
		return null;
	}
	
	
	/**
	 * updates book information.
	 * @param book
	 * @return response
	 */
	@PUT
	@Path("/books/{isbn}")
	public Response updateBook(@PathParam("book")Book book) {
		
		// find book and replace book with updated book
		Book [] books = (Book []) mediaServiceImpl.getBooks();
		
		for (Book oldBook : books) { // find book that need to be updated
			if (oldBook.getIsbn().equals(book.getIsbn())) { // what happens when isbn changes??? or make field final!
				
			}
		}
		
		
		return null;
	}
	
	
}
