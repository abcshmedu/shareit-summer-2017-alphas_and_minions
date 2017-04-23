/**
 * 
 */
package buissneslogic;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import models.*;

/**
 * @author Michael Eggers
 * @author Rebecca Brydon
 */
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
	public Response createBook(Book book) {
		MediaServiceResult result = mediaServiceImpl.addBook(book);
		JSONObject jsonObj = new JSONObject();
		
		if (result == MediaServiceResult.OK) {
			
			ObjectMapper mapper = new ObjectMapper();
			ObjectNode node = mapper.valueToTree(book);
			return Response
					.status(MediaServiceResult.OK.getErrorNum())
					.entity(node)
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
	public Response getBooks() {
		
		Book [] books = (Book [])mediaServiceImpl.getBooks();
		ObjectMapper mapper = new ObjectMapper();
		
		if (books != null) {
			if (books.length == 0) {
				ObjectNode node = mapper.valueToTree(books);
				return Response
						.status(MediaServiceResult.OK.getErrorNum())
						.entity(node)
						.build();
			} else {
				MediaServiceResult result = MediaServiceResult.NO_BOOKS;
				ObjectNode node = mapper.valueToTree(MediaServiceResult.getErrorMessage(result));
				return Response.status(result.getErrorNum()).entity(node).build();
			}
		} else {
			MediaServiceResult result = MediaServiceResult.BAD_REQUEST;
			ObjectNode node = mapper.valueToTree(result);
			return Response.status(result.getErrorNum()).entity(node).build();
		}
		

	}
	
	
	/**
	 * updates book information.
	 * @param book
	 * @return response
	 */
	public Response updateBook(Book book) {
		
		// find book and replace book with updated book
		MediaServiceResult result = mediaServiceImpl.updateBook(book);
		ObjectMapper mapper = new ObjectMapper();
		if (result == MediaServiceResult.OK) {
			
			ObjectNode node = mapper.valueToTree(book);
			return Response
					.status(MediaServiceResult.OK.getErrorNum())
					.entity(node)
					.build();
		} else {
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("code", result);
			jsonObj.put("detail", MediaServiceResult.getErrorMessage(result));
			return Response.status(result.getErrorNum()).entity(jsonObj.toString()).build();
		}
		
	}
	
	
}
