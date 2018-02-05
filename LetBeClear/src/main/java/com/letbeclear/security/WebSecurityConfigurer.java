package com.letbeclear.security;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;

import com.letbeclear.security.handler.JwtAuthenticationFailureHandler;
import com.letbeclear.security.handler.JwtAuthenticationSuccessHandler;
import com.letbeclear.security.handler.JwtLogoutHandler;
import com.letbeclear.security.handler.JwtLogoutSuccessHandler;
import com.letbeclear.service.UserDetailsSeviceImpl;
import com.letbeclear.token.config.JwtConfig;

@Configuration
@EnableWebSecurity	//These two will disable all the default web security configurations
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter
{
	//already all JwtConfigurer is embedded in it
	
	@Autowired
	JwtLogoutSuccessHandler jwtLogoutSuccessHandler;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	JwtAuthenticationProvider authenticationProvider;

	@Autowired
	private UserDetailsSeviceImpl userDetailsServiceImpl;

//	@Autowired
//	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	
	public JwtUsernamePasswordAuthenticationFilter jwtUsernamePasswordAuthenticationFilter()
	{
		JwtUsernamePasswordAuthenticationFilter userPwdFilter=null;
		try
		{
			System.out.println("Inside WebSecurityConfig jwtUsernamePasswordAuthenticationFilter() ");
			userPwdFilter=new JwtUsernamePasswordAuthenticationFilter(authenticationManager() );
		
			//userPwdFilter.setFilterProcessesUrl("/login");
			
			//This can also be invoked if we want to set method of the url we want to invoke
			userPwdFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
			
			userPwdFilter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
			userPwdFilter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
		}
		catch(Exception e)
		{
			System.out.println("Exception in WebSecurityConfig jwtUsernamePasswordAuthenticationFilter()");
			e.printStackTrace();
		}
		return userPwdFilter;
	}
	
//	@Bean AuthenticationManager loginAuthenticationManager()
//	{
//		return new ProviderManager();
//	}
	
	@Bean
	public JwtAutenticationTokenFilter jwtAutenticationTokenFilter()
	{
		System.out.println("Inside WebSecurityConfigurer jwtAuthenticationTokenFilter() initialized");
		JwtAutenticationTokenFilter filter=new JwtAutenticationTokenFilter();
		
		//filter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/rest/**"));
		
		filter.setAuthenticationManager(authenticationManagerToken());
		
		filter.setAuthenticationSuccessHandler(new JwtAuthenticationSuccessHandler());
		//filter.setAuthenticationFailureHandler(new JwtAuthenticationFailureHandler());
		//filter.setSessionAuthenticationStrategy(concurrentSessionControlAuthenticationStrategy());
		
		return filter;
	}
	
	//Bean for AuthenticationManager for Authorization of JWT
	@Bean
	public AuthenticationManager authenticationManagerToken()
	{
		System.out.println("Inside WebSecurityConfigurer authenticationManager() initialized");
		return new ProviderManager(Collections.singletonList(authenticationProvider));
	}
	
	//Bean for ConcurrentSessionControlAuthenticationStrategy
//	@Bean
//	ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy()
//	{
//		System.out.println("Inside WebSecurityConfigurer concurrentSessionControlAuthenticationStrategy() initialized");
//		ConcurrentSessionControlAuthenticationStrategy css=new ConcurrentSessionControlAuthenticationStrategy(sessionRegistryImpl());
//		css.setMaximumSessions(1);
//		return css;
//	}
	
	//Bean for SessionRegistry
//	@Bean
//	SessionRegistryImpl sessionRegistryImpl()
//	{
//		System.out.println("Inside WebSecurityConfigurer sessionRegistryImpl() initialized");
//		return new SessionRegistryImpl();
//	}
	
	//Bean for Concurrent Session Control
//	@Bean
//	public HttpSessionEventPublisher httpSessionEventPublisher() 
//	{
//	    return new HttpSessionEventPublisher();
//	}
	
	//Bean for JwtTokenRefreshingFilter
//	@Bean
//	public JwtTokenRefreshingFilter jwtTokenRefreshingFilter()
//	{
//		JwtTokenRefreshingFilter refreshingFilter=new JwtTokenRefreshingFilter();
//		//refreshingFilter.addUrlPatterns("/rest/**");
//		
//		return refreshingFilter;
//	}
	
	//Bean for LogoutFilter
//	@Bean
//	LogoutFilter logoutFilter()
//	{
//		LogoutFilter logoutFilter= new LogoutFilter(jwtLogoutSuccessHandler(), jwtLogoutHandler());
//		
//		return logoutFilter;
//	}
	
	//Bean for LogoutSuccessHandler
//	JwtLogoutSuccessHandler jwtLogoutSuccessHandler()
//	{
//		JwtLogoutSuccessHandler logoutHandler=new JwtLogoutSuccessHandler();
//		return logoutHandler;
//	}
	
	//Bean for LogoutHandler
//	JwtLogoutHandler jwtLogoutHandler()
//	{
//		JwtLogoutHandler logoutHandler=new JwtLogoutHandler();
//		return logoutHandler;
//	}
	
	@Override
	protected void configure(HttpSecurity http)
	{
		System.out.println("Inside WebSecurityConfigurer configure(HttpSecurity http) method");
		try
		{
			http
				.authorizeRequests()
				 	.antMatchers("/").permitAll()
				 	.antMatchers("/test").permitAll()
				 	.antMatchers("/token").permitAll()
				 	.antMatchers("/register", "/login", "/loginFailed", "/comment/getComment").permitAll()
				 	.antMatchers("/forgotPassword").permitAll()
				 	.antMatchers("/admin/**").hasAuthority("ADMIN").anyRequest()
					.authenticated()
					.antMatchers("**/rest/**").authenticated()
				.and()
					.csrf().disable()
					//.logout()
					//.logoutUrl("/rest/logout")
					//.invalidateHttpSession(true)
					//.logoutSuccessHandler(jwtLogoutSuccessHandler).permitAll()
					//.deleteCookies("JSESSIONID")
				//.and()
					.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.and()
					.sessionManagement()
					.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//					.maximumSessions(2)
//					.expiredUrl("/sessionExpired.html")
//					.maxSessionsPreventsLogin(true);
			
			//http.addFilterBefore(jwtAutenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
			
			http.addFilterBefore(jwtAutenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
			
			http.addFilter(jwtUsernamePasswordAuthenticationFilter());
			
			//http.addFilterAfter(jwtTokenRefreshingFilter(), JwtAutenticationTokenFilter.class);
			
		//	http.addFilterAt(logoutFilter(), LogoutFilter.class);
			
			//headers() expalnation
			/*Adds the Security headers to the response. This is activated by default when using
		  		{@link WebSecurityConfigurerAdapter}'s default constructor*/
			
			http.headers().cacheControl();
			
		}
		catch(Exception e)
		{
			System.out.println("Exception in WebSecurityConfigurer configure(HttpSecurity http) ");
			e.printStackTrace();
		}
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	{
		//This method is used to provide UserDetailsService to the Spring framework
		//the class whose loadUserByUsername() will be executed for verifying the user
		//for the /login request
		
		/**
		 * Allows providing a parent {@link AuthenticationManager} that will be tried if this
		 * {@link AuthenticationManager} was unable to attempt to authenticate the provided
		 * {@link Authentication}.
		 *
		 * @param authenticationManager the {@link AuthenticationManager} that should be used
		 * if the current {@link AuthenticationManager} was unable to attempt to authenticate
		 * the provided {@link Authentication}.
		 * @return the {@link AuthenticationManagerBuilder} for further adding types of
		 * authentication
		 */
		/*public AuthenticationManagerBuilder parentAuthenticationManager(
				AuthenticationManager authenticationManager) {
			if (authenticationManager instanceof ProviderManager) {
				eraseCredentials(((ProviderManager) authenticationManager)
						.isEraseCredentialsAfterAuthentication());
			}
			this.parentAuthenticationManager = authenticationManager;
			return this;
		}*/
		
		
								// MY CODE STARTS FROM HERE //
		
		try
		{
			System.out.println("Inside WebSecurityConfigurer configure(AuthenticationManagerBuilder auth)");
			
			auth.userDetailsService(userDetailsServiceImpl);
				//.passwordEncoder(bcryptPasswordEncoder);
		} 
		catch (Exception e) 
		{
			System.out.println("Exception in WebSecurityConfigurer configure(AuthenticationManagerBuilder auth)");
			e.printStackTrace();
		}
	}
}
