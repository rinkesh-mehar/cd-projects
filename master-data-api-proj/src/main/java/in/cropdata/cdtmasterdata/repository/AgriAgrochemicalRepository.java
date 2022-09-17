package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.AgriAgrochemicalDTO;
import in.cropdata.cdtmasterdata.dto.interfaces.AgriAgrochemicalInfDto;
import in.cropdata.cdtmasterdata.model.AgriAgrochemical;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface AgriAgrochemicalRepository extends JpaRepository<AgriAgrochemical, Integer> {

    @Query(value = "select ID, Name, Status from agri_agrochemical\n" +
            "where Name like :searchText\n" +
            "OR ID like :searchText",
            countQuery = "select ID, Name, Status from agri_agrochemical\n" +
                    "where Name like :searchText\n" +
                    "OR ID like :searchText", nativeQuery = true)
    Page<AgriAgrochemicalInfDto> getAllAgrochemical(Pageable pageable, String searchText);

    @Query(value = "select ID, Name, Status from cdt_master_data.agri_agrochemical", nativeQuery = true)
    List<AgriAgrochemicalDTO> getAgriAgrochemicalList();

}