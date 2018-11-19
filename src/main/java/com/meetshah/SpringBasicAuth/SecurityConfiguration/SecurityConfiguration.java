package com.meetshah.SpringBasicAuth.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthProvider authProvider;
	@Autowired
	private AuthEntryPoint authEntryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.authorizeRequests()
				.antMatchers("/api/application").permitAll()
				.antMatchers("/api/**").access("principal.authenticated")
				.and().httpBasic()
				.authenticationEntryPoint(authEntryPoint)
				.and().csrf().disable();

	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// Ensure the in-memory authentication provider is fully configured before the custom provider
		inMemoryConfigurer()
				.withUser("user")
				.password("password")
				.roles("USER").and()
				.configure(auth);

		auth.authenticationProvider(authProvider);
	}

	private InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> inMemoryConfigurer() {
		return new InMemoryUserDetailsManagerConfigurer<>();
	}
}
