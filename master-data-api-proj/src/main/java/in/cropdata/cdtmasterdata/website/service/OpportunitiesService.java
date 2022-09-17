package in.cropdata.cdtmasterdata.website.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.PlatFormMaster;
import in.cropdata.cdtmasterdata.repository.PlatFormRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.OpportunitiesDto;
import in.cropdata.cdtmasterdata.website.model.Opportunities;
import in.cropdata.cdtmasterdata.website.model.OpportunitiesEducation;
import in.cropdata.cdtmasterdata.website.repository.OpportunitiesEducationRepository;
import in.cropdata.cdtmasterdata.website.repository.OpportunitiesRepository;

@Service
public class OpportunitiesService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpportunitiesService.class);

	@Autowired
	private OpportunitiesRepository opportunitiesRepository;
	@Autowired
	private OpportunitiesEducationRepository opportunitiesEducationRepository;

	@Autowired
	ResponseMessageUtil responseMessageUtil;
	
	@Autowired
	private PlatFormRepository platFormRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public List<OpportunitiesDto> getAllOpportunities() {

		try {
			LOGGER.info("getting all Opportunities info...");

			return opportunitiesRepository.getAllOpportunities();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Opportunities Data Found!");
		}
	}

	public Page<OpportunitiesDto> getAllOpportunitiesByPagenation(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all opportunities info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());

			return opportunitiesRepository.getAllOpportunitiesByPagenation(sortedByIdDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Opportunity Data Found For Searched Text -> " + searchText);
		}
	}

	@Transactional
	public ResponseMessage addOppotunity(Opportunities opportunities) {
		try {
			LOGGER.info(opportunities.getDescription());

			String descriptionWithParagraphTag = "";
			String descriptionlineWithParagraphTag = "";
			String[] descriptionLines = opportunities.getDescription().split("\\r?\\n");

			for (String line : descriptionLines) {

				descriptionlineWithParagraphTag = "<p>" + line + "</p>";
				descriptionWithParagraphTag = descriptionWithParagraphTag + descriptionlineWithParagraphTag + "\n";

			}

			String profileWithParagraphTag = "";
			String profilelineWithParagraphTag = "";
			String[] profileLines = opportunities.getProfile().split("\\r?\\n");

			for (String line : profileLines) {

				profilelineWithParagraphTag = "<p>" + line + "</p>";
				profileWithParagraphTag = profileWithParagraphTag + profilelineWithParagraphTag + "\n";

			}

			opportunities.setDescription(descriptionWithParagraphTag);
			opportunities.setProfile(profileWithParagraphTag);

			opportunitiesRepository.save(opportunities);
			LOGGER.info("Opportunity Saved...{}");
			
			List<OpportunitiesEducation> opportunitiesEducationList = new ArrayList<>();
			LOGGER.info("educationIDs...{} " + opportunities.getEducationID());
			Integer[] educationIDs = opportunities.getEducationID();
			for(Integer educationID : educationIDs) {
				OpportunitiesEducation opportunitiesEducation = new OpportunitiesEducation();
				opportunitiesEducation.setOpportunityID(opportunities.getId());
				opportunitiesEducation.setEducationID(educationID);
				opportunitiesEducationList.add(opportunitiesEducation);
			}
			opportunitiesEducationRepository.saveAll(opportunitiesEducationList);
			LOGGER.info("Opportunity Education Saved...{}");


		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
		return responseMessageUtil.sendResponse(true, "Oppotunity" + APIConstants.RESPONSE_ADD_SUCCESS, "");
	}

	public OpportunitiesDto getOpportunitiesById(Integer id) {

		try {

			LOGGER.info("getting opportunity by Id...");
			return opportunitiesRepository.getOpportunitiesById(id);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Opportunity Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	@Transactional
	public ResponseMessage UpdateOpportunity(Integer id, Opportunities opportunities) {
		try {
			Optional<Opportunities> opportunityDetail = opportunitiesRepository.findById(id);

			if (opportunityDetail.isPresent()) {

				LOGGER.info("updating opportunity info...");

				String descriptionWithParagraphTag = "";
				String descriptionlineWithParagraphTag = "";
				String[] descriptionLines = opportunities.getDescription().split("\\r?\\n");

				for (String line : descriptionLines) {

					descriptionlineWithParagraphTag = "<p>" + line + "</p>";
					descriptionWithParagraphTag = descriptionWithParagraphTag + descriptionlineWithParagraphTag + "\n";

				}

				String profileWithParagraphTag = "";
				String profilelineWithParagraphTag = "";
				String[] profileLines = opportunities.getProfile().split("\\r?\\n");

				for (String line : profileLines) {

					profilelineWithParagraphTag = "<p>" + line + "</p>";
					profileWithParagraphTag = profileWithParagraphTag + profilelineWithParagraphTag + "\n";

				}

				opportunities.setDescription(descriptionWithParagraphTag);
				opportunities.setProfile(profileWithParagraphTag);
				opportunities.setId(id);

				opportunitiesRepository.save(opportunities);
				
				
				Integer[] existingEducationIDs = opportunitiesEducationRepository.findEducationIdByOpportunityID(opportunities.getId());
				
				Integer[] educationIDs = opportunities.getEducationID();
				
				// deleting the educationIDs which exist in DB but de-selected while editing the form. 
				 
				for (int i = 0; i < existingEducationIDs.length; i++) 
		        { 
		            int j; 
		              
		            for (j = 0; j < educationIDs.length; j++) 
		                if (existingEducationIDs[i] == educationIDs[j]) 
		                    break; 
		  
		            if (j == educationIDs.length) {
		            	LOGGER.info(existingEducationIDs[i] + " * "); 
		            	
		            	opportunitiesEducationRepository.deleteByOpportunityIDAndEducationID(opportunities.getId(),existingEducationIDs[i]);
						
		            }
		        } 
				
				
			
				for(Integer educationID : educationIDs) {
					OpportunitiesEducation opportunitiesEducationUpdate = opportunitiesEducationRepository.findByOpportunityIDAndEducationID(opportunities.getId(),educationID);
					// Update if already exist else create new record
					if(opportunitiesEducationUpdate != null) {
						LOGGER.info("Update educationID...{} " + educationID); 
						opportunitiesEducationUpdate.setId(opportunitiesEducationUpdate.getId());
						opportunitiesEducationUpdate.setOpportunityID(opportunities.getId());
						opportunitiesEducationUpdate.setEducationID(educationID);
						opportunitiesEducationRepository.save(opportunitiesEducationUpdate);
						
					}else {
						LOGGER.info("Insert educationID...{} " + educationID); 
						OpportunitiesEducation opportunitiesEducationInsert = new OpportunitiesEducation();
						opportunitiesEducationInsert.setOpportunityID(opportunities.getId());
						opportunitiesEducationInsert.setEducationID(educationID);
						opportunitiesEducationRepository.save(opportunitiesEducationInsert);
					}
					
				}

				return responseMessageUtil.sendResponse(true, "Opportunity" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");

			} else {
				return responseMessageUtil.sendResponse(false,
						"Opportunity Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id, "");
			}

		} catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}
	
	public List<PlatFormMaster> getPlatForm(){
	    try
        {
            LOGGER.info("getting all platForm info...");
            return platFormRepository.findAll(Sort.by("name"));
        } catch (Exception ex){
	        LOGGER.error(SERVER_ERROR_MSG, ex);
	        throw new DoesNotExistException("No PlatForm found!");
        }
    }
	
	public ResponseMessage closeOpportunityById(int id) {
		try {
			Optional<Opportunities> optionalOpportunity = opportunitiesRepository.findById(id);
			if (optionalOpportunity.isPresent()) {

				opportunitiesRepository.closeOpportunityById(id);

				return responseMessageUtil.sendResponse(true, "Opportunity" + APIConstants.RESPONSE_CLOSE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Opportunity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage activeOpportunityById(int id) {
		try {
			Optional<Opportunities> optionalOpportunity = opportunitiesRepository.findById(id);
			if (optionalOpportunity.isPresent()) {

				opportunitiesRepository.activeOpportunityById(id);

				return responseMessageUtil.sendResponse(true, "Opportunity" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Opportunity" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public Integer[] getEducationIdsByOpportunityId(Integer opportunityId) {

		try {

			LOGGER.info("getting opportunity education by Opportunity Id...");
			return opportunitiesRepository.getEducationIdsByOpportunityId(opportunityId);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Opportunity Education Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + opportunityId);
		}

	}


}
