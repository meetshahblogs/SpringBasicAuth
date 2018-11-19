package com.meetshah.SpringBasicAuth.SecurityConfiguration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.meetshah.SpringBasicAuth.Authentication.AuthenticationClient;
import com.meetshah.SpringBasicAuth.Authentication.models.AuthenticatedUser;
import com.meetshah.SpringBasicAuth.Authentication.models.AuthenticationInfo;

@Component
public class AuthProvider implements AuthenticationProvider {

	private AuthenticationClient authenticationClient;

	public AuthProvider(AuthenticationClient authenticationClient) {
		this.authenticationClient = authenticationClient;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();

		AuthenticationInfo authenticationInfo = authenticationClient.authenticateUser(username, password);

		AuthenticatedUser authenticatedUser =
				new AuthenticatedUser(username, password, authenticationInfo.isAuthenticated());
		return new UsernamePasswordAuthenticationToken(authenticatedUser, authentication.getCredentials().toString(),
				authenticatedUser.getAuthorities());
	}

	/*
	 * Returns true if this AuthProvider supports the UsernamePasswordAuthenticationToken object.
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
