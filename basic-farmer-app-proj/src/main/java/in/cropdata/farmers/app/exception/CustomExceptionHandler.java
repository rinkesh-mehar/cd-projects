
package in.cropdata.farmers.app.exception;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.LockTimeoutException;
import javax.persistence.QueryTimeoutException;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.PessimisticLockException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.cropdata.farmers.app.constants.ErrorConstants;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.PARAM_MISSING);
		responseBody.put("message", " Missing Parameter " + ex.getParameterName());
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}


	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		List<String> details = new ArrayList<>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			details.add(error.getDefaultMessage());
		}
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_ARGS);
		responseBody.put("message", " Validation Failed: " + details);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}

	@ExceptionHandler(value = Exception.class)
	public final ResponseEntity<Object> handleAllExcpetions(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.SERVER_ERROR);
		responseBody.put("message", "Something went wrong please try again later!");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	
	@ExceptionHandler(value = DataIntegrityViolationException.class)
	public final ResponseEntity<Object> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.FARMER_ALREADY_REGISTERED);
		responseBody.put("message", "Farmer already registered !!");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.ALREADY_EXIST);
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	@ExceptionHandler(value = SQLIntegrityConstraintViolationException.class)
	public final ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(Exception ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.ALREADY_EXIST);
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
		
	}

	@ExceptionHandler(InvalidApiKeyException.class)
	public final ResponseEntity<Object> handleInvalidApiKeyException(InvalidApiKeyException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}

	@ExceptionHandler(InvalidAppKeyException.class)
	public final ResponseEntity<Object> handleInvalidAppKeyException(InvalidAppKeyException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}

	@ExceptionHandler(DoesNotExistException.class)
	public final ResponseEntity<Object> handleDoesNotExistException(DoesNotExistException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}

	@ExceptionHandler(MissingDeviceToken.class)
	public final ResponseEntity<Object> handleMissingDeviceTokenException(MissingDeviceToken ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}

	@ExceptionHandler({MissingRequestHeaderException.class})
	public ResponseEntity<Object> exception( MissingRequestHeaderException e, HttpServletResponse response) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.HEADER_MISSING);
		responseBody.put("message", " Missing Parameter " + e.getHeaderName());
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}
	
	@ExceptionHandler(InvalidUserCredentialsException.class )
	public ResponseEntity<Object> handleInvalidUserCredentialsException(InvalidUserCredentialsException e, HttpServletResponse response) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", e.getErrorCode());
		responseBody.put("message", e.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	public ResponseEntity<Object> handleUnauthorizedException(UnauthorizedException e, HttpServletResponse response) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode",e.getErrorCode());
		responseBody.put("message", e.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}
	
	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<Object> handleAlreadyExistException(AlreadyExistException e, HttpServletResponse response) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode",e.getErrorCode());
		responseBody.put("message", "Already exist");
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException e, HttpServletResponse response) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode",e.getErrorCode());
		responseBody.put("message", e.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}
	
	@ExceptionHandler(InactiveUserException.class)
	public ResponseEntity<Object> handleInactiveUserException(InactiveUserException e, HttpServletResponse response) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode",e.getErrorCode());
		responseBody.put("message", e.getMessage());
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
	}
	
	@ExceptionHandler(value = AuthTokenMissing.class)
	public final ResponseEntity<Object> handleAuthTokenMissingException(AuthTokenMissing ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	
	@ExceptionHandler(value = MyExceptionHandler.class)
	public final ResponseEntity<Object> handleMyExcpetionHandler(MyExceptionHandler ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", ex.getStatus());
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}

	@ExceptionHandler(value = CaseNotFoundException.class)
	public final ResponseEntity<Object> handleCaseNotFoundHandler(CaseNotFoundException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", ex.getStatus());
		responseBody.put("errorCode", ex.getErrorCode());
		responseBody.put("message", ex.getMessage());
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(responseBody, HttpStatus.OK);
	}
	
	// Lock and query time out exception handler  
	@ExceptionHandler(value = LockTimeoutException.class)
	public final ResponseEntity<Object> handleLockTimeoutException(LockTimeoutException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.SERVER_ERROR);
		responseBody.put("message", "Something went wrong please try again later!");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);

	}
	@ExceptionHandler(value = QueryTimeoutException.class)
	public final ResponseEntity<Object> handleQueryTimeoutException(QueryTimeoutException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.SERVER_ERROR);
		responseBody.put("message", "Something went wrong please try again later!");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
		
	}
	@ExceptionHandler(value = PessimisticLockException.class)
	public final ResponseEntity<Object> handlePessimisticLockException(PessimisticLockException ex, WebRequest request) {
		Map<String, Object> responseBody = new LinkedHashMap<>();
		responseBody.put("status", false);
		responseBody.put("errorCode", ErrorConstants.SERVER_ERROR);
		responseBody.put("message", "Something went wrong please try again later!");
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(responseBody, HttpStatus.OK);
		
	}
	

}
