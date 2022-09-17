package in.cropdata.cdtmasterdata.website.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.ApplicantDetails;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationExportToExcelVO;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationGetByIdVO;
import in.cropdata.cdtmasterdata.website.model.vo.BuyerPreApplicationListVO;


@Repository
public interface ApplicantDetailsRepository extends JpaRepository<ApplicantDetails, Integer> {
	
	@Query(value = "SELECT  alpplicant_details.ID,\n" + 
			"				    UPPER(agm_applicant_type.Name) AS ApplicantType,\n" + 
			"				    UPPER(agm_application_type.Name) AS ApplicationType,\n" + 
			"				    UPPER(applicant_application_details.CompanyName) as CompanyName,\n" + 
			"                    UPPER(alpplicant_details.Name) as Name,\n" + 
			"                    alpplicant_details.TelephoneNumber,\n" + 
			"                    alpplicant_details.MobileNumber,\n" + 
			"                    alpplicant_details.EmailAddress,\n" + 
			"                    UPPER(REPLACE(GROUP_CONCAT(agri_commodity.Name),',',', ')) AS Commodity\n" + 
			"				FROM\n" + 
			"				    cdt_website.alpplicant_details alpplicant_details\n" + 
			"				        LEFT JOIN\n" + 
			"				    cdt_master_data.agm_designation agm_designation ON (alpplicant_details.DesignationID = agm_designation.ID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_master_data.agm_applicant_type agm_applicant_type ON (alpplicant_details.ApplicantTypeID = agm_applicant_type.ID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.applicant_application_details applicant_application_details ON (alpplicant_details.ID = applicant_application_details.ApplicantID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_master_data.agm_application_type agm_application_type ON (applicant_application_details.ApplicationTypeID = agm_application_type.ID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.applicant_commodities_of_interest applicant_commodities_of_interest ON (applicant_application_details.ID = applicant_commodities_of_interest.ApplicantApplicationID)\n" + 
			"				        INNER JOIN \n" + 
			"				    cdt_master_data.agri_commodity agri_commodity ON (applicant_commodities_of_interest.CommodityID = agri_commodity.ID)\n" + 
			"				        INNER JOIN\n" + 
			"				    cdt_website.applicant_other_business_details applicant_other_business_details ON (alpplicant_details.ID = applicant_other_business_details.ApplicantID\n" + 
			"				        AND applicant_application_details.ID = applicant_other_business_details.ApplicationAplicantID)\n" + 
			"				        LEFT JOIN \n" + 
			"				    cdt_master_data.agm_firm_type agm_firm_type ON (applicant_other_business_details.FirmTypeID = agm_firm_type.ID)\n" + 
			"				        LEFT JOIN\n" + 
			"				    cdt_master_data.currency_master currency_master ON (applicant_other_business_details.CurrencyID = currency_master.ID) \n" + 
			"				        LEFT JOIN\n" + 
			"				    cdt_master_data.agm_bussiness_type agm_bussiness_type ON (applicant_other_business_details.BusinessTypeID = agm_bussiness_type.ID)\n" + 
			"				WHERE agm_applicant_type.Name LIKE :searchText\n" + 
			"				        OR agm_application_type.Name LIKE :searchText\n" + 
			"				        OR applicant_application_details.CompanyName LIKE :searchText\n" + 
			"				        OR applicant_application_details.GstNumber LIKE :searchText\n" + 
			"				GROUP BY alpplicant_details.ID ,\n" + 
			"					agm_applicant_type.Name ,\n" + 
			"                    agm_application_type.Name , \n" + 
			"                    applicant_application_details.CompanyName,\n" + 
			"                    alpplicant_details.Name,\n" + 
			"                    alpplicant_details.TelephoneNumber,\n" + 
			"                    alpplicant_details.MobileNumber,\n" + 
			"                    alpplicant_details.EmailAddress", 
			countQuery = "SELECT  alpplicant_details.ID,\n" + 
					"				    UPPER(agm_applicant_type.Name) AS ApplicantType,\n" + 
					"				    UPPER(agm_application_type.Name) AS ApplicationType,\n" + 
					"				    UPPER(applicant_application_details.CompanyName) as CompanyName,\n" + 
					"                    UPPER(alpplicant_details.Name) as Name,\n" + 
					"                    alpplicant_details.TelephoneNumber,\n" + 
					"                    alpplicant_details.MobileNumber,\n" + 
					"                    alpplicant_details.EmailAddress,\n" + 
					"                    UPPER(REPLACE(GROUP_CONCAT(agri_commodity.Name),',',', ')) AS Commodity\n" + 
					"				FROM\n" + 
					"				    cdt_website.alpplicant_details alpplicant_details\n" + 
					"				        LEFT JOIN\n" + 
					"				    cdt_master_data.agm_designation agm_designation ON (alpplicant_details.DesignationID = agm_designation.ID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_master_data.agm_applicant_type agm_applicant_type ON (alpplicant_details.ApplicantTypeID = agm_applicant_type.ID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.applicant_application_details applicant_application_details ON (alpplicant_details.ID = applicant_application_details.ApplicantID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_master_data.agm_application_type agm_application_type ON (applicant_application_details.ApplicationTypeID = agm_application_type.ID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.applicant_commodities_of_interest applicant_commodities_of_interest ON (applicant_application_details.ID = applicant_commodities_of_interest.ApplicantApplicationID)\n" + 
					"				        INNER JOIN \n" + 
					"				    cdt_master_data.agri_commodity agri_commodity ON (applicant_commodities_of_interest.CommodityID = agri_commodity.ID)\n" + 
					"				        INNER JOIN\n" + 
					"				    cdt_website.applicant_other_business_details applicant_other_business_details ON (alpplicant_details.ID = applicant_other_business_details.ApplicantID\n" + 
					"				        AND applicant_application_details.ID = applicant_other_business_details.ApplicationAplicantID)\n" + 
					"				        LEFT JOIN \n" + 
					"				    cdt_master_data.agm_firm_type agm_firm_type ON (applicant_other_business_details.FirmTypeID = agm_firm_type.ID)\n" + 
					"				        LEFT JOIN\n" + 
					"				    cdt_master_data.currency_master currency_master ON (applicant_other_business_details.CurrencyID = currency_master.ID) \n" + 
					"				        LEFT JOIN\n" + 
					"				    cdt_master_data.agm_bussiness_type agm_bussiness_type ON (applicant_other_business_details.BusinessTypeID = agm_bussiness_type.ID)\n" + 
					"				WHERE agm_applicant_type.Name LIKE :searchText\n" + 
					"				        OR agm_application_type.Name LIKE :searchText\n" + 
					"				        OR applicant_application_details.CompanyName LIKE :searchText\n" + 
					"				        OR applicant_application_details.GstNumber LIKE :searchText\n" + 
					"				GROUP BY alpplicant_details.ID ,\n" + 
					"					agm_applicant_type.Name ,\n" + 
					"                    agm_application_type.Name , \n" + 
					"                    applicant_application_details.CompanyName,\n" + 
					"                    alpplicant_details.Name,\n" + 
					"                    alpplicant_details.TelephoneNumber,\n" + 
					"                    alpplicant_details.MobileNumber,\n" + 
					"                    alpplicant_details.EmailAddress", nativeQuery = true)
	Page<BuyerPreApplicationListVO> getBuyerPreApplicationListByPagenation(Pageable sortedByIdDesc, String searchText);
	
