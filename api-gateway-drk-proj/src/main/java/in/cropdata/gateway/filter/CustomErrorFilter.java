package in.cropdata.gateway.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;



/**
 * @author Vivek Gajbhiye
 *
 */
@Component
public class CustomErrorFilter extends ZuulFilter {

	private static final Logger LOG = LoggerFactory.getLogger(CustomErrorFilter.class);

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return -1; // Needs to run before SendErrorFilter which has filterOrder == 0
	}

	@Override
	public boolean shouldFilter() {
		// only forward to errorPath if it hasn't been forwarded to already
		RequestContext ctx = RequestContext.getCurrentContext();
		LOG.info(" ctx " + ctx.getRequest().getRequestURI());
		return RequestContext.getCurrentContext().containsKey("error.status_code");
	}

	@Override
	public Object run() {
		try {
			RequestContext ctx = RequestContext.getCurrentContext();

			Object e = ctx.get("error.exception");
			LOG.info(" RouteHost " + ctx.getRouteHost());
			if (e != null && e instanceof ZuulException) {
//				UriComponentsBuilder uriComponent = UriComponentsBuilder.fromHttpUrl(ctx.get);

				ZuulException zuulException = (ZuulException) e;
				LOG.error("Zuul failure detected: " + zuulException.getMessage(), zuulException);

				// Remove error code to prevent further error handling in follow up filters
				ctx.remove("error.status_code");

				// Populate context with new response values
				List<String> details = new ArrayList();
				details.add(zuulException.getMessage());
				Map<String, Object> responseBody = new HashMap();

				responseBody.put("errorCode", "not found");
				responseBody.put("error", details);

				StringBuilder mapAsString = new StringBuilder("{");
				for (String key : responseBody.keySet()) {
					mapAsString.append(key + "=" + responseBody.get(key) + ", ");
				}
				mapAsString.delete(mapAsString.length() - 2, mapAsString.length()).append("}");

				ctx.setResponseBody(mapAsString.toString());
				ctx.getResponse().setContentType("application/json");
				ctx.setResponseStatusCode(500); // Can set any error code as excepted

			}
		} catch (Exception ex) {
			LOG.error("Exception filtering in custom error filter", ex);
			ReflectionUtils.rethrowRuntimeException(ex);
		}
		return null;
	}
}