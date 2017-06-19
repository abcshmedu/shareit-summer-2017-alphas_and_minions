package edu.hm.shareit.auth;

import java.io.IOException;

public interface AuthInterface {

    public String authenticate(final String token) throws IOException;
}
