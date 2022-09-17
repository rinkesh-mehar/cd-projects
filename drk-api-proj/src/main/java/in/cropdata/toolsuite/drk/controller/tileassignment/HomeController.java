package in.cropdata.toolsuite.drk.controller.tileassignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.controller.util.ControllerUtil;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;

@RestController
public class HomeController {
	Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/")
	public void index() {
//	String classpathStr = System.getProperty("java.class.path");
//	System.out.print(classpathStr.substring(0, classpathStr.indexOf("/target/classes:")));
//	
		final String userIpAddress = ControllerUtil.getCurrentRequest().getRemoteAddr();
		final String userAgent = ControllerUtil.getCurrentRequest().getHeader("user-agent");
		String query = "Accessed index without API Key";
		final String userDisplay = String.format("Query:%s,IP:%s Browser:%s", query, userIpAddress, userAgent);
		logger.error(userDisplay);
		throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
				"Api Key is required to access this Resource");
	}
}
