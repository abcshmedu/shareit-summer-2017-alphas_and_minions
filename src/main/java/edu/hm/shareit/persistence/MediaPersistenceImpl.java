package edu.hm.shareit.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import edu.hm.shareit.model.Book;
import edu.hm.shareit.model.Disc;
import edu.hm.shareit.model.Medium;

public class MediaPersistenceImpl implements MediaPersistence {
    

    private static SessionFactory factory;
    
    public MediaPersistenceImpl() {
        try {
            setFactory(new Configuration().buildSessionFactory());
        } catch (Throwable e) {
            System.err.println("Failed to create sessionFactory object." + e);
            throw new ExceptionInInitializerError(e); 
        }
    }

    @Override
    public Optional<Medium> addMedium(Medium medium, String id) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer bookID = null;
        Optional<Medium> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            bookID = (Integer) session.save(id, medium); // using isbn as id -> TODO check if it works the way it should
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if (bookID != null)
            result = Optional.of(medium);
        
        return result;
    }
    

    @Override
    public Optional<List<Medium>> getBooks() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Medium> mediums = null;
        Optional<List<Medium>> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            mediums = session.createQuery("FROM MEDIUM").list(); // XXX database name were to define??
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if (mediums != null)
            result = Optional.of(mediums);
        
        return result;
    }
    
    @Override
    public Optional<List<Medium>> getDiscs() {
        Session session = factory.openSession();
        Transaction tx = null;
        List<Medium> mediums = null;
        Optional<List<Medium>> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            mediums = session.createQuery("FROM MEDIUM").list(); // XXX database name were to define??
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if (mediums != null)
            result = Optional.of(mediums);
        
        return result;
    }

    @Override
    public Optional<Medium> getBook(String isbn) {
        Session session = factory.openSession();
        Transaction tx = null;
        Medium book = null;
        Optional<Medium> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            book = session.get(Book.class, isbn);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if (book != null)
            result = Optional.of(book);
        
        return result;
    }


    @Override
    public Optional<Medium> getDisc(String barcode) {
        Session session = factory.openSession();
        Transaction tx = null;
        Medium disc = null;
        Optional<Medium> result = Optional.empty();
        
        try {
            tx = session.beginTransaction();
            disc = session.get(Disc.class, barcode);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        if (disc != null)
            result = Optional.of(disc);
        
        return result;
    }

    @Override
    public void remove(String id) {
        Session session = factory.openSession();
        Transaction tx = null;
        
        try {
            tx = session.beginTransaction();
            Medium medium = (Medium) session.get(Medium.class, id);
            session.delete(medium);
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /**
     * @return the factory
     */
    public static SessionFactory getFactory() {
        return factory;
    }

    /**
     * @param factory the factory to set
     */
    public static void setFactory(SessionFactory factory) {
        MediaPersistenceImpl.factory = factory;
    }

}
