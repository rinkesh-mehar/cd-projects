package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.PricingMspGroupInfo;
import in.cropdata.cdtmasterdata.model.PricingBcSlopeRange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


/**
 * @author RinkeshKM
 * @Date 09/11/20
 */

@Repository
public interface PricingBCSlopeRepository extends JpaRepository<PricingBcSlopeRange, Integer> {

    @Query(value = "select ID, SlopeMin, SlopeMax, BuyerConstant, Status from cdt_master_data.pricing_bc_slope_range pbsr where pbsr.SlopeMin like :searchText\n" +
            "                    or pbsr.SlopeMax like :searchText\n" +
            "                    or pbsr.BuyerConstant like :searchText\n" +
            "                    or pbsr.Status like :searchText",
            countQuery = "select ID, SlopeMin, SlopeMax, BuyerConstant, Status from cdt_master_data.pricing_bc_slope_range pbsr where pbsr.SlopeMin like :searchText\n" +
                    "                    or pbsr.SlopeMax like :searchText\n" +
                    "                    or pbsr.BuyerConstant like :searchText\n" +
                    "                    or pbsr.Status like :searchText",
            nativeQuery = true)
    Page<PricingMspGroupInfo> getBuyerConstantPaginated(Pageable pageable, String searchText);

    @Query(value = "select ID, SlopeMin, SlopeMax, BuyerConstant, Status from cdt_master_data.pricing_bc_slope_range pbsr",
            nativeQuery = true)
    List<PricingMspGroupInfo> getBuyerConstant();

    Optional<PricingBcSlopeRange> findBySlopeMinAndSlopeMaxAndBuyerConstant(BigDecimal slopeMin, BigDecimal slopeMax, BigDecimal buyerConstant);


}
