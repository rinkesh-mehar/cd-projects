/**
 * 
 */
package in.cropdata.cdtmasterdata.website.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.website.model.PartnershipRequest;
import in.cropdata.cdtmasterdata.website.model.vo.PartnershipRequestVO;
import in.cropdata.cdtmasterdata.website.repository.PartnershipRequestRepository;

/**
 * @author cropdata-kunal
 *
 */
@Service
public class PartnershipRequestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartnershipRequestService.class);
	
	private static final String SERVER_ERROR_MSG = "Server Error : ";

	@Autowired
	PartnershipRequestRepository partnershipRequestRepository;

	public ResponseMessage addPartnershipRequest(PartnershipRequest partnershipRequest) {

		ResponseMessage message = new ResponseMessage();
		try {

			partnershipRequestRepository.save(partnershipRequest);

			message.setMessage("Partnership request has been accepted");
			message.setSuccess(true);

		} catch (Exception e) {
			message.setMessage("Unable to accept the Partnership request " + e.getMessage());
			message.setSuccess(false);
		}

		return message;
	}

	public CopyOnWriteArrayList<Map<String, Object>> getIndustries() {
		try {

			CopyOnWriteArrayList<Map<String, Object>> list = partnershipRequestRepository.getAllIndustries();

			if (list != null && list.size() > 0) {
				Integer ID = 0;
				for (Map<String, Object> map : list) {

					if (map.containsValue("Other")) {

						ID = (Integer) map.get("ID");
						list.remove(map);
					}
				}

				Map<String, Object> m = new HashMap<String, Object>();
				m.put("ID", ID);
				m.put("Name", "Other");
				list.add(m);

			}

			return list;

		} catch (Exception e) {
			throw e;
		}
	}

	public List<Map<String, Object>> getPartnershipProgram() {
		try {

			List<Map<String, Object>> list = partnershipRequestRepository.getPartnershipProgram();

			return list;

		} catch (Exception e) {
			throw e;
		}
	}
	
	public Page<PartnershipRequestVO> getAllPartnershipRequestByPagenation(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all partnership request info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return partnershipRequestRepository.getAllPartnershipRequestByPagenation(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Partnership Request Data Found For Searched Text -> " + searchText);
		}
	}
	
	public PartnershipRequestVO getPartnershipRequestById(Integer id) {

		try {

			LOGGER.info("getting partnership request by Id...");
			return partnershipRequestRepository.getPartnershipRequestById(id);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Partnership Request Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

}
