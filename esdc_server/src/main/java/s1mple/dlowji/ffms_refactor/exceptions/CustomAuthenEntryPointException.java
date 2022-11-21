package s1mple.dlowji.ffms_refactor.exceptions;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import s1mple.dlowji.ffms_refactor.dto.response.ResponseMessage;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CustomAuthenEntryPointException implements AuthenticationEntryPoint {
	private final ObjectMapper mapper;
	@Override
	public void commence(HttpServletRequest req, HttpServletResponse res,
											 AuthenticationException authException) throws IOException, ServletException {
		System.out.println("auth exception");
		authException.printStackTrace();
		Map<String, Object> errorDetails = new HashMap<>();
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		errorDetails.put("message", authException.getMessage());
		errorDetails.put("status", HttpStatus.FORBIDDEN.value());
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		res.setStatus(403);
		mapper.writeValue(res.getWriter(), errorDetails);
	}

}
