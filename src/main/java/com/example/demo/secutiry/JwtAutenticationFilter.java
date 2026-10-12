package com.example.demo.secutiry;

import java.io.IOException;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.demo.utils.AuthUser;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAutenticationFilter extends OncePerRequestFilter{

	private final JWTService jwtService;
	private final UserDetailsService userDetailsService;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.substring(7);
        try {
        	String email=jwtService.getEmail(token);
        	if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
        		UserDetails user=userDetailsService.loadUserByUsername(email);
        		if (jwtService.isValid(token, user)) {
        		    Claims c = jwtService.extractClaims(token);
        		    AuthUser authUser = new AuthUser(
        		            jwtService.getUserId(token),
        		            jwtService.getOrganizationId(token),
        		            jwtService.getEmail(token),
        		            jwtService.getRoleName(token));

        		    UsernamePasswordAuthenticationToken authentication =
        		            new UsernamePasswordAuthenticationToken(
        		                    authUser,                            
        		                    null,
        		                    List.of(new SimpleGrantedAuthority("ROLE_" + authUser.role())));

        		    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        		    SecurityContextHolder.getContext().setAuthentication(authentication);
        		}
        	}
        }
        catch (JwtException | IllegalArgumentException ex) {
            SecurityContextHolder.clearContext();
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    "Invalid or expired JWT");
            return;
        }

        filterChain.doFilter(request, response);
	}
}
