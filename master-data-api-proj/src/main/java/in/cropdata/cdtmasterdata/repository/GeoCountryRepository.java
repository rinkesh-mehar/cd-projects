package in.cropdata.cdtmasterdata.repository;

import in.cropdata.cdtmasterdata.dto.interfaces.GeoCountryInfDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import in.cropdata.cdtmasterdata.model.GeoCountry;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeoCountryRepository extends JpaRepository<GeoCountry, Integer> {

	GeoCountry findByCountryCode(int countryCode);

	@Query(value = "select gc.Id, gc.CountryCode, gc.Name from cdt_master_data.geo_country gc"
			+" where gc.CountryCode = ?1 or gc.Name = ?2", nativeQuery = true)
	GeoCountryInfDto existCountry(int countryId, String countryName);

	@Query(value = "SELECT GC.Id, GC.CountryCode, GC.Name,GC.UpdatedAt,GC.CreatedAt,GC.Status FROM cdt_master_data.geo_country GC\n" +
			" ORDER BY  GC.Name ASC", nativeQuery = true)
	List<GeoCountry> getActiveCountry();
	
	@Query(value="select ID,CountryCode,Name,Status from geo_country\n" + 
			"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",countQuery = "select ID,CountryCode,Name,Status from geo_country\n" + 
					"where ID  like :searchText OR Name like :searchText OR  Status like :searchText",nativeQuery = true)
	Page<GeoCountry> getCountryListByPagenation(Pageable sortedByIdDesc, String searchText);

}
