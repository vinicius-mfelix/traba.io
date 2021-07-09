package com.growth.startupone.api.v1.exception;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.growth.startupone.domain.exception.EmailAlreadyInUseException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ProblemType problemType = ProblemType.INTERNAL_ERROR;
		String details = "An uncaught exception was thrown, try again later or contact a system admin.";
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(EmailAlreadyInUseException.class)
	public ResponseEntity<Object> handleEmailAlreadyInUse(EmailAlreadyInUseException ex, WebRequest request) {

		HttpStatus status = HttpStatus.CONFLICT;
		ProblemType problemType = ProblemType.VALIDATION_ERROR;
		String details = "E-mail informed is already in use, please enter another email address and try again.";
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
	}
	
	private ResponseEntity<Object> handleValidationInternal(Exception ex, HttpHeaders headers,
			HttpStatus status, WebRequest request, BindingResult bindingResult) {
		
		List<Problem.Field> fields;
		
		if ("Passwords must match!".equals(bindingResult.getGlobalError().getDefaultMessage())) {
			fields = Arrays.asList(Problem.Field.builder()
					.name("password")
					.message("Passwords must match!")
					.build());
		} else {
			fields = bindingResult.getFieldErrors().stream()
					.map(field -> {
						String message = messageSource.getMessage(field, LocaleContextHolder.getLocale());
						
						String name = field.getObjectName();
						
						if (field instanceof FieldError) {
							name = ((FieldError) field).getField();
						}
						
						return Problem.Field.builder()
							.name(name)
							.message(message)
							.build();
					}).collect(Collectors.toList());
		}
		
		ProblemType problemType = ProblemType.VALIDATION_ERROR;
		
		String details = "A problem were found during the validation. Check the payload fields and try again.";
		
		Problem problema = createProblemBuilder(status, problemType, details)
				.fields(fields)
				.build();
		
		return handleExceptionInternal(ex, problema, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if(body == null) {
			body = Problem.builder()
				.title(status.getReasonPhrase())
				.status(status.value())
				.timestamp(OffsetDateTime.now())
				.build();
		} else if (body instanceof String) {
			body = Problem.builder()
				.title((String) body)
				.status(status.value())
				.timestamp(OffsetDateTime.now())
				.build();
		}
		
		return super.handleExceptionInternal(ex, body, headers, status, request);
	}
	
	private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String details) {
		return Problem.builder()
				.status(status.value())
				.type(problemType.getUri())
				.title(problemType.getTitle())
				.timestamp(OffsetDateTime.now())
				.details(details);
	}

}
