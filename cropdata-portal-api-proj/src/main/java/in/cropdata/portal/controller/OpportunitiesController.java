package in.cropdata.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.service.OpportunitiesService;
import in.cropdata.portal.vo.OpportunitiesVO;

@RestController
@RequestMapping("/site/opportunities")
public class OpportunitiesController
{

    @Autowired
    private OpportunitiesService opportunitiesService;


    //Portal API
    @GetMapping("/list")
    public List<OpportunitiesVO> getAllOpportunities()
    {

        return opportunitiesService.getAllOpportunities();

    }

    @GetMapping("/paginatedList")
    public Page<OpportunitiesVO> getAllOpportunitiesByPagenation(@RequestParam("page") Integer page,
                                                                 
                                                                 @RequestParam(required = false, defaultValue = "") String searchText)
    {

//        if (page == null || size == null)
//        {
//            throw new InvalidDataException("page or size can not be null!");
//        }

        return opportunitiesService.getAllOpportunitiesByPagenation(page, 10, searchText);
    }
    
    @GetMapping("id/{id}")
	public OpportunitiesVO getOpportunityById(@PathVariable(required = true) Integer id) {

		return opportunitiesService.getOpportunitiesById(id);

	}

}
