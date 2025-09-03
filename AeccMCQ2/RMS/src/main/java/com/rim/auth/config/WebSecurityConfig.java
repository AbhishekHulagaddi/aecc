package com.rim.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rim.auth.filter.AuthTokenFilter;
import com.rim.auth.utils.UserDetailsServiceImpl;



@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

//    private final UserDetailsService userDetailsService;
//    private final AuthEntryPointJwt unauthorizedHandler;
//    
//    @Autowired
//    public WebSecurityConfig(UserDetailsService userDetailsService, AuthEntryPointJwt unauthorizedHandler) {
//        this.userDetailsService = userDetailsService;
//        this.unauthorizedHandler = unauthorizedHandler;
//    }
//
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }

	@Autowired
	UserDetailsServiceImpl userDetailsService;

//  @Autowired
	// private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	

//	@Bean
//	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//		http.csrf(csrf -> csrf.disable())
//				// .exceptionHandling(exception ->
//				// exception.authenticationEntryPoint(unauthorizedHandler))
//				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//				.authorizeHttpRequests(auth -> auth.requestMatchers("/api/auth/**").permitAll()
//						.requestMatchers( "/Auth/User/signin").permitAll()
//						.requestMatchers("/api/test/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
//								"/configuration/security", "/swagger-ui.html", "/swagger-ui.html", "/swagger-ui/**",
//								"/v3/api-docs/**", "/swagger-resources/**","/Auth/User/signin", "/Auth/User/Create", "/webjars/**")
//						.permitAll()
//						.anyRequest().authenticated()).cors();
//
//
//		http.headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()));
//
//		http.authenticationProvider(authenticationProvider());
//
//		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
//
//		return http.build();
//	}
//	
// 
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .cors() // Enable CORS here
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**","/api/auth/**", "/Auth/User/signin", "/api/test/**", "/v2/api-docs", "/configuration/ui", "/swagger-resources/**",
                        "/configuration/security", "/swagger-ui.html", "/swagger-ui.html", "/swagger-ui/**",
                        "/v3/api-docs/**", "/swagger-resources/**","/Auth/User/signin", "/Auth/User/Create", "/webjars/**").permitAll()
                .anyRequest().authenticated())
            .headers(headers -> headers.frameOptions(frameOption -> frameOption.sameOrigin()))
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
