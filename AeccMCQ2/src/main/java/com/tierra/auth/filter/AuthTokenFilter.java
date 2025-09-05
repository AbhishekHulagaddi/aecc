package com.tierra.auth.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tierra.accesspolicy.model.PermissionAccessModel;
import com.tierra.accesspolicy.model.PermissionModel;
import com.tierra.accesspolicy.service.PermissionService;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.JwtUtils;
import com.tierra.auth.utils.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthTokenFilter extends OncePerRequestFilter {
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private ExcludeUrls excludeUrls;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private PermissionService permissionService;

	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String requestUrl = request.getRequestURI().toString();
//		System.out.println(requestUrl);


		// Define a list of URLs for which token validation is not required
		List<String> excludedUrls = Arrays.asList("/Auth/User/signup", "/configuration/ui", "/swagger-resources/**",
				"/configuration/security", "/swagger-ui/index.html", "/swagger-ui.html", "/swagger-ui/**",
				"/v3/api-docs/**", "/v3/api-docs/swagger-config", "/swagger-resources/**", "/swagger-ui/swagger-ui.css",
				"/swagger-ui/index.css", "/swagger-ui/swagger-ui-bundle.js", "/swagger-ui/swagger-initializer.js",
				"/v3/api-docs", "/swagger-ui/swagger-ui-standalone-preset.js", "/swagger-ui/favicon-32x32.png",
				"/swagger-ui/favicon-16x16.png", "/Auth/User/signin", "/Auth/User/sendOtp","/Auth/User/Create","/Auth/User/ForgotPassword",
				"/Auth/Roles/GetAll","/Message/Message/Create","/Question/Question/AddWeeklyTest",
	              "/", "/index.html", "**.css", "**.js", "**.html","/style.css",
	              "/register.js", "/script.js", "/spinner.css", "/style.css", "/userDashboard.html", "/chatbot.js", "/dashboard.js", "/dashboard_style.css", "/dropdown.css",
	              "/forgotpassword.html", "/forgotpassword.js", "/main.js", "/onlinetest.css", "/onlinetest.js", "/package-lock", "/profile.css", "/profile.js", "register.html",
	              "/images/logo.jpg","/images/chatbot.png","/image/bg.jpg","/onlineTest/mcq.js",
	              "/.well-known/appspecific/com.chrome.devtools.json","/onlineTest/mcq.html","/onlineTest/mcq.css"
	
		);
		

	    // Extensions for static files
	    List<String> excludedExtensions = Arrays.asList(".css", ".js", ".html", ".png", ".jpg", ".jpeg", ".gif", ".ico",".pdf",".mjs",".ftl", ".svg",".json");

	    // Prefixes (folders)
	    List<String> excludedPrefixes = Arrays.asList("/swagger-ui/", "/v3/api-docs", "/swagger-resources", "/images/","/notes",    "/pdfjs/web/**",        // ✅ allow viewer.html, viewer.js, viewer.css
	    	    "/pdfjs/build/**"  );

	    boolean skip = excludedUrls.contains(requestUrl)
	            || excludedExtensions.stream().anyMatch(requestUrl::endsWith)
	            || excludedPrefixes.stream().anyMatch(requestUrl::startsWith);

	    if (skip) {
	        filterChain.doFilter(request, response);
	        return;
	    }else {
			System.out.println(requestUrl);
	    }

//		// Check if the request URL is in the excluded URLs list
//		if (excludedUrls.contains(requestUrl)) {
//			filterChain.doFilter(request, response);
//			return;
//		}

		try {
			String jwt = parseJwt(request);
			if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
				String username = jwtUtils.getUsernameFromJwtToken(jwt);
				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				System.out.println(userDetails.getAuthorities() + " :roles");

				Set<String> userRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				String[] parts = requestUrl.split("/");
				String menu = parts[1]; 
				String submenu = parts[2];
				String feature = parts[3];

				System.out.println("menu :"+ menu);
				System.out.println("submenu :"+ submenu);
				System.out.println("feature :"+ feature);
				PermissionModel permission = new PermissionModel();
				boolean permitted = false;
				for (String role : userRoles) {

					PermissionAccessModel permissionAccessModel = new PermissionAccessModel();
					permissionAccessModel.setMenu(menu);
					permissionAccessModel.setSubMenu(submenu);
					permissionAccessModel.setFeature(feature);
					permissionAccessModel.setRole(role);
					permission = permissionService.getPermissionByMenuAndSubMenuAndFeature(permissionAccessModel);
					if ( permission != null && permission.isEnable() == true) {
						permitted=true;
					}
				}
//				if(permission==null&&(!excludeUrls.isPermitted(requestUrl))) {//exclude URLs which has universal access
//					throw new CusException("Permission is not been created for This Role and Api", HttpStatus.UNAUTHORIZED);
//				}
//				if ( permission != null && permitted == false ) {
//					throw new CusException("Error: Unauthorized: User '" + username + "' doesn't have access to "+ permission.getPermissionName(), HttpStatus.UNAUTHORIZED);
//
//				}

			} else {
				
				throw new CusException("Error: Unauthorized: jwtToken Cannot be Null ", HttpStatus.UNAUTHORIZED);
				
			}

		} catch (Exception e) {
			logger.error("Cannot set user authentication: {}", e);
			throw e;
		}
		filterChain.doFilter(request, response);
		return;
	}

	private String parseJwt(HttpServletRequest request) {
		String jwt = jwtUtils.extractJwtFromRequest(request);
		return jwt;
	}
}