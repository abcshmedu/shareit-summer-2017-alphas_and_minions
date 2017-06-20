package edu.hm.shareit.auth;

import java.io.IOException;
/**
 * Interface for DI.
 * 
 * @author Rebecca Brydon, Michael Eggers
 *
 */
public interface AuthInterface {
	
	/**
	 * Connect to auth-microservice and get Status.
	 * 
	 * @param token Token provided by user.
	 * @return eg "200" for OK
	 * @throws IOException if something with the httpURLconnection went wrong.
	 */
     String authenticate(final String token) throws IOException;
}
