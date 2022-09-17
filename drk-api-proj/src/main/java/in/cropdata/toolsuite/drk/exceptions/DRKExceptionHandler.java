package in.cropdata.toolsuite.drk.exceptions;

import java.util.HashMap;
import java.util.Map;

import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.DirectoryCreationFailedException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.ImageUploadFailedException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.PathKeyCheckFailedException;

@SuppressWarnings({ "unchecked", "rawtypes" })
@ControllerAdvice
public class DRKExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(DRKExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
		responseBody.put("success", false);
		responseBody.put("error", ex.getParameterName() + " parameter is missing");
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
		responseBody.put("success", false);
		responseBody.put("error", "Internal Server Error");
		responseBody.put("message", "Internal Server Error");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(CustomAccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(CustomAccessDeniedException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
		responseBody.put("success", false);
		responseBody.put("error", ex.getMessage());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.FORBIDDEN);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
		responseBody.put("success", false);
		responseBody.put("error", "Validation Failed: " + ex.getMessage());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity(responseBody, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(InvalidApiKeyException.class)
	public final ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("error", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public final ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
			MethodArgumentTypeMismatchException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.TYPE_MISSMATCH);
		responseBody.put("error", "The value for required parameter must be Integer.");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(InvalidDataException.class)
	public final ResponseEntity<Object> handleInvalidDataException(InvalidDataException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_DATA);
		responseBody.put("error", ex.getMessage());
		logger.error("InvalidDataException :: " + ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

	@ExceptionHandler(DirectoryCreationFailedException.class)
	public final ResponseEntity<Object> handleDirectoryCreationFailedException(DirectoryCreationFailedException ex,
			WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DIRECTORY_CREATION_FAILED);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(ImageUploadFailedException.class)
	public final ResponseEntity<Object> handleImageUploadFailedException(ImageUploadFailedException ex,
			WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DIRECTORY_CREATION_FAILED);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(PathKeyCheckFailedException.class)
	public final ResponseEntity<Object> handlePathKeyCheckFailedException(PathKeyCheckFailedException ex,
			WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DIRECTORY_CREATION_FAILED);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler(NoRecordFoundException.class)
	public final ResponseEntity<Object> handleNoRecordFoundException(NoRecordFoundException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.NO_RECORD_FOUND);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(DataInsertionException.class)
	public final ResponseEntity<Object> handleDataInsertionException(DataInsertionException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INSERT_ERROR);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.EXPECTATION_FAILED);
	}
//	@ExceptionHandler(SizeMisMatchException.class)
//	public final ResponseEntity<Object> sizeMisMatchException(SizeMisMatchException ex, WebRequest request) {
//
//		Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//		responseBody.put("success", false);
//		responseBody.put("errorCode", ErrorConstants.SIZE_MIS_MATCH);
//		responseBody.put("error", ex.getMessage());
//		logger.error(ex.getMessage(), ex);
//		return new ResponseEntity<Object>(responseBody, HttpStatus.FAILED_DEPENDENCY);
//	}

	@ExceptionHandler(NullPointerException.class)
	public final ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INSERT_ERROR);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.EXPECTATION_FAILED);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public final ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex,
			WebRequest request) {

		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_DATA);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.EXPECTATION_FAILED);
	}

	public static String getPath(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getRequestURL().toString();
	}

	@ExceptionHandler(DataNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(DataNotFoundException ex) {
		Map<String, Object> responseBody = new HashMap();
//		responseBody.put("path", getPath(request));
		responseBody.put("errorCode", ErrorConstants.INVALID_DATA);
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		Map<String, Object> responseBody = new HashMap();
		responseBody.put("errorCode", ErrorConstants.INVALID_DATA);
		responseBody.put("error", ex.getMessage());
		responseBody.put("success", false);
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}

	@ExceptionHandler(VersionControlException.class)
	public final ResponseEntity<Object> handleVersionControlException(VersionControlException ex, WebRequest request) {
		Map<String, Object> responseBody = new HashMap();
//	responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", "APKVER-ERR-002");
		responseBody.put("error", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_ACCEPTABLE);
	}

}