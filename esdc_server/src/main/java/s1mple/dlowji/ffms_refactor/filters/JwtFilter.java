package s1mple.dlowji.ffms_refactor.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import s1mple.dlowji.ffms_refactor.helper.JwtHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
	private final ObjectMapper mapper;
	@Autowired
	private JwtHelper jwtHelper;


	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		String token = getToken(request);
		Map<String, Object> errorDetails = new HashMap<>();
		if (token == null) {
			errorDetails.put("message", "Invalid token");
			errorDetails.put("status", HttpStatus.FORBIDDEN.value());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			mapper.writeValue(response.getWriter(), errorDetails);
			return;
		}

		if (!jwtHelper.validateToken(token)) {
			errorDetails.put("message", "Expired token");
			errorDetails.put("status", HttpStatus.FORBIDDEN.value());
			response.setStatus(HttpStatus.FORBIDDEN.value());
			mapper.writeValue(response.getWriter(), errorDetails);
			return;
		}

		String username = jwtHelper.getUsernameFromToken(token);
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken authenticationToken =
		new UsernamePasswordAuthenticationToken(userDetails, null,
		userDetails.getAuthorities());
		authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		filterChain.doFilter(request, response);
	}

	private String getToken(HttpServletRequest request) {
		try {
			String token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (token == null) return null;
			token = token.substring(7);
			return token;
		} catch (IndexOutOfBoundsException e) {

			return e.getMessage();
		}
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

		boolean isNotFilltered = request.getRequestURI().startsWith("/auth/login")
		|| request.getRequestURI().startsWith("/auth/register")
		|| request.getRequestURI().startsWith("/api/football_fields") && request.getMethod().equals("GET")
		|| request.getRequestURI().startsWith("/api/services") && request.getMethod().equals("GET");
		return isNotFilltered;
	}
}

