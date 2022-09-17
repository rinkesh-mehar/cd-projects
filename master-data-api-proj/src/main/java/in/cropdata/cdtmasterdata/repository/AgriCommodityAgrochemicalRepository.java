package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.AgriAgrochemicalDTO;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalInfDto;
import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;

public interface AgriCommodityAgrochemicalRepository extends JpaRepository<AgriCommodityAgrochemical, Integer> {

    @Query(value = "SELECT DISTINCT Name,MIN(ID) as ID, AgrochemicalTypeID FROM agri_commodity_agrochemical where AgrochemicalTypeID=?1 GROUP BY Name ORDER BY Name ASC", nativeQuery = true)
    List<AgriAgrochemicalInfDto> findAllByAgrochemicalTypeId(int agrochemicalTypeId);

    List<AgriCommodityAgrochemical> findAllByCommodityId(int id);

    @Query(value = "SELECT AA.ID,AA.AgrochemicalTypeID,AA.Name,AA.WaitingPeriod,AA.CommodityID,AA.StressTypeID,AA.CIBRCApproved, AA.Status,  \n"
            + "						AAT.Name as AgrochemicalType, AC.Name as Commodity, AST.Name as StressType, AA.IsValid, AA.ErrorMessage FROM agri_commodity_agrochemical AA  \n"
            + "						Left Join agri_agrochemical_type AAT on (AA.AgrochemicalTypeID = AAT.ID) \n"
            + "						Left Join agri_commodity AC on (AA.CommodityID = AC.ID) \n"
            + "						Left Join agri_stress_type AST on (AA.StressTypeID = AST.ID)\n"
            + "					 where AA.Name like :searchText\n" + "					 OR AC.Name like :searchText\n"
            + "					 OR AAT.Name like :searchText\n" + "					 OR AST.Name like :searchText\n"
            + "					 OR AA.WaitingPeriod like :searchText\n"
            + "					 OR AA.CIBRCApproved like :searchText", countQuery = "SELECT AA.ID,AA.AgrochemicalTypeID,AA.Name,AA.WaitingPeriod,AA.CommodityID,AA.StressTypeID,AA.CIBRCApproved, AA.Status,  \n"
            + "						AAT.Name as AgrochemicalType, AC.Name as Commodity, AST.Name as StressType, AA.IsValid, AA.ErrorMessage FROM agri_commodity_agrochemical AA  \n"
            + "						Left Join agri_agrochemical_type AAT on (AA.AgrochemicalTypeID = AAT.ID) \n"
            + "						Left Join agri_commodity AC on (AA.CommodityID = AC.ID) \n"
            + "						Left Join agri_stress_type AST on (AA.StressTypeID = AST.ID)\n"
            + "					 where AA.Name like :searchText\n"
            + "					 OR AC.Name like :searchText\n"
            + "					 OR AAT.Name like :searchText\n"
            + "					 OR AST.Name like :searchText\n"
            + "					 OR AA.WaitingPeriod like :searchText\n"
            + "					 OR AA.CIBRCApproved like :searchText", nativeQuery = true)
    Page<AgriAgrochemicalInfDto> getAllAgrochemical(Pageable pageable, String searchText);


    @Query(value = "SELECT AA.ID,AA.AgrochemicalTypeID,AA.Name,AA.WaitingPeriod,AA.CommodityID,AA.StressTypeID,AA.CIBRCApproved, AA.Status,  \n"
            + "						AAT.Name as AgrochemicalType, AC.Name as Commodity, AST.Name as StressType, AA.ErrorMessage FROM agri_commodity_agrochemical AA  \n"
            + "						Left Join agri_agrochemical_type AAT on (AA.AgrochemicalTypeID = AAT.ID) \n"
            + "						Left Join agri_commodity AC on (AA.CommodityID = AC.ID) \n"
            + "						Left Join agri_stress_type AST on (AA.StressTypeID = AST.ID)\n"
            + "					 where AA.IsValid = 0 and (AA.Name like :searchText\n" + "					 OR AC.Name like :searchText\n"
            + "					 OR AAT.Name like :searchText\n" + "					 OR AST.Name like :searchText\n"
            + "					 OR AA.WaitingPeriod like :searchText\n"
            + "					 OR AA.CIBRCApproved like :searchText)", countQuery = "SELECT AA.ID,AA.AgrochemicalTypeID,AA.Name,AA.WaitingPeriod,AA.CommodityID,AA.StressTypeID,AA.CIBRCApproved, AA.Status,  \n"
            + "						AAT.Name as AgrochemicalType, AC.Name as Commodity, AST.Name as StressType, AA.ErrorMessage FROM agri_commodity_agrochemical AA  \n"
            + "						Left Join agri_agrochemical_type AAT on (AA.AgrochemicalTypeID = AAT.ID) \n"
            + "						Left Join agri_commodity AC on (AA.CommodityID = AC.ID) \n"
            + "						Left Join agri_stress_type AST on (AA.StressTypeID = AST.ID)\n"
            + "					 where AA.IsValid = 0 and (AA.Name like :searchText\n"
            + "					 OR AC.Name like :searchText\n"
            + "					 OR AAT.Name like :searchText\n"
            + "					 OR AST.Name like :searchText\n"
            + "					 OR AA.WaitingPeriod like :searchText\n"
            + "					 OR AA.CIBRCApproved like :searchText)", nativeQuery = true)
    Page<AgriAgrochemicalInfDto> getAllAgrochemicalInvalidated(Pageable pageable, String searchText);

    List<AgriCommodityAgrochemical> findAllByOrderByNameAsc();

    @Query(value = "SELECT AA.ID,AA.AgrochemicalTypeID,AAT.Name as AgrochemicalType,AA.Name,AA.WaitingPeriod,AA.CommodityID,AC.Name as Commodity,\n"
            + "AA.CIBRCApproved,AA.status FROM cdt_master_data.agri_commodity_agrochemical AA\n"
            + "inner join cdt_master_data.agri_agrochemical_type AAT on AAT.ID = AA.AgrochemicalTypeID\n"
            + "inner join cdt_master_data.agri_commodity AC on AC.ID = AA.CommodityID order by AA.Name", nativeQuery = true)
    List<AgriAgrochemicalDTO> getAgriAgrochemicalList();

    @Query(value = "select ID,CommodityID,StressTypeID,AgrochemicalTypeID,Name,CIBRCApproved,WaitingPeriod,\n"
            + "UpdatedAt,CreatedAt,status from agri_commodity_agrochemical where CommodityID=?1 and StressTypeID=?2 and status='Active' order by Name asc", nativeQuery = true)
    List<AgriCommodityAgrochemical> findAllByStressTypeId(int commodityID, int stressTypeId);

}