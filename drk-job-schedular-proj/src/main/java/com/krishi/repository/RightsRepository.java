package com.krishi.repository;

import java.util.List;

import com.krishi.model.ViewRightsModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.krishi.entity.Rights;

public interface RightsRepository extends JpaRepository<Rights, String> {

	@Query("Select r from Rights as r,FarmCase as fc,FarmerFarm as ff ,FarmerKyc as fkc,FarmerBankAccount as bd,CaseKml as ck where "
			+ " fkc.isVerified =1 and bd.pennydropStatus ='paid' and ck.isVerified =1 and "
			+ " (r.status is NULL) and r.transactionId is NULL and "
			+ " r.caseId = ck.farmCaseId and fc.id = ck.farmCaseId and fc.farmId = ff.id and"

			/** replace farmerId to caseId - As per discussion in 21/08/2021*/
			/*+ " ff.farmerId = fkc.farmerId and ff.farmerId = bd.farmerId and ( r.isVerified = 1 or r.versionNumber > 1)"*/
			+ " ff.farmerId = fkc.farmerId and fc.id = bd.caseId and ( r.isVerified = 1 or r.versionNumber > 1)"
			+ " and r.stage in (:rightSendStage)")
	List<Rights> findAllRightsToShare(Pageable page, List<String> rightSendStage);

	@Query(value = "select right_id as RightId, rights_version as rightsVersion, case_id as caseId, current_qty as currentQty,\n" +
			"       estimated_qty as estimatedQty, standard_qty as standardQty, allowable_variance_inqty_neg as allowableVarianceInqtyNeg,\n" +
			"       allowable_variance_inqty_pos as allowableVarianceInqtyPos, allowable_variance_inqty_pos_per as allowableVarianceInqtyPosPer,\n" +
			"       allowable_variance_inqty_neg_per as allowableVarianceInqtyNegPer, estimated_qly as estimatedQly,\n" +
			"       current_qly as currentQly, allowable_variance_inqty_grade as allowableVarianceInqtyGrade, mbep, domrestrictions,\n" +
			"       int_restrictions as intRestrictions, del_date_time as delDateTime, logistic_hub_id as logisticHubId,\n" +
			"       logistic_hub_address as logisticHubAddress, farmer_default as farmerDefault, split_field as splitField,\n" +
			"       geo_adjacent as geoAdjacent, risk_report as riskReport, right_certificate as rightCertificate, \n" +
			"       record_by as recordBy, record_date as recordDate, lot_id as lotId, transaction_id as transactionId,\n" +
			"       phenophase_id as phenophaseId, stage, due_money_by_farmer as dueMoneyByFarmer, amount_collected as amountCollected,\n" +
			"       contracted_price as contractedPrice, expected_delivery_date as expectedDeliveryDate,\n" +
			"       total_weighbridge_weight as totalWeighbridgeWeight, total_weighment_weight as totalWeighmentWeight,\n" +
			"       number_of_bags_farmer as numberOfBagsFarmer, number_of_bags_counting as numberOfBagsCounting,\n" +
			"       number_of_bags_unloaded as numberOfBagsUnloaded, number_of_vehicles as numberOfVehicles, right_quality as rightQuality,\n" +
			"       agm_variety_id as agmVarietyId, reporting_pass_qr_code as reportingPassQrCode, exit_pass_qr_code as exitPassQrCode,\n" +
			"       sample_seal_qr_code as sampleSealQrCode, stack_no as stackNo, unloaded_bag_photo_url as unloadedBagPhotoUrl,\n" +
			"       payment_status as paymentStatus, deliverable_quantity as deliverableQuantity, estimated_travel_time as estimatedTravelTime,\n" +
			"       harvested_quantity as harvestedQuantity, right_sign_url as rightSignUrl, shift_id as shiftId,\n" +
			"       status, is_verified as isVerified, crop_type as cropType, famer_kyc_verified as famerKycVerified,\n" +
			"       case_kml_verified as caseKmlVerified, pennydrop_status as pennydropStatus, international_restriction as \n" +
			"		internationalRestriction, comments from view_rights as r\n" +
			"where r.famer_kyc_verified = 1 and r.case_kml_verified = 1 and r.pennydrop_status = 'paid'\n" +
			"  and r.status is null and r.transaction_id is null and (r.is_verified = 1 or r.rights_version > 1)\n" +
			"  and r.stage in (?1) and r.sent_to_agm ='No'", nativeQuery = true)
	List<ViewRightsModel> findAllRightsToShare(List<String> rightSendStage);