	@Query(value = "SELECT \n" + 
			"		    alpplicant_details.Name,\n" + 
			"		    alpplicant_details.DesignationID,\n" + 
			"		    alpplicant_details.OtherDesignationName, \n" + 
			"		    alpplicant_details.TelephoneNumber,\n" + 
			"		    alpplicant_details.MobileNumber,\n" + 
			"		    alpplicant_details.EmailAddress, \n" + 
			"		    alpplicant_details.Nationality, \n" + 
			"		    alpplicant_details.ApplicantTypeID,\n" + 
			"		    alpplicant_details.Website,\n" + 
			"		    applicant_application_details.ID AS ApplicantApplicationId,\n" + 
			"		    applicant_application_details.ApplicationTypeID,\n" + 
			"		    applicant_application_details.CompanyName,\n" + 
			"		    applicant_application_details.DateOfIncorporation,\n" + 
			"		    applicant_application_details.PanNumber,\n" + 
			"		    applicant_application_details.CinNumber,\n" + 
			"		    applicant_application_details.GstNumber,\n" + 
			"		    applicant_application_details.Vat,\n" + 
			"		    applicant_application_details.CompanyRegistrationNumber,\n" + 
			"		    applicant_application_details.Reference,\n" + 
			"		    applicant_application_details.BuildingName,\n" + 
			"		    applicant_application_details.StreetName,\n" + 
			"		    applicant_application_details.PostalCode,\n" + 
			"		    applicant_application_details.Country,\n" + 
			"		    applicant_application_details.State,\n" + 
			"		    applicant_application_details.City, \n" + 
			"		    applicant_other_business_details.NatureOfBusiness,\n" + 
			"		    applicant_other_business_details.CurrencyID,\n" + 
			"		    applicant_other_business_details.BusinessTypeID,\n" + 
			"		    applicant_other_business_details.AverageRevenue,\n" + 
			"		    applicant_other_business_details.NonAgriculturalBusinessName\n" + 
			"		FROM\n" + 
			"		    cdt_website.alpplicant_details alpplicant_details\n" + 
			"		        LEFT JOIN \n" + 
			"		    cdt_master_data.agm_designation agm_designation ON (alpplicant_details.DesignationID = agm_designation.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.agm_applicant_type agm_applicant_type ON (alpplicant_details.ApplicantTypeID = agm_applicant_type.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_website.applicant_application_details applicant_application_details ON (alpplicant_details.ID = applicant_application_details.ApplicantID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.agm_application_type agm_application_type ON (applicant_application_details.ApplicationTypeID = agm_application_type.ID) \n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_website.applicant_other_business_details applicant_other_business_details ON (alpplicant_details.ID = applicant_other_business_details.ApplicantID \n" + 
			"		        AND applicant_application_details.ID = applicant_other_business_details.ApplicationAplicantID)\n" + 
			"		        LEFT JOIN\n" + 
			"		    cdt_master_data.agm_firm_type agm_firm_type ON (applicant_other_business_details.FirmTypeID = agm_firm_type.ID)\n" + 
			"		        LEFT JOIN\n" + 
			"		    cdt_master_data.currency_master currency_master ON (applicant_other_business_details.CurrencyID = currency_master.ID) \n" + 
			"		        LEFT JOIN\n" + 
			"		    cdt_master_data.agm_bussiness_type agm_bussiness_type ON (applicant_other_business_details.BusinessTypeID = agm_bussiness_type.ID)\n" + 
			"		WHERE\n" + 
			"		    alpplicant_details.ID = ?1", nativeQuery = true)
	BuyerPreApplicationGetByIdVO getBuyerPreApplicationById(Integer id);

