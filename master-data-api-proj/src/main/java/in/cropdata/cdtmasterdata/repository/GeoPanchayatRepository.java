package in.cropdata.cdtmasterdata.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.GeoPanchayatInfDto;
import in.cropdata.cdtmasterdata.model.GeoPanchayat;

public interface GeoPanchayatRepository extends JpaRepository<GeoPanchayat, Integer> {

	List<GeoPanchayat> findByStateCode(int stateCode);

	List<GeoPanchayat> findByDistrictCode(int districtCode);

	List<GeoPanchayat> findByTehsilCode(int tehsilCode);

	GeoPanchayat findByPanchayatCode(int panchayatCode);

	@Query(value = "SELECT GP.ID,GP.StateCode,GP.DistrictCode,GP.TehsilCode,GP.PanchayatCode,\n"
			+ "GP.Name,GP.CreatedAt,GP.UpdatedAt,GP.Status,GS.Name as State,\n"
			+ "GD.Name as District,GT.Name as Tehsil\n" + "FROM geo_panchayat GP \n"
			+ "LEFT JOIN geo_state GS ON (GP.StateCode = GS.StateCode)\n"
			+ "LEFT JOIN geo_district GD ON (GP.DistrictCode = GD.DistrictCode)\n"
			+ "LEFT JOIN geo_tehsil GT ON (GP.TehsilCode = GT.TehsilCode)", nativeQuery = true)
	List<GeoPanchayatInfDto> getGeoPanchayatList();

	@Query(value = "SELECT GP.ID,GP.StateCode,GP.DistrictCode,GP.TehsilCode,GP.PanchayatCode,\n" +
			"			GP.Name,GP.Status,GS.Name as State,\n" +
			"			GD.Name as District,GT.Name as Tehsil FROM geo_panchayat GP \n" +
			"			LEFT JOIN geo_state GS ON (GP.StateCode = GS.StateCode)\n" +
			"			LEFT JOIN geo_district GD ON (GP.DistrictCode = GD.DistrictCode)\n" +
			"			LEFT JOIN geo_tehsil GT ON (GP.TehsilCode = GT.TehsilCode)\n" +
			"			where GP.Name like :searchText\n" +
			"			OR GP.PanchayatCode like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GD.Name like :searchText\n" +
			"			OR GT.Name like :searchText",
			countQuery = "SELECT count(GP.ID) as Count \n" +
					"			FROM geo_panchayat GP \n" +
					"			LEFT JOIN geo_state GS ON (GP.StateCode = GS.StateCode)\n" +
					"			LEFT JOIN geo_district GD ON (GP.DistrictCode = GD.DistrictCode)\n" +
					"			LEFT JOIN geo_tehsil GT ON (GP.TehsilCode = GT.TehsilCode)\n" +
					"			where GP.Name like :searchText\n" +
					"			OR GP.PanchayatCode like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GD.Name like :searchText\n" +
					"			OR GT.Name like :searchText", nativeQuery = true)
	Page<GeoPanchayatInfDto> getGeoPanchayatList(Pageable pageable,String searchText);

}
