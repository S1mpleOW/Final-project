package s1mple.dlowji.ffms_refactor.exceptions;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedException implements AccessDeniedHandler {
	private final ObjectMapper mapper;
	@Override
	public void handle(HttpServletRequest req, HttpServletResponse res,
										 AccessDeniedException accessDeniedException) throws IOException, ServletException {
		System.out.println("access denied exception");
		accessDeniedException.printStackTrace();
		Map<String, Object> errorDetails = new HashMap<>();
		res.setContentType(MediaType.APPLICATION_JSON_VALUE);
		errorDetails.put("message", "Access denied");
		errorDetails.put("details", accessDeniedException.getMessage());
		errorDetails.put("status", HttpStatus.FORBIDDEN.value());
		res.setStatus(403);
		mapper.writeValue(res.getWriter(), errorDetails);
	}
}
