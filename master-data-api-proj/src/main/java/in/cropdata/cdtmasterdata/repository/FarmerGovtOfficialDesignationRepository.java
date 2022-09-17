package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.FarmerGovtOfficialInfDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.FarmerGovtOfficialDesignation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerGovtOfficialDesignationRepository extends JpaRepository<FarmerGovtOfficialDesignation, Integer>
{
    @Query(value = "SELECT FGOD.ID, FGOD.Name, FGD.Name AS Department, FGOD.Status, FGOD.IsValid, FGOD.ErrorMessage\n" +
            "FROM cdt_master_data.farmer_govt_official_designation FGOD\n" +
            "         LEFT JOIN cdt_master_data.farmer_govt_department FGD ON (FGOD.DepartmentID = FGD.ID)\n" +
            "WHERE FGOD.Name LIKE :searchText\n" +
            "   OR FGD.Name LIKE :searchText\n" +
            "   OR FGOD.Status LIKE :searchText",
            countQuery = "SELECT FGOD.ID, FGOD.Name, FGD.Name AS Department, FGOD.Status, FGOD.IsValid, FGOD.ErrorMessage\n" +
                    "FROM cdt_master_data.farmer_govt_official_designation FGOD\n" +
                    "         LEFT JOIN cdt_master_data.farmer_govt_department FGD ON (FGOD.DepartmentID = FGD.ID)\n" +
                    "WHERE FGOD.Name LIKE :searchText\n" +
                    "   OR FGD.Name LIKE :searchText\n" +
                    "   OR FGOD.Status LIKE :searchText", nativeQuery = true)
    Page<FarmerGovtOfficialInfDto> findAllWithSearch(Pageable sortByIdASC, String searchText);
    @Query(value = "SELECT FGOD.ID, FGOD.Name, FGD.Name AS Department, FGOD.Status, FGOD.IsValid, FGOD.ErrorMessage\n" +
            "FROM cdt_master_data.farmer_govt_official_designation FGOD\n" +
            "         LEFT JOIN cdt_master_data.farmer_govt_department FGD ON (FGOD.DepartmentID = FGD.ID)\n" +
            "where FGOD.IsValid = 0 and (FGOD.Name LIKE :searchText\n" +
            "   OR FGD.Name LIKE :searchText\n" +
            "   OR FGOD.Status LIKE :searchText)",
            countQuery = "SELECT FGOD.ID, FGOD.Name, FGD.Name AS Department, FGOD.Status, FGOD.IsValid, FGOD.ErrorMessage\n" +
                    "FROM cdt_master_data.farmer_govt_official_designation FGOD\n" +
                    "         LEFT JOIN cdt_master_data.farmer_govt_department FGD ON (FGOD.DepartmentID = FGD.ID)\n" +
                    "   where FGOD.IsValid = 0 and( FGOD.Name LIKE :searchText\n" +
                    "   OR FGD.Name LIKE :searchText\n" +
                    "   OR FGOD.Status LIKE :searchText)", nativeQuery = true)
    Page<FarmerGovtOfficialInfDto> findAllWithSearchInvalidated(Pageable sortByIdASC, String searchText);
}
