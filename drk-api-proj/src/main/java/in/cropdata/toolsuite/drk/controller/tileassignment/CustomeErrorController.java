package in.cropdata.toolsuite.drk.controller.tileassignment;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomeErrorController implements ErrorController {

    public CustomeErrorController() {
    }

    @GetMapping(value = "/error")
    public Map<String, Object> handleError(HttpServletRequest request) {

	Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	Map<String, Object> responseBody = new HashMap();
	if (status != null) {
	    
	    //responseBody.put("path", request.getRequestURL().toString());
	    responseBody.put("success", false);

	    Integer statusCode = Integer.valueOf(status.toString());

	    if (statusCode == HttpStatus.NOT_FOUND.value()) {

		responseBody.put("error", "API endpoint not found for the given URL.");
		return responseBody;
		
	    } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
		responseBody.put("error", "Internal Server Error.");
		return responseBody;
	    }
	}
	return responseBody;
    }

    @Override
    public String getErrorPath() {
	return "/error";
    }

}