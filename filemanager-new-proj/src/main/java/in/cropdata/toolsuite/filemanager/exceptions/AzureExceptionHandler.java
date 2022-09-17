/**
 * 
 */
package in.cropdata.toolsuite.filemanager.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.microsoft.azure.storage.table.TableServiceException;

import in.cropdata.toolsuite.filemanager.constant.ErrorConstants;
import in.cropdata.toolsuite.filemanager.model.ResponseMessage;

/**
 * @author cropdata-user
 *
 */
@ControllerAdvice
public class AzureExceptionHandler extends ResponseEntityExceptionHandler {

	// 400

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ResponseMessage apiError = new ResponseMessage(false, errors.toString(), ex.getLocalizedMessage(), "");
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		final ResponseMessage apiError = new ResponseMessage(false, errors.toString(), ex.getLocalizedMessage(), "");
		return handleExceptionInternal(ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
	}

	@Override
	protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex, final HttpHeaders headers,
			final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type "
				+ ex.getRequiredType();
		final ResponseMessage apiError = new ResponseMessage(false, error, ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = ex.getRequestPartName() + " part is missing";
		final ResponseMessage apiError = new ResponseMessage(false, error, ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(
			final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		final String error = ex.getParameterName() + " parameter is missing";
		final ResponseMessage apiError = new ResponseMessage(false, error, ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ MethodArgumentTypeMismatchException.class })
	public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();
		final ResponseMessage apiError = new ResponseMessage(false, error, ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final List<String> errors = new ArrayList<String>();
		for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}
		final ResponseMessage apiError = new ResponseMessage(false, errors.toString(), ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	// 404

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
		final ResponseMessage apiError = new ResponseMessage(false, error, ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(CustomAccessDeniedException.class)
	public ResponseEntity<Object> handleAccessDeniedException(CustomAccessDeniedException ex, WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
	}

	

	// 405

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getMethod());
		builder.append(" method is not supported for this request. Supported methods are ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
		final ResponseMessage apiError = new ResponseMessage(false, builder.toString(), ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.METHOD_NOT_ALLOWED);
	}

	// 415

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex,
			final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.info(ex.getClass().getName());
		//
		final StringBuilder builder = new StringBuilder();
		builder.append(ex.getContentType());
		builder.append(" media type is not supported. Supported media types are ");
		ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));

		final ResponseMessage apiError = new ResponseMessage(false, builder.substring(0, builder.length() - 2),
				ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler({ MissingRequestHeaderException.class })
	public ResponseEntity<ResponseMessage> handleMissingRequestHeaderException(final MissingRequestHeaderException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);

		final ResponseMessage apiError = new ResponseMessage(false, "Missing request header", ex.getLocalizedMessage(),
				"");
		return new ResponseEntity<ResponseMessage>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(final Exception ex, final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);

		final ResponseMessage apiError = new ResponseMessage(false, "SERVER_ERROR", ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ TableServiceException.class })
	public ResponseEntity<Object> handleTableServiceException(final TableServiceException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		final ResponseMessage apiError = new ResponseMessage(false, "exception occured", ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.FAILED_DEPENDENCY);
	}

	@ExceptionHandler({ IllegalArgumentException.class })
	public ResponseEntity<Object> handleIllegalArgumentException(final IllegalArgumentException ex,
			final WebRequest request) {
		logger.info(ex.getClass().getName());
		logger.error("error", ex);
		final ResponseMessage apiError = new ResponseMessage(false, "exception occured", ex.getLocalizedMessage(), "");
		return new ResponseEntity<Object>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DuplicatePathKeyException.class)
	    public final ResponseEntity<Object> handleDuplicatePathKeyException(DuplicatePathKeyException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
//		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DUPLICATE_PATH_KEY);
		responseBody.put("error", ex.getLocalizedMessage());
		responseBody.put("message", "the value for pathKey is already exist, please resend it with different value.");

		return new ResponseEntity<Object>(responseBody, HttpStatus.CONFLICT);
	    }
	    
	    @ExceptionHandler(PathKeyNotFoundException.class)
	    public final ResponseEntity<Object> handlePathKeyNotFoundException(PathKeyNotFoundException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
//		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.PATH_KEY_NOT_FOUND);
		responseBody.put("error", ex.getLocalizedMessage());
		responseBody.put("message", "provided pathKey is not exist, please resend it with different value.");

		return new ResponseEntity<Object>(responseBody, HttpStatus.ACCEPTED);
	    }
	    
	    @ExceptionHandler(FileUploadException.class)
	    public final ResponseEntity<Object> handleFileUploadException(FileUploadException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
//		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_REQ_PARAMETERS);
		responseBody.put("error", ex.getMessage());
		//responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");

		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	    }

	    @ExceptionHandler(ModuleNotFoundException.class)
	    public final ResponseEntity<Object> handleModuleNotFoundException(ModuleNotFoundException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
//		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.MODULE_NOT_FOUND);
		responseBody.put("error", ex.getMessage());

		return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
	    }
	    
	    @ExceptionHandler(DirectoryNotFoundException.class)
	    public final ResponseEntity<Object> handleDirectoryNotFoundException(DirectoryNotFoundException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
//		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.DIR_NOT_FOUND);
		responseBody.put("error", ex.getMessage());
//		responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");

		return new ResponseEntity<Object>(responseBody, HttpStatus.FAILED_DEPENDENCY);
	    }
	    
	    @ExceptionHandler(InvalidMetadataException.class)
	    public final ResponseEntity<Object> handleInvalidMetadataException(InvalidMetadataException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
//		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_REQ_PARAMETERS);
		responseBody.put("error", ex.getMessage());
//		responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");

		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	    }
	    
	    @ExceptionHandler(FileNotFoundException.class)
	    public final ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {

		Map<String, Object> responseBody = new HashMap<String, Object>();
		responseBody.put("path", getPath(request));
		responseBody.put("success", false);
		responseBody.put("errorCode", ErrorConstants.INVALID_REQ_PARAMETERS);
		responseBody.put("error", ex.getMessage());
//		responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");

		return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
	    }
	    
	    private String getPath(WebRequest request) {
		return ((ServletWebRequest) request).getRequest().getRequestURL().toString();
	    }
}