	@Query(value="SELECT \n" + 
			"		    ceil(COUNT(buyerCount.ID)/200) as totalRecordsCount\n" + 
			"		FROM \n" + 
			"		    (SELECT  \n" + 
			"		        alpplicant_details.ID, \n" + 
			"		            UPPER(REPLACE(GROUP_CONCAT(agri_commodity.Name), ',', ', ')) AS Commodity\n" + 
			"		    FROM\n" + 
			"		        cdt_website.alpplicant_details alpplicant_details \n" + 
			"		    LEFT JOIN cdt_master_data.agm_designation agm_designation ON (alpplicant_details.DesignationID = agm_designation.ID)\n" + 
			"		    INNER JOIN cdt_master_data.agm_applicant_type agm_applicant_type ON (alpplicant_details.ApplicantTypeID = agm_applicant_type.ID) \n" + 
			"		    INNER JOIN cdt_website.applicant_application_details applicant_application_details ON (alpplicant_details.ID = applicant_application_details.ApplicantID)\n" + 
			"		    INNER JOIN cdt_master_data.agm_application_type agm_application_type ON (applicant_application_details.ApplicationTypeID = agm_application_type.ID)\n" + 
			"		    INNER JOIN cdt_website.applicant_commodities_of_interest applicant_commodities_of_interest ON (applicant_application_details.ID = applicant_commodities_of_interest.ApplicantApplicationID)\n" + 
			"		    INNER JOIN cdt_master_data.agri_commodity agri_commodity ON (applicant_commodities_of_interest.CommodityID = agri_commodity.ID)\n" + 
			"		    INNER JOIN cdt_website.applicant_other_business_details applicant_other_business_details ON (alpplicant_details.ID = applicant_other_business_details.ApplicantID \n" + 
			"		        AND applicant_application_details.ID = applicant_other_business_details.ApplicationAplicantID)\n" + 
			"		    LEFT JOIN cdt_master_data.agm_firm_type agm_firm_type ON (applicant_other_business_details.FirmTypeID = agm_firm_type.ID)\n" + 
			"		    LEFT JOIN cdt_master_data.currency_master currency_master ON (applicant_other_business_details.CurrencyID = currency_master.ID) \n" + 
			"		    LEFT JOIN cdt_master_data.agm_bussiness_type agm_bussiness_type ON (applicant_other_business_details.BusinessTypeID = agm_bussiness_type.ID)\n" + 
			"		    GROUP BY alpplicant_details.ID) buyerCount",nativeQuery = true)
	Integer totalPreApplicationCount();
	
