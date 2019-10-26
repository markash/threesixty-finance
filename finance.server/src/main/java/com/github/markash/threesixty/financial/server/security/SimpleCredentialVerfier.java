package com.github.markash.threesixty.financial.server.security;

import java.io.IOException;

import org.eclipse.scout.rt.platform.security.ICredentialVerifier;

public class SimpleCredentialVerfier implements ICredentialVerifier {

    /**
     * Attempts to verify the given credentials.
     *
     * @param username
     *          the user to verify
     * @param password
     *          the password to verify
     * @return Result of the verification; one of {@link #AUTH_OK}, {@link #AUTH_FORBIDDEN},
     *         {@link #AUTH_CREDENTIALS_REQUIRED}, {@link #AUTH_FAILED}
     * @throws IOException
     */
    @Override
    public int verify(
            final String username, 
            final char[] password) throws IOException {
        
        return (username.equals("admin") && new String(password).equals("password1*")) ? AUTH_OK : AUTH_FAILED;
    }
}
