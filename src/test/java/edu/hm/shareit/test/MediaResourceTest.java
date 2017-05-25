//package edu.hm.shareit.test;
//
//import static org.junit.Assert.*;
//
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import javax.ws.rs.core.Response;
//
//import org.json.JSONObject;
////import org.eclipse.jetty.http.HttpStatus;
////import org.eclipse.jetty.server.Response;
////import org.eclipse.jetty.server.Server;
////import org.eclipse.jetty.servlet.DefaultServlet;
////import org.eclipse.jetty.servlet.ServletContextHandler;
////import org.eclipse.jetty.servlet.ServletHolder;
////import org.eclipse.jetty.webapp.WebAppContext;
//import org.junit.Test;
//import org.junit.runners.Parameterized.Parameters;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;
//
//import edu.hm.shareit.model.Book;
//import edu.hm.shareit.model.Disc;
//import edu.hm.shareit.model.Medium;
//import edu.hm.shareit.resource.MediaResource;
//import edu.hm.shareit.service.MediaServiceResult;
//
//public class MediaResourceTest {
//
//    public static final String APP_URL = "/";
//    public static final int PORT = 8080;
//    public static final String WEBAPP_DIR = "./src/main/webapp/";
//
//    final String title = "Calculus";
//    final String author = "Adams";
//    final String author2 = "Strang";
//    final String title2 = "Computation Science";
//    final String isbn = "978-0-306-40615-7";
//    final String isbn2 = "978-3-16-148410-0";
//    final String invalidISBN = "978-3-16-148410-8";
//    final String title3 = "Guardians of the Galaxy";
//    final String director = "Star Lord";
//    final String barcode = "0000000000";
//    final String barcode2 = "0000000001";
//    final int fsk = 18;
//    MediaServiceResult result = MediaServiceResult.IM_A_TEAPOT;
//    Book testB1 = new Book(title, author, isbn);
//    Book testB2 = new Book(title, author, isbn2);
//
//    MediaResource resource;
//
//    public MediaResourceTest() {
//        resource = new MediaResource();
//        resource.clearDatabase(); // start with empty database for tests
//    }
//
//    @Test
//    public void testCasesBook() throws JsonProcessingException {
//        Book book1 = new Book(title, author, isbn);
//        Response expected = responseBuilder(MediaServiceResult.OK);
//        // getBooks
//        Response response = resource.getBooks();
//        expected = responseBuilder(MediaServiceResult.NOT_FOUND);
//        // assertEquals(expected.getStatus(), response.getStatus());
//        // assertEquals(expected.getEntity(), response.getEntity());
//
//        // ok
//        response = resource.createBook(book1);
//        expected = responseBuilder(MediaServiceResult.OK);
//        assertEquals(response.getStatus(), response.getStatus());
//        assertEquals(response.getEntity(), response.getEntity());
//
//        // isbn reserved
//        response = resource.createBook(book1);
//        expected = responseBuilder(MediaServiceResult.ISBN_RESERVED);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // invalid isbn
//        response = resource.createBook(new Book(title, author, invalidISBN));
//        expected = responseBuilder(MediaServiceResult.INVALID_ISBN);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // missing info
//        response = resource.createBook(new Book("", author, isbn));
//        expected = responseBuilder(MediaServiceResult.MISSING_INFO);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.createBook(new Book(title, "", isbn));
//        expected = responseBuilder(MediaServiceResult.MISSING_INFO);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // get books
//        response = resource.getBook(book1.getIsbn());
//        expected = jsonBuilder(book1);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.getBook(isbn2);
//        expected = responseBuilder(MediaServiceResult.NOT_FOUND);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // update book
//        response = resource.updateBook(isbn, new Book("updated", "", isbn));
//        expected = responseBuilder(MediaServiceResult.OK);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.updateBook(isbn2, new Book("updated", "", isbn));
//        expected = responseBuilder(MediaServiceResult.ISBN_NOT_EQUAL);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        Book updatedBook = new Book("updated", author, isbn);
//        // get book
//        response = resource.getBook(book1.getIsbn());
//        expected = jsonBuilder(updatedBook);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // get books
//        response = resource.getBooks();
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals("[" + jsonBuilder(updatedBook).getEntity() + "]", response.getEntity());
//    }
//
//    @Test
//    public void testCasesDiscs() throws JsonProcessingException {
//        Disc disc1 = new Disc(barcode, director, fsk, title3);
//        Response expected = responseBuilder(MediaServiceResult.OK);
//        // getDiscs
//        Response response = resource.getDiscs();
//        expected = responseBuilder(MediaServiceResult.NOT_FOUND);
//        // assertEquals(expected.getStatus(), response.getStatus());
//        // assertEquals(expected.getEntity(), response.getEntity());
//
//        // ok
//        response = resource.createDisc(disc1);
//        expected = responseBuilder(MediaServiceResult.OK);
//        assertEquals(response.getStatus(), response.getStatus());
//        assertEquals(response.getEntity(), response.getEntity());
//
//        // barcode reserved
//        response = resource.createDisc(disc1);
//        expected = responseBuilder(MediaServiceResult.BARCODE_RESERVED);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // invalid barcode
//        response = resource.createDisc(new Disc("", director, fsk, title3));
//        expected = responseBuilder(MediaServiceResult.MISSING_BARCODE);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // missing info
//        response = resource.createDisc(new Disc(barcode, "", fsk, title3));
//        expected = responseBuilder(MediaServiceResult.MISSING_INFO);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.createDisc(new Disc(barcode2, director, -1, title));
//        expected = responseBuilder(MediaServiceResult.MISSING_INFO);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.createDisc(new Disc(barcode2, director, fsk, ""));
//        expected = responseBuilder(MediaServiceResult.MISSING_INFO);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        String discs = resource.getDiscs().getEntity().toString();
//
//        // get books
//        response = resource.getDisc(disc1.getBarcode());
//        expected = jsonBuilder(disc1);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.getDisc(barcode2);
//        expected = responseBuilder(MediaServiceResult.NOT_FOUND);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // update disc
//        response = resource.updateDisc(barcode, new Disc(barcode, "", -1, "updated"));
//        expected = responseBuilder(MediaServiceResult.OK);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        response = resource.updateDisc(barcode2, new Disc(barcode, "", -1, "updated"));
//        expected = responseBuilder(MediaServiceResult.BARCODE_NOT_EQUAL);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        Disc updatedDisc = new Disc(barcode, director, fsk, "updated");
//        // get book
//        response = resource.getDisc(updatedDisc.getBarcode());
//        expected = jsonBuilder(updatedDisc);
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals(expected.getEntity(), response.getEntity());
//
//        // get books
//        response = resource.getDiscs();
//        assertEquals(expected.getStatus(), response.getStatus());
//        assertEquals("[" + jsonBuilder(updatedDisc).getEntity() + "]", response.getEntity());
//    }
//
//    private Response responseBuilder(MediaServiceResult result) {
//        String message = MediaServiceResult.getErrorMessage(result);
//        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("code", result.getErrorNum());
//        jsonObj.put("detail", MediaServiceResult.getErrorMessage(result));
//        return Response.status(result.getErrorNum()).entity(jsonObj.toString()).build();
//    }
//
//    private Response jsonBuilder(Medium medium) {
//        ObjectMapper mapper = new ObjectMapper();
//        ObjectNode node = mapper.valueToTree(medium);
//        return Response.status(MediaServiceResult.OK.getErrorNum()).entity(node).build();
//    }
//
//}
