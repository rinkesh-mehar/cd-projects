package in.cropdata.toolsuite.filemanager.exceptions;
//package in.cropdata.toolsuite.filemanager.exceptions;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.ServletWebRequest;
//import org.springframework.web.context.request.WebRequest;
//import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
//
//import in.cropdata.toolsuite.filemanager.constant.ErrorConstants;
//
//@SuppressWarnings({ "unchecked", "rawtypes" })
//@ControllerAdvice
//public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionHandler.class);
//    
//    @ExceptionHandler(Exception.class)
//    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//	logger.error("Server Error: "+ex);
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", "ERR-500");
//	responseBody.put("error", "Server Error: "+ex);
//	responseBody.put("message", ex.getCause());
//
//	return new ResponseEntity(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @ExceptionHandler(CustomAccessDeniedException.class)
//    public ResponseEntity<Object> handleAccessDeniedException(CustomAccessDeniedException ex, WebRequest request) {
//	logger.error("Access Denied: "+ex);
//	return new ResponseEntity<Object>(ex.getMessage(), new HttpHeaders(), HttpStatus.FORBIDDEN);
//    }
//
//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
//	List<String> details = new ArrayList();
//	for (ObjectError error : ex.getBindingResult().getAllErrors()) {
//	    details.add(error.getDefaultMessage());
//	}
//	ErrorResponse error = new ErrorResponse("Validation Failed", details);
//	return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(DuplicatePathKeyException.class)
//    public final ResponseEntity<Object> handleDuplicatePathKeyException(DuplicatePathKeyException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.DUPLICATE_PATH_KEY);
//	responseBody.put("error", ex.getLocalizedMessage());
//	responseBody.put("message", "the value for pathKey is already exist, please resend it with different value.");
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.ACCEPTED);
//    }
//    
//    @ExceptionHandler(PathKeyNotFoundException.class)
//    public final ResponseEntity<Object> handlePathKeyNotFoundException(PathKeyNotFoundException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.PATH_KEY_NOT_FOUND);
//	responseBody.put("error", ex.getLocalizedMessage());
//	responseBody.put("message", "provided pathKey is not exist, please resend it with different value.");
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.ACCEPTED);
//    }
//    
//    @ExceptionHandler(FileUploadException.class)
//    public final ResponseEntity<Object> handleFileUploadException(FileUploadException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.INVALID_REQ_PARAMETERS);
//	responseBody.put("error", ex.getMessage());
//	//responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
//    }
//
//    @ExceptionHandler(ModuleNotFoundException.class)
//    public final ResponseEntity<Object> handleModuleNotFoundException(ModuleNotFoundException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.MODULE_NOT_FOUND);
//	responseBody.put("error", ex.getMessage());
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.NOT_FOUND);
//    }
//    
//    @ExceptionHandler(DirectoryNotFoundException.class)
//    public final ResponseEntity<Object> handleDirectoryNotFoundException(DirectoryNotFoundException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.DIR_NOT_FOUND);
//	responseBody.put("error", ex.getMessage());
////	responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.FAILED_DEPENDENCY);
//    }
//    
//    @ExceptionHandler(InvalidMetadataException.class)
//    public final ResponseEntity<Object> handleInvalidMetadataException(InvalidMetadataException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.INVALID_REQ_PARAMETERS);
//	responseBody.put("error", ex.getMessage());
////	responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
//    }
//    
//    @ExceptionHandler(FileNotFoundException.class)
//    public final ResponseEntity<Object> handleFileNotFoundException(FileNotFoundException ex, WebRequest request) {
//
//	Map<String, Object> responseBody = new HashMap();
////	responseBody.put("path", getPath(request));
//	responseBody.put("success", false);
//	responseBody.put("errorCode", ErrorConstants.INVALID_REQ_PARAMETERS);
//	responseBody.put("error", ex.getMessage());
////	responseBody.put("message", "missing pathKey or dirPath, required any one of these to upload a file.");
//
//	return new ResponseEntity<Object>(responseBody, HttpStatus.BAD_REQUEST);
//    }
//    
//    private String getPath(WebRequest request) {
//	return ((ServletWebRequest) request).getRequest().getRequestURL().toString();
//    }
//}