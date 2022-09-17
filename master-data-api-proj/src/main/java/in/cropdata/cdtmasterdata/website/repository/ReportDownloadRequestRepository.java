package in.cropdata.cdtmasterdata.website.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.cdtmasterdata.website.model.ReportDownloadRequest;
import in.cropdata.cdtmasterdata.website.model.vo.ReportDownloadRequestVO;

@Repository
public interface ReportDownloadRequestRepository extends JpaRepository<ReportDownloadRequest, Integer> {
	
	@Query(value = "SELECT \n" + 
			"				    report_download_request.ID, \n" + 
			"				    Upper(platform_master.Name) as Platform, \n" + 
			"				    Upper(report_download_request.Name) as Name,\n" + 
			"				    report_download_request.Email ,\n" + 
			"				    report_download_request.Mobile,\n" + 
			"				    Upper(reports.Title) as Title,\n" + 
			"                    DATE_FORMAT(report_download_request.CreatedAt, '%d-%m-%Y') AS RequestDate\n" + 
			"				FROM\n" + 
			"				    report_download_request report_download_request\n" + 
			"				    inner join reports reports on(report_download_request.ReportID = reports.ID) \n" + 
			"				    inner join cdt_master_data.platform_master platform_master on(reports.PlatformID = platform_master.ID) \n" + 
			"		            left join cdt_master_data.agm_industries agm_industries on(report_download_request.IndustryID = agm_industries.ID)\n" + 
			"		            where platform_master.Name like :searchText\n" + 
			"					    or report_download_request.Name like :searchText\n" + 
			"					    or report_download_request.Email like :searchText\n" + 
			"					    or report_download_request.Mobile like :searchText", countQuery = "SELECT \n" + 
					"				    report_download_request.ID, \n" + 
					"				    Upper(platform_master.Name) as Platform, \n" + 
					"				    Upper(report_download_request.Name) as Name,\n" + 
					"				    report_download_request.Email ,\n" + 
					"				    report_download_request.Mobile,\n" + 
					"				    Upper(reports.Title) as Title,\n" + 
					"                    DATE_FORMAT(report_download_request.CreatedAt, '%d-%m-%Y') AS RequestDate\n" + 
					"				FROM\n" + 
					"				    report_download_request report_download_request\n" + 
					"				    inner join reports reports on(report_download_request.ReportID = reports.ID) \n" + 
					"				    inner join cdt_master_data.platform_master platform_master on(reports.PlatformID = platform_master.ID) \n" + 
					"		            left join cdt_master_data.agm_industries agm_industries on(report_download_request.IndustryID = agm_industries.ID)\n" + 
					"		            where platform_master.Name like :searchText\n" + 
					"					    or report_download_request.Name like :searchText\n" + 
					"					    or report_download_request.Email like :searchText\n" + 
					"					    or report_download_request.Mobile like :searchText", nativeQuery = true)
	Page<ReportDownloadRequestVO> getReportDownloadRequestList(Pageable sortedByIdDesc, String searchText);
	
	@Query(value="SELECT \n" + 
			"		   ceil(count(report_download_request.ID)/200) as totalRecordsCount\n" + 
			"		FROM\n" + 
			"		    report_download_request report_download_request\n" + 
			"		        INNER JOIN\n" + 
			"		    reports reports ON (report_download_request.ReportID = reports.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.platform_master platform_master ON (reports.PlatformID = platform_master.ID)\n" + 
			"            left join cdt_master_data.agm_industries agm_industries on(report_download_request.IndustryID = agm_industries.ID)",nativeQuery = true)
	Integer totalReportDownloadRequestCount();
	
	@Query(value="SELECT\n" + 
			"		    report_download_request.ID,\n" + 
			"		    UPPER(platform_master.Name) AS Platform,\n" + 
			"		    UPPER(report_download_request.Name) AS Name,\n" + 
			"		    report_download_request.Email,\n" + 
			"		    report_download_request.Mobile,\n" + 
			"		    UPPER(reports.Title) AS Title,\n" + 
			"		    UPPER(agm_industries.Name) AS Industry,\n" + 
			"		    UPPER(report_download_request.OtherIndustry) AS OtherIndustry,\n" + 
			"		    UPPER(report_download_request.Organization) AS Organization,\n" + 
			"			DATE_FORMAT(report_download_request.CreatedAt, '%d-%m-%Y') AS RequestDate \n" + 
			"		FROM\n" + 
			"		    report_download_request report_download_request\n" + 
			"		        INNER JOIN\n" + 
			"		    reports reports ON (report_download_request.ReportID = reports.ID)\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.platform_master platform_master ON (reports.PlatformID = platform_master.ID)\n" + 
			"		        left JOIN\n" + 
			"		    cdt_master_data.agm_industries agm_industries ON (report_download_request.IndustryID = agm_industries.ID)", countQuery = "SELECT\n" + 
					"		    report_download_request.ID,\n" + 
					"		    UPPER(platform_master.Name) AS Platform,\n" + 
					"		    UPPER(report_download_request.Name) AS Name,\n" + 
					"		    report_download_request.Email,\n" + 
					"		    report_download_request.Mobile,\n" + 
					"		    UPPER(reports.Title) AS Title,\n" + 
					"		    UPPER(agm_industries.Name) AS Industry,\n" + 
					"		    UPPER(report_download_request.OtherIndustry) AS OtherIndustry,\n" + 
					"		    UPPER(report_download_request.Organization) AS Organization,\n" + 
					"			DATE_FORMAT(report_download_request.CreatedAt, '%d-%m-%Y') AS RequestDate \n" + 
					"		FROM\n" + 
					"		    report_download_request report_download_request\n" + 
					"		        INNER JOIN\n" + 
					"		    reports reports ON (report_download_request.ReportID = reports.ID)\n" + 
					"		        INNER JOIN\n" + 
					"		    cdt_master_data.platform_master platform_master ON (reports.PlatformID = platform_master.ID)\n" + 
					"		        left JOIN\n" + 
					"		    cdt_master_data.agm_industries agm_industries ON (report_download_request.IndustryID = agm_industries.ID)", nativeQuery = true)
	Page<ReportDownloadRequestVO> exportToExcelReportDownloadRequest(Pageable sortedByIdAsc);

}