	@Query("Select r from Rights as r,FarmCase as fc,FarmerFarm as ff ,FarmerKyc as fkc,FarmerBankAccount as bd,CaseKml as ck where "
			+ " fkc.isVerified =1 and bd.pennydropStatus ='paid' and ck.isVerified =1 and "
			+ " (r.status is NULL) and r.transactionId is NULL and "
			+ " r.caseId = ck.farmCaseId and fc.id = ck.farmCaseId and fc.farmId = ff.id and"

			/** replace farmerId to caseId - As per discussion in 21/08/2021*/
			/*+ " ff.farmerId = fkc.farmerId and ff.farmerId = bd.farmerId and ( r.isVerified = 1 or r.versionNumber > 1) "*/
			+ " ff.farmerId = fkc.farmerId and fc.id = bd.caseId and ( r.isVerified = 1 or r.versionNumber > 1) "
			+ " and r.stage in (:rightSendStage) and r.recordCreatedBy in (:createdBy) ")
	List<Rights> findAllRightsToShareUATTemp(@Param("createdBy") List<Integer> createdBy,Pageable page, List<String> rightSendStage);

	@Query(value = "select right_id as RightId, rights_version as rightsVersion, case_id as caseId, current_qty as currentQty,\n" +
			"       estimated_qty as estimatedQty, standard_qty as standardQty, allowable_variance_inqty_neg as allowableVarianceInqtyNeg,\n" +
			"       allowable_variance_inqty_pos as allowableVarianceInqtyPos, allowable_variance_inqty_pos_per as allowableVarianceInqtyPosPer,\n" +
			"       allowable_variance_inqty_neg_per as allowableVarianceInqtyNegPer, estimated_qly as estimatedQly,\n" +
			"       current_qly as currentQly, allowable_variance_inqty_grade as allowableVarianceInqtyGrade, mbep, domrestrictions,\n" +
			"       int_restrictions as intRestrictions, del_date_time as delDateTime, logistic_hub_id as logisticHubId,\n" +
			"       logistic_hub_address as logisticHubAddress, farmer_default as farmerDefault, split_field as splitField,\n" +
			"       geo_adjacent as geoAdjacent, risk_report as riskReport, right_certificate as rightCertificate, \n" +
			"       record_by as recordBy, record_date as recordDate, lot_id as lotId, transaction_id as transactionId,\n" +
			"       phenophase_id as phenophaseId, stage, due_money_by_farmer as dueMoneyByFarmer, amount_collected as amountCollected,\n" +
			"       contracted_price as contractedPrice, expected_delivery_date as expectedDeliveryDate,\n" +
			"       total_weighbridge_weight as totalWeighbridgeWeight, total_weighment_weight as totalWeighmentWeight,\n" +
			"       number_of_bags_farmer as numberOfBagsFarmer, number_of_bags_counting as numberOfBagsCounting,\n" +
			"       number_of_bags_unloaded as numberOfBagsUnloaded, number_of_vehicles as numberOfVehicles, right_quality as rightQuality,\n" +
			"       agm_variety_id as agmVarietyId, reporting_pass_qr_code as reportingPassQrCode, exit_pass_qr_code as exitPassQrCode,\n" +
			"       sample_seal_qr_code as sampleSealQrCode, stack_no as stackNo, unloaded_bag_photo_url as unloadedBagPhotoUrl,\n" +
			"       payment_status as paymentStatus, deliverable_quantity as deliverableQuantity, estimated_travel_time as estimatedTravelTime,\n" +
			"       harvested_quantity as harvestedQuantity, right_sign_url as rightSignUrl, shift_id as shiftId,\n" +
			"       status, is_verified as isVerified, crop_type as cropType, famer_kyc_verified as famerKycVerified,\n" +
			"       case_kml_verified as caseKmlVerified, pennydrop_status as pennydropStatus, international_restriction as \n" +
			"		internationalRestriction, comments from view_rights as r\n" +
			"where r.famer_kyc_verified = 1 and r.case_kml_verified = 1 and r.pennydrop_status = 'paid'\n" +
			"  and r.status is null and r.transaction_id is null and (r.is_verified = 1 or r.rights_version > 1)\n" +
			"  and r.stage in (?2) and r.record_by in (?1) and r.sent_to_agm ='No'", nativeQuery = true)
	List<ViewRightsModel> findAllRightsToShareUATTemp(List<Integer> createdBy, List<String> rightSendStage);

//	@Query("Select r from Rights as r,FarmCase as fc,FarmerFarm as ff ,FarmerKyc as fkc,FarmerBankAccount as bd,CaseKml as ck where "
//			+ " fkc.isVerified =1 and ck.isVerified =1 and "
//			+ " (r.status is NULL) and r.transactionId is NULL and "
//			+ " r.caseId = ck.farmCaseId and fc.id = ck.farmCaseId and fc.farmId = ff.id and"
//
//			/** replace farmerId to caseId - As per discussion in 21/08/2021*/
//			/*+ " ff.farmerId = fkc.farmerId and ff.farmerId = bd.farmerId and ( r.isVerified = 1 or r.versionNumber > 1)"*/
//			+ " ff.farmerId = fkc.farmerId and fc.id = bd.caseId and ( r.isVerified = 1 or r.versionNumber > 1)"
//			+ " and r.stage in (:rightSendStage)")
//	List<Rights> findAllRightsToShare1(Pageable page, List<String> rightSendStage);

