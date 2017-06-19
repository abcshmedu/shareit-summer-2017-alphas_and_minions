package edu.hm.shareit.mockitoTests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javax.ws.rs.core.Response;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import antlr.collections.List;
import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;
import edu.hm.shareit.persistence.MediaPersistence;
import edu.hm.shareit.persistence.MediaPersistenceImpl;
import edu.hm.shareit.resource.MediaResource;
import edu.hm.shareit.service.MediaServiceImpl;
import edu.hm.shareit.service.MediaServiceResult;

public class MediaServiceMockitoTest {

    @Mock
    MediaPersistence database;
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @Test
    public void testAddBook() {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
        
        when(database.getBook(isbn)).thenReturn(Optional.empty());
        // ok
        MediaServiceResult result = service.addBook(hobbit);
        assertEquals(MediaServiceResult.OK, result);
        
        // invalid isbn
        Book hobbit2 = new Book("The Hobbit","JRR Tolkin","978-0-306-40615-0");
        result = service.addBook(hobbit2);
        assertEquals(MediaServiceResult.INVALID_ISBN, result);
        
        // missing info
        Book hobbit3 = new Book("The Hobbit","",isbn);
        result = service.addBook(hobbit3);
        assertEquals(MediaServiceResult.MISSING_INFO, result);
        
        // isbn reserved
        when(database.getBook(isbn)).thenReturn(Optional.of(hobbit));
        result = service.addBook(hobbit);
        assertEquals(MediaServiceResult.ISBN_RESERVED, result);
    }
    
    @Test
    public void testAddDisc() {
        String barcode = "000000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
        when(database.getDisc(barcode)).thenReturn(Optional.empty());
        // ok
        MediaServiceResult result = service.addDisc(twoTowers);
        assertEquals(MediaServiceResult.OK, result);
        // missing info
        Disc twoTowers2 = new Disc(barcode,"",12,"The Two Towers");
        result = service.addDisc(twoTowers2);
        assertEquals(MediaServiceResult.MISSING_INFO,result);
        
        // invalid barcod
        Disc twoTowers3 = new Disc("0","Peter Jackson",12,"The Two Towers");    
        result = service.addDisc(twoTowers3);
        assertEquals(MediaServiceResult.MISSING_BARCODE,result);
        
        // barcode reserved
        when(database.getDisc(barcode)).thenReturn(Optional.of(twoTowers));
        result = service.addDisc(twoTowers);
        assertEquals(MediaServiceResult.BARCODE_RESERVED, result);        
    }
    
    @Test
    public void testUpdateBook() {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        Book update = new Book("The Hobbit - There and back again","",isbn);
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
        when(database.getBook(isbn)).thenReturn(Optional.of(hobbit));
        
        // ok
        MediaServiceResult result = service.updateBook(update);
        assertEquals(result, MediaServiceResult.OK);
        
        // author
        update = new Book("","J.R.R. Tolkin",isbn);
        when(database.getBook(isbn)).thenReturn(Optional.of(update));
        result = service.updateBook(update);
        assertEquals(result, MediaServiceResult.OK);
        
        // Not found 
        when(database.getBook(isbn)).thenReturn(Optional.empty());
        result = service.updateBook(update);
        assertEquals(result, MediaServiceResult.NOT_FOUND);
    }
    
    
    @Test
    public void testUpdateDisc() {
        String barcode = "000000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        Disc update = new Disc(barcode,"",16,"");
        
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
        when(database.getDisc(barcode)).thenReturn(Optional.of(twoTowers));
        
        // ok
        MediaServiceResult result = service.updateDisc(update);
        assertEquals(MediaServiceResult.OK,result);
        
        // title and director
        
        update = new Disc(barcode,"By Peter Jackson",-1,"The Lord of the Rings: The Two Towers");
        when(database.getDisc(barcode)).thenReturn(Optional.of(update));
        result = service.updateDisc(update);
        assertEquals(MediaServiceResult.OK,result);
        
        // not found        
        when(database.getDisc(barcode)).thenReturn(Optional.empty());
        result = service.updateDisc(update);
        assertEquals(MediaServiceResult.NOT_FOUND,result);
    }
    
    @Test
    public void testGetBook() {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
         
        // return book
        when(database.getBook(isbn)).thenReturn(Optional.of(hobbit));
        Optional<Medium> book = service.getBook(isbn);
        assertEquals(hobbit,book.get());
        
        // return empty
        when(database.getBook(isbn)).thenReturn(Optional.empty());
        book = service.getBook(isbn);
        assertEquals(Optional.empty(),book);
    }
    
    @Test
    public void testGetDisc() {
        String barcode = "000000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
         
        // return book
        when(database.getDisc(barcode)).thenReturn(Optional.of(twoTowers));
        Optional<Medium> disc = service.getDisc(barcode);
        assertEquals(twoTowers,disc.get());
        
        // return empty
        when(database.getDisc(barcode)).thenReturn(Optional.empty());
        disc = service.getDisc(barcode);
        assertEquals(Optional.empty(),disc);
    }
    
    @Test
    public void testGetBooks() {
        String isbn = "978-0-306-40615-7";
        Book hobbit = new Book("The Hobbit","JRR Tolkin",isbn);
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
        
        ArrayList<Medium> list = new ArrayList<>();
        list.add(hobbit);
        list.add(hobbit);
        // return book
        when(database.getBooks()).thenReturn(Optional.of(list));
        
        // return list
        Medium[] result = service.getBooks();
        assertArrayEquals(list.toArray(), result);
        
        // return empty
        ArrayList<Medium> empty = new ArrayList<>();
        when(database.getBooks()).thenReturn(Optional.of(empty));
        result = service.getBooks();
        assertArrayEquals(empty.toArray(),result);
    }
    
    @Test
    public void testGetDiscs() {
        String barcode = "000000000000";
        Disc twoTowers = new Disc(barcode,"Peter Jackson",12,"The Two Towers");
        
        database = mock(MediaPersistenceImpl.class);
        MediaServiceImpl service = new MediaServiceImpl(database);
        
        ArrayList<Medium> list = new ArrayList<>();
        list.add(twoTowers);
        list.add(twoTowers);
        // return book
        when(database.getDiscs()).thenReturn(Optional.of(list));
        
        // return list
        Medium[] result = service.getDiscs();
        assertArrayEquals(list.toArray(),result);
        
        // return empty
        ArrayList<Medium> empty = new ArrayList<>();
        when(database.getDiscs()).thenReturn(Optional.of(empty));
        result = service.getDiscs();
        assertArrayEquals(empty.toArray(),result);
    }
    
    

}
