/**
 * 
 */
package in.cropdata.gateway.framework;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import in.cropdata.gateway.model.Zuul;

/**
 * @author Vivek Gajbhiye
 *
 */
@Component
@Scope("application")
public class ApplicationSession {

	@Autowired
	ApplicationService applicationService;

	private List<Zuul> zuulList = new LinkedList<>();

	public static ApplicationSession getCurrent() {
		return ApplicationContextProvider.getApplicationContext().getBean(ApplicationSession.class);
	}

	public void forceReloadAll() {
		this.forceReloadZuulList();
	}

	public void forceReloadZuulList() {
		this.zuulList = null;
		this.zuulList = applicationService.fetchAllZuulRoutes();
	}

	public List<Zuul> getZuulList() {
		if (this.zuulList != null && !this.zuulList.isEmpty()) {
			return this.zuulList;
		} else {
			return this.zuulList = applicationService.fetchAllZuulRoutes();
		}
	}

}
