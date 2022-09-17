package com.krishi.step;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.krishi.model.ViewRightsModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.krishi.entity.Rights;
import com.krishi.entity.StaticData;
import com.krishi.repository.RightsRepository;
import com.krishi.repository.StaticDataRepository;

public class RightsReader implements ItemReader<List<ViewRightsModel>>{

	private static final Logger LOGGER = LogManager.getLogger(RightsReader.class);

	@Autowired
	RightsRepository rightsRepo;

	@Autowired
	private StaticDataRepository staticDataRepository;
	
	/*Pick rights info from right table and send to RightsWriter*/
	public List<ViewRightsModel> read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		
		List<StaticData> staticDatas = staticDataRepository.findAll();
		String varified = staticDatas.stream().filter(p -> p.getKey().equals("isPennydropped")).collect(Collectors.toList()).get(0).getValue();
		String rightCreatedBy = null;
		List<String> rightSendStage = null;
		
		for( StaticData staticData : staticDatas) {
			if(staticData.getKey().equals("rightCreatedBy")) {
				rightCreatedBy = staticData.getValue();
			}
			if(staticData.getKey().equals("rightStage")) {
				rightSendStage = List.of(staticData.getValue().split(","));
			}
		}
		Boolean isPennydropped =true;
		if(varified.equals("0")) {
			isPennydropped=false;
		}
		Pageable page = PageRequest.of(0, 15);
//		List<Rights> rightsList = new ArrayList<>();
		List<ViewRightsModel> rightsList = new ArrayList<>();
		if(rightCreatedBy != null && !rightCreatedBy.equals("")) {
			List<String> lists = new ArrayList<String>(Arrays.asList(rightCreatedBy.split(",")));
			List<Integer> createdBy = new ArrayList<>();
			for(String list : lists ) {
				createdBy.add(Integer.parseInt(list.trim()));
			}
			if(isPennydropped) {
//				rightsList = rightsRepo.findAllRightsToShareUATTemp(createdBy, page,rightSendStage);
				rightsList = rightsRepo.findAllRightsToShareUATTemp(createdBy, rightSendStage);
			} else {
//				rightsList = rightsRepo.findAllRightsToShareUATTemp1(createdBy, page,rightSendStage);
				rightsList = rightsRepo.findAllRightsToShareUATTemp1(createdBy, rightSendStage);
			}
			
		} else {
			if(isPennydropped) {
//				rightsList = rightsRepo.findAllRightsToShare(page,rightSendStage);
				rightsList = rightsRepo.findAllRightsToShare(rightSendStage);
			} else {
//				rightsList = rightsRepo.findAllRightsToShare1(page,rightSendStage);
				rightsList = rightsRepo.findAllRightsToShare1(rightSendStage);
			}
		}
		
		
		LOGGER.info("INFO :: Rights item reader Successful");
		return rightsList.size() > 0 ? rightsList : null;
	}
	

}
