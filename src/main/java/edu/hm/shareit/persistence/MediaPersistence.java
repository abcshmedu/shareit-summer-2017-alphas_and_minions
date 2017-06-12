package edu.hm.shareit.persistence;

import java.util.List;
import java.util.Optional;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;

public interface MediaPersistence {
 
    public Optional<Medium> addMedium(Medium medium, String id);
    
    public Optional<List<Medium>> getBooks();
    
    public Optional<Medium> getBook(final String isbn);
    
    public Optional<List<Medium>> getDiscs();
    
    public Optional<Medium> getDisc(final String barcode);
    
    public void remove(String id);

    
    
    

}
