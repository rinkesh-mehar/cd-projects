package in.cropdata.gstm.studio.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.cropdata.gstm.studio.constants.AppConstants;
import in.cropdata.gstm.studio.constants.ErrorConstants;

/**
 * Global Exception Handler class for catching exceptions raised in application.
 * 
 * @author PranaySK
 * @since 1.0
 */

@ControllerAdvice
public class GstmDataStudioExceptionHandler extends ResponseEntityExceptionHandler {

	private Logger log = LoggerFactory.getLogger(GstmDataStudioExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ErrorConstants.INVALID_DATA);
		responseBody.put(AppConstants.ERROR, ex.getParameterName() + " parameter is missing");
		responseBody.put(AppConstants.MESSAGE, ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		log.error("Returning HTTP 500 - Something went wrong!!!");
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ErrorConstants.INTERNAL_SERVER_ERROR);
		responseBody.put(AppConstants.ERROR, ex.getLocalizedMessage());
		responseBody.put(AppConstants.MESSAGE, ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Returning HTTP 400 - Bad Request!!!");
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ErrorConstants.TYPE_MISMATCH);
		responseBody.put(AppConstants.ERROR, ex.getLocalizedMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("Returning HTTP 400 - Bad Request!!!");
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ErrorConstants.TYPE_MISMATCH);
		responseBody.put(AppConstants.ERROR, ex.getLocalizedMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ErrorConstants.TYPE_MISMATCH);
		responseBody.put(AppConstants.ERROR, ex.getLocalizedMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidApiKeyException.class)
	public final ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ex.getErrorCode());
		responseBody.put(AppConstants.ERROR, ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidDataException.class)
	public final ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ex.getErrorCode());
		responseBody.put(AppConstants.ERROR, ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DbException.class)
	public final ResponseEntity<Object> handleDbException(DbException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ex.getErrorCode());
		responseBody.put(AppConstants.ERROR, ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DataNotFoundException.class)
	public final ResponseEntity<Object> handleDataNotFoundException(DataNotFoundException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put(AppConstants.SUCCESS, false);
		responseBody.put(AppConstants.ERROR_CODE, ex.getErrorCode());
		responseBody.put(AppConstants.ERROR, ex.getMessage());
		log.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.EXPECTATION_FAILED);
	}

	public static String getPath(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getRequestURL().toString();
	}

}
