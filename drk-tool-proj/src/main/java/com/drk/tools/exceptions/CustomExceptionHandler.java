package com.drk.tools.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.drk.tools.constants.ErrorConstants;
import org.springframework.web.util.NestedServletException;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger EXCEPTION_LOGGER = LoggerFactory.getLogger(CustomExceptionHandler.class);

	@ExceptionHandler(JsonEOFException.class)
	public ResponseEntity<Object> handleJsonMappingException(Exception ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("error", "DRGERR-008");
		responseBody.put("message", "Invalid json format");
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(JsonParseException.class)
	public ResponseEntity<Object> handleJsonParseException(Exception ex) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("error", "DRGERR-008");
		responseBody.put("message", "Invalid json format");
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomAccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(CustomAccessDeniedException ex, WebRequest request) {
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		ErrorResponse error = new ErrorResponse("Validation Failed", details);
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidApiKeyException.class)
	public final ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidAppKeyException.class)
	public final ResponseEntity<Object> handleInvalidAppKeyException(InvalidAppKeyException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(FileUploadFailedException.class)
	public final ResponseEntity<Object> handleInvalidTypeException(FileUploadFailedException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.FILE_UPLOAD_FAILED);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(DbException.class)
	public final ResponseEntity<Object> handleDbException(DbException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DB_ERROR);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(DirectoryNotFoundException.class)
	public final ResponseEntity<Object> handleDirectoryNotFoundException(DirectoryNotFoundException ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DIRECTORY_NOT_FOUND);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(SyncNotFound.class)
	public final ResponseEntity<Object> handleSyncNotFoundException(SyncNotFound ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.SYNC_NOT_FOUND);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ex.getParameterName());
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}


//	@ExceptionHandler(SyncNotFound.class)
//	protected ResponseEntity<Object> handleMissingRequestHeaderException(Exception ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//		Map<String, Object> responseBody = new HashMap<>();
//		responseBody.put("success", false);
//		responseBody.put("errorCode", ex.getCause());
//		responseBody.put("error", ex.getMessage());
//		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
//		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
//	}

	@ExceptionHandler(DataUploadFailedExcpetion.class)
	public final ResponseEntity<Object> handleDataUploadFailedExcpetion(DataUploadFailedExcpetion ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DATA_UPLOAD_FAILED);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidTypeException.class)
	public final ResponseEntity<Object> handleInvalidTypeException(InvalidTypeException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_TYPE);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidDataException.class)
	public final ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_DATA);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(ResponseNotFoundException.class)
	public final ResponseEntity<Object> handleResponseNotFoundException(ResponseNotFoundException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.RIGHT_ADD_UPDATE);
		responseBody.put("error", ex.getMessage());
		EXCEPTION_LOGGER.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	public static String getPath(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getRequestURL().toString();
	}
}
