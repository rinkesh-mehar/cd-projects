/**
 * 
 */
package in.cropdata.toolsuite.drk.exceptions.cases;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import in.cropdata.toolsuite.drk.model.cases.DetailedMessage;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         17-Dec-2019
 */
@ControllerAdvice
public class GlobleRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter is missing";
		DetailedMessage detailedMessage = new DetailedMessage(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(detailedMessage, new HttpHeaders(), detailedMessage.getStatus());
	}

	@ExceptionHandler({ SizeMisMatchException.class })
	public ResponseEntity<Object> handleSizeMismatchException(SizeMisMatchException ex, WebRequest request) {
		String error = ex.getLocalizedMessage() + ": data is mismatch ";
		DetailedMessage detailedMessage = new DetailedMessage(HttpStatus.NOT_ACCEPTABLE, ex.getLocalizedMessage(), error);
		return new ResponseEntity<Object>(detailedMessage, new HttpHeaders(), detailedMessage.getStatus());
	}

}