	@Query(value="SELECT \n" + 
			"		    alpplicant_details.ID, \n" + 
			"		    agm_application_type.Name AS ApplicationType,\n" + 
			"		    alpplicant_details.Nationality as Origin, \n" + 
			"		    agm_applicant_type.Name AS ApplicantType,\n" + 
			"		    applicant_application_details.Reference,\n" + 
			"		    applicant_application_details.CompanyName,\n" + 
			"		    applicant_other_business_details.NatureOfBusiness,\n" + 
			"            applicant_other_business_details.NonAgriculturalBusinessName,\n" + 
			"		    agm_bussiness_type.Name as PresentBusiness,\n" + 
			"		    applicant_application_details.DateOfIncorporation,\n" + 
			"		    applicant_application_details.PanNumber, \n" + 
			"		    applicant_application_details.CinNumber,\n" + 
			"		    applicant_application_details.GstNumber,\n" + 
			"            applicant_application_details.CompanyRegistrationNumber,\n" + 
			"            applicant_application_details.Vat,\n" + 
			"            concat(currency_master.CurrencyCode,'-',currency_master.CurrencyName) as CurrencyCode,\n" + 
			"            applicant_other_business_details.AverageRevenue,\n" + 
			"            alpplicant_details.Name as ContactPersonName,\n" + 
			"            agm_designation.Name as Designation,\n" + 
			"            alpplicant_details.OtherDesignationName,\n" + 
			"		    alpplicant_details.TelephoneNumber,\n" + 
			"		    alpplicant_details.MobileNumber,\n" + 
			"		    alpplicant_details.EmailAddress,\n" + 
			"		    alpplicant_details.Website,\n" + 
			"		    REPLACE(GROUP_CONCAT(agri_commodity.Name),',',', ') AS Commodity,\n" + 
			"            applicant_application_details.Country,\n" + 
			"            applicant_application_details.State,\n" + 
			"            applicant_application_details.City,\n" + 
			"            applicant_application_details.BuildingName,\n" + 
			"            applicant_application_details.StreetName,\n" + 
			"            applicant_application_details.PostalCode\n" + 
			"		FROM \n" + 
			"		    cdt_website.alpplicant_details alpplicant_details\n" + 
			"		        LEFT JOIN\n" + 
			"		    cdt_master_data.agm_designation agm_designation ON (alpplicant_details.DesignationID = agm_designation.ID) \n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.agm_applicant_type agm_applicant_type ON (alpplicant_details.ApplicantTypeID = agm_applicant_type.ID) \n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_website.applicant_application_details applicant_application_details ON (alpplicant_details.ID = applicant_application_details.ApplicantID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.agm_application_type agm_application_type ON (applicant_application_details.ApplicationTypeID = agm_application_type.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_website.applicant_commodities_of_interest applicant_commodities_of_interest ON (applicant_application_details.ID = applicant_commodities_of_interest.ApplicantApplicationID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.agri_commodity agri_commodity ON (applicant_commodities_of_interest.CommodityID = agri_commodity.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_website.applicant_other_business_details applicant_other_business_details ON (alpplicant_details.ID = applicant_other_business_details.ApplicantID\n" + 
			"		        AND applicant_application_details.ID = applicant_other_business_details.ApplicationAplicantID)\n" + 
			"		        LEFT JOIN\n" + 
			"		    cdt_master_data.agm_firm_type agm_firm_type ON (applicant_other_business_details.FirmTypeID = agm_firm_type.ID)\n" + 
			"		        LEFT JOIN \n" + 
			"		    cdt_master_data.currency_master currency_master ON (applicant_other_business_details.CurrencyID = currency_master.ID)\n" + 
			"		        LEFT JOIN\n" + 
			"		    cdt_master_data.agm_bussiness_type agm_bussiness_type ON (applicant_other_business_details.BusinessTypeID = agm_bussiness_type.ID)\n" + 
			"		GROUP BY alpplicant_details.ID, \n" + 
			"		    agm_application_type.Name,\n" + 
			"		    alpplicant_details.Nationality, \n" + 
			"		    agm_applicant_type.Name,\n" + 
			"		    applicant_application_details.Reference,\n" + 
			"		    applicant_application_details.CompanyName,\n" + 
			"		    applicant_other_business_details.NatureOfBusiness,\n" + 
			"            applicant_other_business_details.NonAgriculturalBusinessName,\n" + 
			"		    agm_bussiness_type.Name,\n" + 
			"		    applicant_application_details.DateOfIncorporation,\n" + 
			"		    applicant_application_details.PanNumber, \n" + 
			"		    applicant_application_details.CinNumber,\n" + 
			"		    applicant_application_details.GstNumber,\n" + 
			"            applicant_application_details.CompanyRegistrationNumber,\n" + 
			"            applicant_application_details.Vat,\n" + 
			"            concat(currency_master.CurrencyCode,'-',currency_master.CurrencyName),\n" + 
			"            applicant_other_business_details.AverageRevenue,\n" + 
			"            alpplicant_details.Name,\n" + 
			"            agm_designation.Name,\n" + 
			"            alpplicant_details.OtherDesignationName,\n" + 
			"		    alpplicant_details.TelephoneNumber,\n" + 
			"		    alpplicant_details.MobileNumber,\n" + 
			"		    alpplicant_details.EmailAddress,\n" + 
			"		    alpplicant_details.Website,\n" + 
			"            applicant_application_details.Country,\n" + 
			"            applicant_application_details.State,\n" + 
			"            applicant_application_details.City,\n" + 
			"            applicant_application_details.BuildingName,\n" + 
			"            applicant_application_details.StreetName,\n" + 
			"            applicant_application_details.PostalCode", countQuery = "SELECT \n" + 
					"		    alpplicant_details.ID, \n" + 
					"		    agm_application_type.Name AS ApplicationType,\n" + 
					"		    alpplicant_details.Nationality as Origin, \n" + 
					"		    agm_applicant_type.Name AS ApplicantType,\n" + 
					"		    applicant_application_details.Reference,\n" + 
					"		    applicant_application_details.CompanyName,\n" + 
					"		    applicant_other_business_details.NatureOfBusiness,\n" + 
					"            applicant_other_business_details.NonAgriculturalBusinessName,\n" + 
					"		    agm_bussiness_type.Name as PresentBusiness,\n" + 
					"		    applicant_application_details.DateOfIncorporation,\n" + 
					"		    applicant_application_details.PanNumber, \n" + 
					"		    applicant_application_details.CinNumber,\n" + 
					"		    applicant_application_details.GstNumber,\n" + 
					"            applicant_application_details.CompanyRegistrationNumber,\n" + 
					"            applicant_application_details.Vat,\n" + 
					"            concat(currency_master.CurrencyCode,'-',currency_master.CurrencyName) as CurrencyCode,\n" + 
					"            applicant_other_business_details.AverageRevenue,\n" + 
					"            alpplicant_details.Name as ContactPersonName,\n" + 
					"            agm_designation.Name as Designation,\n" + 
					"            alpplicant_details.OtherDesignationName,\n" + 
					"		    alpplicant_details.TelephoneNumber,\n" + 
					"		    alpplicant_details.MobileNumber,\n" + 
					"		    alpplicant_details.EmailAddress,\n" + 
					"		    alpplicant_details.Website,\n" + 
					"		    REPLACE(GROUP_CONCAT(agri_commodity.Name),',',', ') AS Commodity,\n" + 
					"            applicant_application_details.Country,\n" + 
					"            applicant_application_details.State,\n" + 
					"            applicant_application_details.City,\n" + 
					"            applicant_application_details.BuildingName,\n" + 
					"            applicant_application_details.StreetName,\n" + 
					"            applicant_application_details.PostalCode\n" + 
					"		FROM \n" + 
					"		    cdt_website.alpplicant_details alpplicant_details\n" + 
					"		        LEFT JOIN\n" + 
					"		    cdt_master_data.agm_designation agm_designation ON (alpplicant_details.DesignationID = agm_designation.ID) \n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_master_data.agm_applicant_type agm_applicant_type ON (alpplicant_details.ApplicantTypeID = agm_applicant_type.ID) \n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_website.applicant_application_details applicant_application_details ON (alpplicant_details.ID = applicant_application_details.ApplicantID)\n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_master_data.agm_application_type agm_application_type ON (applicant_application_details.ApplicationTypeID = agm_application_type.ID)\n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_website.applicant_commodities_of_interest applicant_commodities_of_interest ON (applicant_application_details.ID = applicant_commodities_of_interest.ApplicantApplicationID)\n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_master_data.agri_commodity agri_commodity ON (applicant_commodities_of_interest.CommodityID = agri_commodity.ID)\n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_website.applicant_other_business_details applicant_other_business_details ON (alpplicant_details.ID = applicant_other_business_details.ApplicantID\n" + 
					"		        AND applicant_application_details.ID = applicant_other_business_details.ApplicationAplicantID)\n" + 
					"		        LEFT JOIN\n" + 
					"		    cdt_master_data.agm_firm_type agm_firm_type ON (applicant_other_business_details.FirmTypeID = agm_firm_type.ID)\n" + 
					"		        LEFT JOIN \n" + 
					"		    cdt_master_data.currency_master currency_master ON (applicant_other_business_details.CurrencyID = currency_master.ID)\n" + 
					"		        LEFT JOIN\n" + 
					"		    cdt_master_data.agm_bussiness_type agm_bussiness_type ON (applicant_other_business_details.BusinessTypeID = agm_bussiness_type.ID)\n" + 
					"		GROUP BY alpplicant_details.ID, \n" + 
					"		    agm_application_type.Name,\n" + 
					"		    alpplicant_details.Nationality, \n" + 
					"		    agm_applicant_type.Name,\n" + 
					"		    applicant_application_details.Reference,\n" + 
					"		    applicant_application_details.CompanyName,\n" + 
					"		    applicant_other_business_details.NatureOfBusiness,\n" + 
					"            applicant_other_business_details.NonAgriculturalBusinessName,\n" + 
					"		    agm_bussiness_type.Name,\n" + 
					"		    applicant_application_details.DateOfIncorporation,\n" + 
					"		    applicant_application_details.PanNumber, \n" + 
					"		    applicant_application_details.CinNumber,\n" + 
					"		    applicant_application_details.GstNumber,\n" + 
					"            applicant_application_details.CompanyRegistrationNumber,\n" + 
					"            applicant_application_details.Vat,\n" + 
					"            concat(currency_master.CurrencyCode,'-',currency_master.CurrencyName),\n" + 
					"            applicant_other_business_details.AverageRevenue,\n" + 
					"            alpplicant_details.Name,\n" + 
					"            agm_designation.Name,\n" + 
					"            alpplicant_details.OtherDesignationName,\n" + 
					"		    alpplicant_details.TelephoneNumber,\n" + 
					"		    alpplicant_details.MobileNumber,\n" + 
					"		    alpplicant_details.EmailAddress,\n" + 
					"		    alpplicant_details.Website,\n" + 
					"            applicant_application_details.Country,\n" + 
					"            applicant_application_details.State,\n" + 
					"            applicant_application_details.City,\n" + 
					"            applicant_application_details.BuildingName,\n" + 
					"            applicant_application_details.StreetName,\n" + 
					"            applicant_application_details.PostalCode", nativeQuery = true)
	Page<BuyerPreApplicationExportToExcelVO> exportToExcelPreApplication(Pageable sortedByIdAsc);
	
	@Query(value="SELECT CommodityID FROM cdt_website.applicant_commodities_of_interest where ApplicantApplicationID = ?1",nativeQuery = true)
	Integer[] getCommodityIdsByApplicantApplicationId(Integer applicantApplicationId);
	
	@Query(value="SELECT ID FROM cdt_website.applicant_application_details where ApplicantID = ?1",nativeQuery = true)
	Integer getApplicantApplicationIdByApplicantId(Integer applicantId);
	
}
