package in.cropdata.portal.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.PartnershipRequest;
import in.cropdata.portal.properties.AppProperties;
import in.cropdata.portal.repository.PartnershipRequestRepository;
import in.cropdata.portal.util.EmailServiceUtil;

/**
 * @author cropdata-kunal
 *
 */
@Service
public class PartnershipRequestService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(PartnershipRequestService.class);

	@Autowired
	PartnershipRequestRepository partnershipRequestRepository;
	
	@Autowired
	private EmailServiceUtil emailServiceUtil;
	
	@Autowired
	private AppProperties properties;

	public ResponseMessage addPartnershipRequest(PartnershipRequest partnershipRequest) {

		ResponseMessage message = new ResponseMessage();
		try {

			partnershipRequestRepository.save(partnershipRequest);
			
			//Sending Mail
			try {
				LOGGER.info("Partner Request Saved. Sending Email...");
				LOGGER.info("emailId : " + partnershipRequest.getEmail() + " Name : " + partnershipRequest.getName());
				String emailResponse = emailServiceUtil.sendMail(partnershipRequest.getEmail(), partnershipRequest.getName(),properties.getFromMailId(),properties.getFromMailerName(),properties.getPartenershipRegMailTemplateId());	
				LOGGER.info("Email Sent Successfully...{}",emailResponse);
			} catch (InvalidDataException ex) {
				LOGGER.error("Error Validating Email...{}", ex.getMessage());
			}

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

}