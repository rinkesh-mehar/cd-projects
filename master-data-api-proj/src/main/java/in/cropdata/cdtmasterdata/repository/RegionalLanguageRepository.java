package in.cropdata.cdtmasterdata.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.dto.interfaces.RegionalLanguageDtoInf;
import in.cropdata.cdtmasterdata.model.RegionalLanguage;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionalLanguageRepository extends JpaRepository<RegionalLanguage, Integer> {

	@Query(value = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language, RL.IsValid, RL.ErrorMessage\n" +
			"			from regional_language RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" + 
			"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" + 
			"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" + 
			"			where FL.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Name like :searchText",
			countQuery = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language, RL.IsValid, RL.ErrorMessage\n" +
					"			from regional_language RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" + 
					"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" + 
					"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" + 
					"			where FL.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Name like :searchText", nativeQuery = true)
	Page<RegionalLanguageDtoInf> getRegionalLanguage(Pageable pageable, String searchText);
	
	@Query(value = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language\n" + 
			"			from regional_language_missing RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" + 
			"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" + 
			"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" + 
			"			where FL.Name like :searchText\n" + 
			"			OR GS.Name like :searchText\n" + 
			"			OR GR.Name like :searchText",
			countQuery = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language\n" + 
					"			from regional_language_missing RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" + 
					"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" + 
					"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" + 
					"			where FL.Name like :searchText\n" + 
					"			OR GS.Name like :searchText\n" + 
					"			OR GR.Name like :searchText", nativeQuery = true)
	Page<RegionalLanguageDtoInf> getRegionalLanguageMissing(Pageable pageable, String searchText);

	@Query(value = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language, RL.IsValid, RL.ErrorMessage\n" +
			"			from regional_language RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" +
			"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" +
			"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" +
			"				where RL.IsValid = 0 and( FL.Name like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GR.Name like :searchText)",
			countQuery = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language, RL.IsValid, RL.ErrorMessage\n" +
					"			from regional_language RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" +
					"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" +
					"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" +
					"			where RL.IsValid = 0 and(FL.Name like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GR.Name like :searchText)", nativeQuery = true)
	Page<RegionalLanguageDtoInf> getRegionalLanguageInvalidated(Pageable pageable, String searchText);
	
	@Query(value = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language, RL.IsValid \n" +
			"			from regional_language_missing RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" +
			"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" +
			"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" +
			"				where RL.IsValid = 0 and( FL.Name like :searchText\n" +
			"			OR GS.Name like :searchText\n" +
			"			OR GR.Name like :searchText)",
			countQuery = "select RL.ID,RL.StateCode,RL.RegionID,RL.LanguageID,RL.Status,RL.CreatedAt,RL.UpdatedAt, GS.Name as state, GR.Name as region, FL.Name as language, RL.IsValid \n" +
					"			from regional_language_missing RL left join geo_state GS on (RL.StateCode = GS.StateCode) \n" +
					"			left join geo_region GR on (RL.RegionID = GR.RegionID)\n" +
					"			left join farmer_language FL on (RL.LanguageID = FL.ID)\n" +
					"			where RL.IsValid = 0 and(FL.Name like :searchText\n" +
					"			OR GS.Name like :searchText\n" +
					"			OR GR.Name like :searchText)", nativeQuery = true)
	Page<RegionalLanguageDtoInf> getRegionalLanguageMissingInvalidated(Pageable pageable, String searchText);

	Optional<RegionalLanguage> findByStateCodeAndLanguageId(int stateCode, int languageId);
}



