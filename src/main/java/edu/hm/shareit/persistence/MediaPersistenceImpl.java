package edu.hm.shareit.persistence;

import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Medium;

/**
 * Concrete database using Hibernate.
 * 
 * @author Rebecca Brydon, Michael Eggers
 *
 */
public class MediaPersistenceImpl implements MediaPersistence {
    

    private static SessionFactory factory;
    
    @Inject
    public MediaPersistenceImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Medium> addMedium(Medium medium) {
    	Session entityManager = factory.getCurrentSession();
        Transaction tx = null;
        Medium m = null;
        Optional<Medium> result = Optional.empty();
        
        try {
            tx = entityManager.beginTransaction();
            String tmp = (String) entityManager.save(medium); // debug
            result = Optional.of(medium);
            System.out.println("added new Medium: " + result.get()); // debug
            tx.commit();
            
            
        } catch (HibernateException e) {
            if (tx != null) {
            	tx.rollback();
            }
            e.printStackTrace();
        }
        
        return result;
    }
    

    @Override
    public Optional<List<Medium>> getBooks() {
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        List<Medium> mediums = null;
        Optional<List<Medium>> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            mediums = session.createQuery("FROM Book").getResultList();
            System.out.println(mediums); // debug
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        
        if (mediums != null) {
        	result = Optional.of(mediums);        	
        }
        
        return result;
    }
    
    @Override
    public Optional<List<Medium>> getDiscs() {
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        List<Medium> mediums = null;
        Optional<List<Medium>> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            mediums = session.createQuery("FROM Disc").getResultList();
            System.out.println(mediums); // debug
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
            	tx.rollback();
            }
            e.printStackTrace();
        }
        
        if (mediums != null) {
        	result = Optional.of(mediums);        	
        }
        
        return result;
    }

    @Override
    public Optional<Medium> getBook(String isbn) {
        Session entityManager = factory.getCurrentSession();
        Transaction tx = null;
        Medium book = null;
        Optional<Medium> result = Optional.empty();
        
        try {
            tx = entityManager.beginTransaction();
            book = entityManager.get(Book.class, isbn);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
            	tx.rollback();
            }
            e.printStackTrace();
        }
        
        if (book != null) {
        	result = Optional.of(book);        	
        }
        
        return result;
    }


    @Override
    public Optional<Medium> getDisc(String barcode) {
        Session entityManager = factory.getCurrentSession();
        Transaction tx = null;
        Medium disc = null;
        Optional<Medium> result = Optional.empty();
        
        try {
            tx = entityManager.beginTransaction();
            disc = entityManager.get(Medium.class, barcode);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
        
        if (disc != null) {
        	result = Optional.of(disc);        	
        }
        
        return result;
    }

    @Override
    public void remove(String id) {
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Medium medium = (Medium) session.get(Medium.class, id);
            session.delete(medium);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) {
            	tx.rollback();
            }
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(final Medium medium) {
    	remove(medium.getID());
    	addMedium(medium);
    }


}
