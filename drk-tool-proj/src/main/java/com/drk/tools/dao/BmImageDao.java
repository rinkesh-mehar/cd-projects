package com.drk.tools.dao;

import com.drk.tools.model.BmImageDetailIds;
import com.drk.tools.model.BmImageDetails;
import com.drk.tools.model.BmImageMetadata;
import com.drk.tools.repository.BmImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

/**
 * DAO to fetch the data from DB to matching with details in excel file.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Component
public class BmImageDao {

	private static final Logger logger = LoggerFactory.getLogger(BmImageDao.class);

	@Autowired
	private BmImageRepository repository;

	public BmImageDetailIds getBmDetailIds(BmImageDetails bmDetails) {
		logger.debug("Inside get BmDetail Ids...");
		BmImageDetailIds detailIds = new BmImageDetailIds();

		Integer commodityId = repository.getCommodityId(bmDetails.getCommodity());
		Integer stressTypeId = repository.getStressTypeId(bmDetails.getStressType());
		Integer stressId = repository.getStressId(bmDetails.getStress(), commodityId, stressTypeId);
		Integer plantPartId = "Weed Infestation".equals(bmDetails.getStressType())
				? repository.getPlantPartId("Whole plant")
				: repository.getPlantPartId(bmDetails.getPlantPart());

//		detailIds.setCaseId(this.getRandomNumber());
		detailIds.setCommodityId(commodityId);
//		detailIds.setPhenophaseId(repository.getPhenophaseId(bmDetails.getPhenophase()));
		detailIds.setPlantPartId(plantPartId);
		detailIds.setStressId(stressId);
//		if (bmDetails.getStressStage() != null && !bmDetails.getStressStage().isEmpty()){
//			detailIds.setStressStageId(repository.getStressStageId(bmDetails.getStressStage(), commodityId, stressTypeId, stressId));
//		}
		detailIds.setStressTypeId(stressTypeId);

		return detailIds;
	}

//	private BigInteger getRandomNumber() {
//		return BigInteger.valueOf(1023456987012365481l).add(BigInteger.valueOf(new Random().nextInt(999)));
//	}

	public List<BmImageMetadata> getBmDetailIds() {
		logger.debug("Inside get BmImage Metadata...");
		return repository.getBmImageMetadata();
	}

}
