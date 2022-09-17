package in.cropdata.gateway.framework;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.gateway.model.Zuul;
import in.cropdata.gateway.repository.ZuulRepository;



/**
 * @author Vivek Gajbhiye
 *
 */
@Service
public class ApplicationService {
	
	@Autowired
	ZuulRepository zuulRepository;
	
	@Transactional
	public void loadData() {
		ApplicationSession applicationSession = ApplicationSession.getCurrent();
	}
	
	public List<Zuul> fetchAllZuulRoutes(){
		return this.zuulRepository.findAll();
	}

}
