package edu.hm.shareit.mockitoTests;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.hm.shareit.auth.AuthInterface;
import edu.hm.shareit.auth.HerokuAuth;
import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;
import edu.hm.shareit.resource.MediaResource;
import edu.hm.shareit.service.MediaServiceImpl;
import edu.hm.shareit.service.MediaServiceResult;

public class MediaResourceMockitoTest {

    @Mock
    MediaServiceImpl service;
    
    @Mock
    AuthInterface auth;
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    
    
    @Test
    public void testGetBook() throws JsonProcessingException {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        
        MediaResource resource = new MediaResource(service, auth);
                
        // OK
        when(service.getBook(isbn)).thenReturn(Optional.of(hobbit));
        Response result = resource.getBook(isbn);
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(hobbit);
        assertEquals(result.getStatus(), MediaServiceResult.OK.getErrorNum());        
        assertEquals(result.getEntity().toString(),node);
        
        // Not found
        when(service.getBook(isbn)).thenReturn(Optional.empty());
        result = resource.getBook(isbn);
        assertEquals(result.getStatus(), MediaServiceResult.NOT_FOUND.getErrorNum());
    }
    
    @Test
    public void testGetDisc() throws JsonProcessingException {
        String barcode = "000000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        
        MediaResource resource = new MediaResource(service, auth);

        
        // Ok
        when(service.getDisc(barcode)).thenReturn(Optional.of(twoTowers));
        Response result = resource.getDisc(barcode);
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(twoTowers);
        assertEquals(result.getStatus(), MediaServiceResult.OK.getErrorNum());
        assertEquals(result.getEntity().toString(), node);
        
        // NOt founrd
        when(service.getDisc(barcode)).thenReturn(Optional.empty());
        result = resource.getDisc(barcode);
        assertEquals(result.getStatus(), MediaServiceResult.NOT_FOUND.getErrorNum());
    }
    
    
    @Test
    public void testGetBooks() throws JsonProcessingException, IOException { 
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        Book lordOfTheRings = new Book("The Felowship of the Ring","JRR Tolkin",isbn);
        Medium[] list = {hobbit, lordOfTheRings};
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(list);
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        
        MediaResource resource = new MediaResource(service, auth);
        
        // Ok
        when(service.getBooks()).thenReturn(list);
        when(auth.authenticate("0")).thenReturn("200");
        Response result = resource.getBooks("0");
        assertEquals(MediaServiceResult.OK.getErrorNum(), result.getStatus());
        assertEquals(node, result.getEntity());
        
        // I'm a teapot = meaning authentication is not valid
        when(auth.authenticate("1")).thenReturn("");
        result = resource.getBooks("1");
        assertEquals(MediaServiceResult.IM_A_TEAPOT.getErrorNum(), result.getStatus());
    }
    
    //@Test
    public void testGetDiscs() throws IOException {
        String barcode = "000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        Disc theReturnOfTheKing = new Disc(barcode,"Peter Jackson",12,"The Retur of the King");
        Medium[] list = {twoTowers,theReturnOfTheKing};
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(list);
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        MediaResource resource = new MediaResource(service, auth);
        
        // OK
        when(service.getDiscs()).thenReturn(list);
        when(auth.authenticate("0")).thenReturn("200");
        Response result = resource.getDiscs("0");
        assertEquals(MediaServiceResult.OK.getErrorNum(), result.getStatus());
        assertEquals(node, result.getEntity());
        // Im a teapot
        when(auth.authenticate("1")).thenReturn("418");
        result = resource.getDiscs("1");
        assertEquals(MediaServiceResult.IM_A_TEAPOT.getErrorNum(),result.getStatus());
    }
    
