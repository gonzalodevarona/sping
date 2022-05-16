package com.gonza.taller.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.gonza.taller.model.auth.Usermine;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private LoggingAccessDeniedHandler accessDeniedHandler;


	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity//.userDetailsService(myCustomUserDetailsService)
		.formLogin()
		.loginPage("/login").permitAll()
		.and().authorizeRequests()
//		.antMatchers("/api/**")
//		.permitAll()
		.antMatchers("/admin/**", "/product/**", "/location/**")
		.hasRole(Usermine.ADMIN)
		.antMatchers("/user/**", "/pch/**", "/pi/**")
		.hasRole(Usermine.USER)
		.anyRequest().authenticated().and()
		.httpBasic().and().logout().invalidateHttpSession(true)
		.clearAuthentication(true)
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login?logout").permitAll().and().exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler);
		
		
		
	}
}