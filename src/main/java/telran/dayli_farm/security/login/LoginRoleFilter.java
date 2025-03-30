package telran.dayli_farm.security.login;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginRoleFilter extends OncePerRequestFilter {

	 @Override
	    protected void doFilterInternal(HttpServletRequest request,
	                                    HttpServletResponse response,
	                                    FilterChain filterChain)
	            throws ServletException, IOException {

	        String uri = request.getRequestURI();

	        if (uri.startsWith("/customer/login")) {
	            LoginRoleContext.setRole("CUSTOMER");
	        } else if (uri.startsWith("/farmer/login")) {
	            LoginRoleContext.setRole("FARMER");
	        }

	        try {
	            filterChain.doFilter(request, response);
	        } finally {
	            LoginRoleContext.clear(); 
	        }
	    }
	}
