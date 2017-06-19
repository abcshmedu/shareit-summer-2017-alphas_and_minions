package edu.hm.shareit.resource;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

import edu.hm.shareit.auth.AuthInterface;
import edu.hm.shareit.auth.HerokuAuth;
import edu.hm.shareit.auth.JettyAuth;
import edu.hm.shareit.persistence.MediaPersistence;
import edu.hm.shareit.persistence.MediaPersistenceImpl;
import edu.hm.shareit.service.MediaService;
import edu.hm.shareit.service.MediaServiceImpl;

/**
 * Context Listener to enable usage of google guice together with jersey.
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 *
 */
public class ShareitServletContextListener extends GuiceServletContextListener {

    private static final Injector injector = Guice.createInjector(new ServletModule() {
        @Override
        protected void configureServlets() {
            bind(MediaService.class).to(MediaServiceImpl.class);
            bind(SessionFactory.class).toInstance(new Configuration().configure().buildSessionFactory());
            bind(MediaPersistence.class).to(MediaPersistenceImpl.class);
            //bind(AuthInterface.class).to(HerokuAuth.class);
            bind(AuthInterface.class).to(JettyAuth.class); // for local testing
        }
    });

    @Override
    protected Injector getInjector() {
        return injector;
    }

    /**
     * This method is only required for the HK2-Guice-Bridge in the Application class.
     * @return Injector instance.
     */
    static Injector getInjectorInstance() {
        return injector;
    }

}
