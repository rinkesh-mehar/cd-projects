package in.cropdata.portal.service;


import in.cropdata.portal.vo.OpportunitiesVO;
import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.repository.OpportunitiesRepository;
import in.cropdata.portal.util.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpportunitiesService
{

    private static final Logger LOGGER = LoggerFactory.getLogger(OpportunitiesService.class);

    @Autowired
    private OpportunitiesRepository opportunitiesRepository;

    @Autowired
    ResponseMessageUtil responseMessageUtil;

    private static final String SERVER_ERROR_MSG = "Server Error : ";

    public List<OpportunitiesVO> getAllOpportunities()
    {

        try
        {
            LOGGER.info("getting all Opportunities info...");

            return opportunitiesRepository.getAllOpportunities();

        } catch (Exception ex)
        {
            LOGGER.error(SERVER_ERROR_MSG, ex);
            throw new DoesNotExistException("No Opportunities Data Found!");
        }
    }

    public Page<OpportunitiesVO> getAllOpportunitiesByPagenation(Integer page, Integer size, String searchText)
    {
        try
        {
            LOGGER.info("getting all opportunities info - paginated...");
            searchText = "%" + searchText + "%";
            Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").descending());

            return opportunitiesRepository.getAllOpportunitiesByPagenation(sortedByIdAsc, searchText);

        } catch (Exception ex)
        {
            LOGGER.error(SERVER_ERROR_MSG, ex);
            throw new DoesNotExistException("No Opportunity Data Found For Searched Text -> " + searchText);
        }
    }
    
    public OpportunitiesVO getOpportunitiesById(Integer id) {

		try {

			LOGGER.info("getting opportunity by Id...");
			return opportunitiesRepository.getOpportunitiesById(id);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Opportunity Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

}