	@Query(value = "select right_id as RightId, rights_version as rightsVersion, case_id as caseId, current_qty as currentQty,\n" +
			"       estimated_qty as estimatedQty, standard_qty as standardQty, allowable_variance_inqty_neg as allowableVarianceInqtyNeg,\n" +
			"       allowable_variance_inqty_pos as allowableVarianceInqtyPos, allowable_variance_inqty_pos_per as allowableVarianceInqtyPosPer,\n" +
			"       allowable_variance_inqty_neg_per as allowableVarianceInqtyNegPer, estimated_qly as estimatedQly,\n" +
			"       current_qly as currentQly, allowable_variance_inqty_grade as allowableVarianceInqtyGrade, mbep, domrestrictions,\n" +
			"       int_restrictions as intRestrictions, del_date_time as delDateTime, logistic_hub_id as logisticHubId,\n" +
			"       logistic_hub_address as logisticHubAddress, farmer_default as farmerDefault, split_field as splitField,\n" +
			"       geo_adjacent as geoAdjacent, risk_report as riskReport, right_certificate as rightCertificate, \n" +
			"       record_by as recordBy, record_date as recordDate, lot_id as lotId, transaction_id as transactionId,\n" +
			"       phenophase_id as phenophaseId, stage, due_money_by_farmer as dueMoneyByFarmer, amount_collected as amountCollected,\n" +
			"       contracted_price as contractedPrice, expected_delivery_date as expectedDeliveryDate,\n" +
			"       total_weighbridge_weight as totalWeighbridgeWeight, total_weighment_weight as totalWeighmentWeight,\n" +
			"       number_of_bags_farmer as numberOfBagsFarmer, number_of_bags_counting as numberOfBagsCounting,\n" +
			"       number_of_bags_unloaded as numberOfBagsUnloaded, number_of_vehicles as numberOfVehicles, right_quality as rightQuality,\n" +
			"       agm_variety_id as agmVarietyId, reporting_pass_qr_code as reportingPassQrCode, exit_pass_qr_code as exitPassQrCode,\n" +
			"       sample_seal_qr_code as sampleSealQrCode, stack_no as stackNo, unloaded_bag_photo_url as unloadedBagPhotoUrl,\n" +
			"       payment_status as paymentStatus, deliverable_quantity as deliverableQuantity, estimated_travel_time as estimatedTravelTime,\n" +
			"       harvested_quantity as harvestedQuantity, right_sign_url as rightSignUrl, shift_id as shiftId,\n" +
			"       status, is_verified as isVerified, crop_type as cropType, famer_kyc_verified as famerKycVerified,\n" +
			"       case_kml_verified as caseKmlVerified, pennydrop_status as pennydropStatus, international_restriction as \n" +
			"		internationalRestriction, comments from view_rights as r\n" +
			"where r.famer_kyc_verified = 1 and r.case_kml_verified = 1\n" +
			"  and r.status is null and r.transaction_id is null and (r.is_verified = 1 or r.rights_version > 1)\n" +
			"  and r.stage in (?1) and r.sent_to_agm ='No'", nativeQuery = true)
	List<ViewRightsModel> findAllRightsToShare1(List<String> rightSendStage);
	
	@Query("Select r from Rights as r,FarmCase as fc,FarmerFarm as ff ,FarmerKyc as fkc,FarmerBankAccount as bd,CaseKml as ck where "
			+ " fkc.isVerified =1 and ck.isVerified =1 and "
			+ " (r.status is NULL) and r.transactionId is NULL and "
			+ " r.caseId = ck.farmCaseId and fc.id = ck.farmCaseId and fc.farmId = ff.id and"

			/** replace farmerId to caseId - As per discussion in 21/08/2021*/
			/*+ " ff.farmerId = fkc.farmerId and ff.farmerId = bd.farmerId and ( r.isVerified = 1 or r.versionNumber > 1) "*/
			+ " ff.farmerId = fkc.farmerId and fc.id = bd.caseId and ( r.isVerified = 1 or r.versionNumber > 1) "
			+ " and r.stage in (:rightSendStage) and r.recordCreatedBy in (:createdBy) ")
	List<Rights> findAllRightsToShareUATTemp1(@Param("createdBy") List<Integer> createdBy, Pageable page, List<String> rightSendStage);


