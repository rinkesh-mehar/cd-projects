package com.krishi.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RightDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public Map<String, Object> getRights(String rightID) {
        Map<String, Object> returnResponse = new HashMap<>();
        try {
            returnResponse = jdbcTemplate.queryForMap("select id, version_number as versionNumber, case_id as caseId, current_quantity as currentQuantity, estimated_quantity as estimatedQuantity, standard_quantity as standardQuantity,\n" +
                    "       allowable_variance_qty_pos as allowableVarianceQtyPos, allowable_variance_qty_neg as allowableVarianceQtyNeg, allowable_variance_qty_pos_per as allowableVarianceQtyPosPer,\n" +
                    "       allowable_variance_qty_neg_per as allowableVarianceQtyNegPer, estimated_quality as estimatedQuality, current_quality as currentQuality, allowable_variance_quality as allowableVarianceQuality, mbep,\n" +
                    "       domestic_restriction as domesticRestriction, international_restriction as internationalRestriction, delivery_date as deliveryDateTime, logistic_hub_id as logisticHubId, logistic_hub_address as logisticHubAddress,\n" +
                    "       farmer_default as farmerDefault, split_field as splitField, geographically_adjustent as geographicallyAdjustent, risk_report as riskReport, right_certificate as rightCertificate, record_created_by as recordCreatedBy,\n" +
                    "       record_date_time as recordDateTime, status, status_receiving_date AS statusReceivingDate, comments, lot_id AS lotId, is_verified AS isVerified, transaction_id AS transactionId, phenophase_id AS phenophaseId,\n" +
                    "       stage, due_amount AS dueAmount, amount_collected AS amountCollected, contracted_price AS contractedPrice, expected_delivery_date AS expectedDeliveryDate, total_weighbridge_weight AS totalWeighbridgeWeight ,\n" +
                    "       total_weighment_weight AS totalWeighmentWeight, number_of_bags_farmer AS numberOfBagsFarmer, number_of_bags_counting AS numberOfBagsCounting, number_of_bags_unloaded AS numberOfBagsUnloaded,\n" +
                    "       number_of_vehicles AS numberOfVehicles, right_quality AS rightQuality, reporting_pass_qr_code AS reportingPassQrCode, exit_pass_qr_code AS exitPassQrCode, sample_seal_qr_code AS sampleSealQrCode, stack_no AS stackNo,\n" +
                    "       seal_bag_photo_URL AS sealBagPhotoUrl, payment_status AS paymentStatus, deliverable_quantity AS deliverableQuantity, created_at AS createdAt, updated_at AS updatedAt, harvested_quantity AS harvestedQuantity\n" +
                    "from rights where id = '" + rightID + "' and version_number = (select max(rights.version_number) from rights where id = '" + rightID + "')");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Exception in get Right");
        }
        return returnResponse;
    }
}
