package com.growth.startupone.api.v1.exception;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
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
		
		ex.printStackTrace();
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		if (ex instanceof MethodArgumentTypeMismatchException) {
			return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
		}
		
		return super.handleTypeMismatch(ex, headers, status, request);
	}
	
	private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.INVALID_PARAMETER;
		
		String details = String.format("The URL parameter '%s' receipt the value '%s', which is an invalid type (%s). Please send a value compatible with type %s.", 
				ex.getName(), ex.getValue(), ex.getValue().getClass().getSimpleName(), ex.getRequiredType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		Throwable rootCause = ExceptionUtils.getRootCause(ex);
		
		if (rootCause instanceof PropertyBindingException) {
			return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
		} else if (rootCause instanceof InvalidFormatException) {
			return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
		}
		
		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String details = "Payload request not readable. Check the syntax and try again.";
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return  handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		
		String details = String.format("The property '%s' receipt the value '%s', which is an invalid type (%s). Please send a value compatible with type '%s'.",
				path, ex.getValue(), ex.getValue().getClass().getSimpleName(), ex.getTargetType().getSimpleName());
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String path = joinPath(ex.getPath());
		
		ProblemType problemType = ProblemType.INCOMPREHENSIBLE_MESSAGE;
		String details = String.format("The informed property '%s' does not exist, change the property name or remove it from payload and try again.", path);
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		
		ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
		
		String details = String.format("The requested resource URI '%s' does not exist.", ex.getRequestURL());
		
		Problem problem = createProblemBuilder(status, problemType, details).build();
		
		return handleExceptionInternal(ex, problem, headers, status, request);
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
	
	private String joinPath(List<Reference> references) {
		return references.stream()
			.map(ref -> ref.getFieldName())
			.collect(Collectors.joining("."));
	}

}