	@Query(value = "select right_id as RightId, rights_version as rightsVersion, case_id as caseId, current_qty as currentQty,\n" +
			"       estimated_qty as estimatedQty, standard_qty as standardQty, allowable_variance_inqty_neg as allowableVarianceInqtyNeg,\n" +
			"       allowable_variance_inqty_pos as allowableVarianceInqtyPos, allowable_variance_inqty_pos_per as allowableVarianceInqtyPosPer,\n" +
			"       allowable_variance_inqty_neg_per as allowableVarianceInqtyNegPer, estimated_qly as estimatedQly,\n" +
			"       current_qly as currentQly, allowable_variance_inqty_grade as allowableVarianceInqtyGrade, mbep, domrestrictions,\n" +
			"       int_restrictions as intRestrictions, del_date_time as delDateTime, logistic_hub_id as logisticHubId,\n" +
			"       logistic_hub_address as logisticHubAddress, farmer_default as farmerDefault, split_field as splitField,\n" +
			"       geo_adjacent as geoAdjacent, risk_report as riskReport, right_certificate as rightCertificate, \n" +
			"       record_by as recordBy, record_date as recordDate, lot_id as lotId, transaction_id as transactionId,\n" +
			"       phenophase_id as phenophaseId, stage, due_money_by_farmer as dueMoneyByFarmer, amount_collected as amountCollected,\n" +
			"       contracted_price as contractedPrice, expected_delivery_date as expectedDeliveryDate,\n" +
			"       total_weighbridge_weight as totalWeighbridgeWeight, total_weighment_weight as totalWeighmentWeight,\n" +
			"       number_of_bags_farmer as numberOfBagsFarmer, number_of_bags_counting as numberOfBagsCounting,\n" +
			"       number_of_bags_unloaded as numberOfBagsUnloaded, number_of_vehicles as numberOfVehicles, right_quality as rightQuality,\n" +
			"       agm_variety_id as agmVarietyId, reporting_pass_qr_code as reportingPassQrCode, exit_pass_qr_code as exitPassQrCode,\n" +
			"       sample_seal_qr_code as sampleSealQrCode, stack_no as stackNo, unloaded_bag_photo_url as unloadedBagPhotoUrl,\n" +
			"       payment_status as paymentStatus, deliverable_quantity as deliverableQuantity, estimated_travel_time as estimatedTravelTime,\n" +
			"       harvested_quantity as harvestedQuantity, right_sign_url as rightSignUrl, shift_id as shiftId,\n" +
			"       status, is_verified as isVerified, crop_type as cropType, famer_kyc_verified as famerKycVerified,\n" +
			"       case_kml_verified as caseKmlVerified, pennydrop_status as pennydropStatus, international_restriction as \n" +
			"		internationalRestriction, comments from view_rights as r\n" +
			"where r.famer_kyc_verified = 1 and r.case_kml_verified = 1\n" +
			"  and r.status is null and r.transaction_id is null and (r.is_verified = 1 or r.rights_version > 1)\n" +
			"  and r.stage in (?2) and r.record_by in (?1) and r.sent_to_agm ='No'", nativeQuery = true)
	List<ViewRightsModel> findAllRightsToShareUATTemp1(List<Integer> createdBy, List<String> rightSendStage);

	// removed createdDate condition needed to be added next when conformed
	@Query("Select r from Rights as r where r.isVerified = 1")
	// List<Rights> findAllsignedRights(@Param("currentDate") Date date );
	List<Rights> findAllsignedRights();

	@Query("Select r from Rights as r where  r.caseId=:caseId")
	Rights findByCaseId(@Param("caseId") String caseId);
	
	Rights findTop1ByCaseIdOrderByVersionNumberDesc(String caseId);

	Rights findTop1ByIdOrderByVersionNumberDesc(String rightsId);
	
	@Modifying 
	@Query("update Rights set rightCertificate = :rightCertificate where id = :id and versionNumber = :versionNumber")
	Integer updateRightsById(@Param("id") String id, @Param("versionNumber") Integer versionNumber, @Param("rightCertificate") String rightCertificate);
	
	@Query("Select r from Rights as r where r.id=:rightId and r.versionNumber = :versionNumber and r.rightCertificate is null")
	List<Rights> findByRightIdVersionNumber(@Param("rightId") String rightId, @Param("versionNumber") Integer versionNumber);

}