    @Test
    public void testCreateBook() throws JsonProcessingException {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(hobbit);
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        MediaResource resource = new MediaResource(service, auth);
        
        //OK
        when(service.addBook(hobbit)).thenReturn(MediaServiceResult.OK);
        Response result = resource.createBook(hobbit);
        assertEquals(MediaServiceResult.OK.getErrorNum(), result.getStatus());
        
        // isbn not valid
        when(service.addBook(hobbit)).thenReturn(MediaServiceResult.INVALID_ISBN);
        result = resource.createBook(hobbit);
        assertEquals(MediaServiceResult.INVALID_ISBN.getErrorNum(), result.getStatus());
        
        // info missing
        when(service.addBook(hobbit)).thenReturn(MediaServiceResult.MISSING_INFO);
        result = resource.createBook(hobbit);
        assertEquals(MediaServiceResult.MISSING_INFO.getErrorNum(), result.getStatus());
        
        // isbn reserved
        when(service.addBook(hobbit)).thenReturn(MediaServiceResult.ISBN_RESERVED);
        result = resource.createBook(hobbit);
        assertEquals(MediaServiceResult.ISBN_RESERVED.getErrorNum(), result.getStatus());
    }
    
    
    private final String token = "0";
    @Test
    public void testCreateDisc() throws JsonProcessingException, IOException {
        
        String barcode = "000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        MediaResource resource = new MediaResource(service, auth);
        
        // ok
        when(service.addDisc(twoTowers)).thenReturn(MediaServiceResult.OK);
        when(auth.authenticate(token)).thenReturn("200");
        Response result = resource.createDisc(twoTowers, token);
        assertEquals(MediaServiceResult.OK.getErrorNum(), result.getStatus());

        // barcode reserved
        when(service.addDisc(twoTowers)).thenReturn(MediaServiceResult.BARCODE_RESERVED);
        result = resource.createDisc(twoTowers, token);
        assertEquals(MediaServiceResult.BARCODE_RESERVED.getErrorNum(), result.getStatus());
        
        // barcode invalid
        twoTowers = new Disc("000","Peter Jackson",12,"The Two Towers");
        when(service.addDisc(twoTowers)).thenReturn(MediaServiceResult.INVALID_BARCODE);
        result = resource.createDisc(twoTowers, token);
        assertEquals(MediaServiceResult.INVALID_BARCODE.getErrorNum(), result.getStatus());
        
        // info missing
        twoTowers = new Disc(barcode,"",12,"The Two Towers");
        when(service.addDisc(twoTowers)).thenReturn(MediaServiceResult.MISSING_INFO);
        result = resource.createDisc(twoTowers, token);
        assertEquals(MediaServiceResult.MISSING_INFO.getErrorNum(), result.getStatus());
        
        // invalid token
        when(auth.authenticate(token)).thenReturn("418");
        result = resource.createDisc(twoTowers, token);
        assertEquals(MediaServiceResult.IM_A_TEAPOT.getErrorNum(), result.getStatus());
        
    }
    
    @Test
    public void testUpdateBook() throws JsonProcessingException {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(hobbit);
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        MediaResource resource = new MediaResource(service, auth);
        
        // Ok
        when(service.updateBook(hobbit)).thenReturn(MediaServiceResult.OK);
        Response result = resource.updateBook(isbn, hobbit);
        assertEquals(MediaServiceResult.OK.getErrorNum(), result.getStatus());
        
        // not found
        when(service.updateBook(hobbit)).thenReturn(MediaServiceResult.NOT_FOUND);
        result = resource.updateBook(isbn, hobbit);
        assertEquals(MediaServiceResult.NOT_FOUND.getErrorNum(), result.getStatus());
        
        // isbn not equal
        result = resource.updateBook("978-0-306-40615-8", hobbit);
        assertEquals(MediaServiceResult.ISBN_NOT_EQUAL.getErrorNum(), result.getStatus());
    }
    
    @Test
    public void testUpdateDisc() throws JsonProcessingException {
        String barcode = "000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        
        ObjectMapper mapper = new ObjectMapper();
        String node = mapper.writeValueAsString(twoTowers);
        
        service = mock(MediaServiceImpl.class);
        auth = mock(HerokuAuth.class);
        MediaResource resource = new MediaResource(service, auth);
        
        // ok
        when(service.updateDisc(twoTowers)).thenReturn(MediaServiceResult.OK);
        Response result = resource.updateDisc(barcode, twoTowers);
        assertEquals(MediaServiceResult.OK.getErrorNum(), result.getStatus());
        
        // barcode not equal
        result = resource.updateDisc(barcode+"0", twoTowers);
        assertEquals(MediaServiceResult.BARCODE_NOT_EQUAL.getErrorNum(), result.getStatus()); 
    }

}
