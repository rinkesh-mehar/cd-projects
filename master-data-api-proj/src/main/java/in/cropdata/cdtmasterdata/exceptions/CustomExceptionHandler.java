package in.cropdata.cdtmasterdata.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.cropdata.cdtmasterdata.constants.ErrorConstants;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static Logger exceptionLogger = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", "Server Error");
		responseBody.put("error", ex.getMessage() + "\t" + details);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomAccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(CustomAccessDeniedException ex, WebRequest request) {
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}

		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", "Validation Failed");
		responseBody.put("error", details);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.TYPE_MISSMATCH);
		responseBody.put("error", "The value for required parameter must be Integer.");
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(AlreadyExistException.class)
	public final ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.ALREADY_EXIST);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidDataException.class)
	public final ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.INVALID_DATA);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(DoesNotExistException.class)
	public final ResponseEntity<Object> handleDoesNotExistException(DoesNotExistException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.NOT_EXIST);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InactiveUserException.class)
	public final ResponseEntity<Object> handleInactiveUserException(InactiveUserException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.INACTIVE_USER);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(InvalidUserCredentialsException.class)
	public final ResponseEntity<Object> handleInvalidUserCredentialsException(InvalidUserCredentialsException ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.INVALID_USER);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}

	@ExceptionHandler(JwtTokenMissingException.class)
	public final ResponseEntity<Object> handleJwtTokenMissingException(JwtTokenMissingException ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.MISSING_TOKEN);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}

	@ExceptionHandler(JwtTokenMalformedException.class)
	public final ResponseEntity<Object> handleJwtTokenMalformedException(JwtTokenMalformedException ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.TOKEN_VALIDATE_FAILED);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}

	@ExceptionHandler(JwtTokenExpiredException.class)
	public final ResponseEntity<Object> handleJwtTokenExpiredException(JwtTokenExpiredException ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.TOKEN_EXPIRED);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.ACCESS_DENIED);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error("AccessDeniedException Vivek {}", ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(CryptoException.class)
	public final ResponseEntity<Object> handleCryptoException(CryptoException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.CRYPTO_FAILURE);
		responseBody.put("error", ex.getMessage());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Handle MissingServletRequestParameterException. Triggered when a 'required'
	 * request parameter is missing.
	 *
	 * @param ex      MissingServletRequestParameterException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is
	 * invalid as well.
	 *
	 * @param ex      HttpMediaTypeNotSupportedException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
		String error = builder.substring(0, builder.length() - 2);
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	/**
	 * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid
	 * validation.
	 *
	 * @param ex      the MethodArgumentNotValidException that is thrown when @Valid
	 *                validation fails
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//	ApiError apiError = new ApiError(BAD_REQUEST);
//	apiError.setMessage("Validation error");
//	apiError.addValidationErrors(ex.getBindingResult().getFieldErrors());
//	apiError.addValidationError(ex.getBindingResult().getGlobalErrors());
//	return buildResponseEntity(apiError);
//    }

	/**
	 * Handles javax.validation.ConstraintViolationException. Thrown when @Validated
	 * fails.
	 *
	 * @param ex the ConstraintViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(javax.validation.ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException ex) {
		String error = "Validation error";
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		responseBody.put("details", ex.getConstraintViolations());
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);

	}

//	    /**
//	     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
//	     *
//	     * @param ex the EntityNotFoundException
//	     * @return the ApiError object
//	     */
//	    @ExceptionHandler(EntityNotFoundException.class)
//	    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
//	        ApiError apiError = new ApiError(NOT_FOUND);
//	        apiError.setMessage(ex.getMessage());
//	        return buildResponseEntity(apiError);
//	    }

	/**
	 * Handle HttpMessageNotReadableException. Happens when request JSON is
	 * malformed.
	 *
	 * @param ex      HttpMessageNotReadableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) request;
		exceptionLogger.info("{} to {}", servletWebRequest.getHttpMethod(),
				servletWebRequest.getRequest().getServletPath());
		String error = "Malformed JSON request";
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle HttpMessageNotWritableException.
	 *
	 * @param ex      HttpMessageNotWritableException
	 * @param headers HttpHeaders
	 * @param status  HttpStatus
	 * @param request WebRequest
	 * @return the ApiError object
	 */
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = "Error writing JSON output";
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle NoHandlerFoundException.
	 *
	 * @param ex
	 * @param headers
	 * @param status
	 * @param request
	 * @return
	 */
	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		String error = "Invalid request URL";
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		responseBody.put("details",
				String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handle javax.persistence.EntityNotFoundException
	 */
	@ExceptionHandler(javax.persistence.EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(javax.persistence.EntityNotFoundException ex) {
		String error = "Entity Does Not Exist.";
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handle DataIntegrityViolationException, inspects the cause for different DB
	 * causes.
	 *
	 * @param ex the DataIntegrityViolationException
	 * @return the ApiError object
	 */
	@ExceptionHandler(DataIntegrityViolationException.class)
	protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException ex,
			WebRequest request) {
		String error = "Database error " + ex.getCause();
		Map<String, Object> responseBody = new HashMap<>();
		if (ex.getCause() instanceof ConstraintViolationException) {
			responseBody.put("errorCode", ErrorConstants.API_ERROR);
			responseBody.put("error", error);
			return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
		}
		responseBody.put("errorCode", ErrorConstants.API_ERROR);
		responseBody.put("error", error);
		exceptionLogger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Handle Exception, handle generic Exception.class
	 *
	 * @param ex the Exception
	 * @return the ApiError object
	 */
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, WebRequest request) {
//	ApiError apiError = new ApiError(BAD_REQUEST);
//	apiError.setMessage(String.format("The parameter '%s' of value '%s' could not be converted to type '%s'", ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName()));
//	apiError.setDebugMessage(ex.getMessage());
//	return buildResponseEntity(apiError);
//    }

	public static String getPath(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getRequestURL().toString();
	}
}