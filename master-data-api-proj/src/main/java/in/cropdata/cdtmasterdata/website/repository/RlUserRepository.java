/**
 * 
 */
package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.cdtmasterdata.website.model.RlUser;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;
import in.cropdata.cdtmasterdata.website.model.vo.RlUserVo;

/**
 * @author vivek-cropdata
 * @author PranaySK
 */

public interface RlUserRepository extends JpaRepository<RlUser, Integer> {

	public Optional<RlUser> findByEmail(String email);

	@Query(value = "SELECT COUNT(ru.ID) from cdt_website.rl_users ru where ru.AadharNo = ?1", nativeQuery = true)
	public Integer checkAadharNo(String aadharNo);

	@Query(value = "select ru.ID, ru.RegionID,ru.RoleName, ru.Name,ru.Email,ru.MobileNumber,\n"
			+ "ru.UserImageUrl,ru.AggrementAccepted,ru.Status from cdt_website.rl_users ru\n"
			+ "inner join cdt_website.rl_user_aggrement rua on rua.RlUserID = ru.ID and rua.Status = ru.Status", nativeQuery = true)
	public List<RlUserVo> gtListOfRlUser();

	@Query(value = "select ru.ID, ru.RegionID, ru.RoleName, ru.Name, ru.Email, ru.MobileNumber,\n" + 
			"						ru.AadharNo, ru.UserImageUrl, ru.Pan as PanNo, ru.AggrementAccepted, ru.Status,GR.Name as RegionName \n" + 
			"			            from cdt_website.rl_users ru\n" + 
			"						inner join cdt_website.rl_user_aggrement rua on rua.RlUserID = ru.ID and rua.Status = ru.Status \n" + 
			"			            inner join cdt_master_data.geo_region GR on (ru.RegionID = GR.RegionID)\n" + 
			"			        LEFT JOIN cdt_master_data.geo_state GS ON (GR.StateCode = GS.StateCode) \n" + 
			"						WHERE ru.ID like :searchText OR ru.RoleName like :searchText OR ru.Name like :searchText OR ru.Email like :searchText\n" + 
			"                        OR ru.MobileNumber like :searchText OR ru.AadharNo like :searchText OR ru.Pan like :searchText\n" + 
			"                        OR ru.Status like :searchText OR ru.RoleName like :searchText OR GR.Name like :searchText OR ru.AggrementAccepted like :searchText", countQuery = "select ru.ID, ru.RegionID, ru.RoleName, ru.Name, ru.Email, ru.MobileNumber,\n" + 
					"						ru.AadharNo, ru.UserImageUrl, ru.Pan as PanNo, ru.AggrementAccepted, ru.Status,GR.Name as RegionName \n" + 
					"			            from cdt_website.rl_users ru\n" + 
					"						inner join cdt_website.rl_user_aggrement rua on rua.RlUserID = ru.ID and rua.Status = ru.Status \n" + 
					"			            inner join cdt_master_data.geo_region GR on (ru.RegionID = GR.RegionID)\n" + 
					"			        LEFT JOIN cdt_master_data.geo_state GS ON (GR.StateCode = GS.StateCode) \n" + 
					"						WHERE ru.ID like :searchText OR ru.RoleName like :searchText OR ru.Name like :searchText OR ru.Email like :searchText\n" + 
					"                        OR ru.MobileNumber like :searchText OR ru.AadharNo like :searchText OR ru.Pan like :searchText\n" + 
					"                        OR ru.Status like :searchText OR ru.RoleName like :searchText OR GR.Name like :searchText OR ru.AggrementAccepted like :searchText", nativeQuery = true)
	Page<RlUserVo> getRlUserWithPage(Pageable sortByIdAsc, String searchText);

	@Query(value = "SELECT COUNT(gr.RegionID) FROM cdt_master_data.geo_region gr where gr.RegionID = ?1", nativeQuery = true)
	int existRegionId(Integer regionId);

	@Query(value = "select ru.ID, ru.AadharImageUrl, ru.AadharNo, ru.AggrementAccepted, ru.DrivingLicenseImageUrl, ru.DrkID, ru.Email, ru.MobileNumber, \n"
			+ "ru.Name, ru.Pan as PanNo, ru.PanImageUrl, ru.RegionID, ru.RoleName, ru.Status, ru.UserImageUrl, rbd.BankName, rbd.AccountNumber, rbd.IFSCCode, rbd.BankImageUrl \n"
			+ "from cdt_website.rl_users ru inner join cdt_website.rl_bank_details rbd on rbd.RlUserID = ru.ID \n"
			+ "where ru.ID = ?1", nativeQuery = true)
	public RlUserVo getRlUserInfo(Integer userId);

	@Query(value = "select ru.ID, ru.AadharImageUrl, ru.AadharNo, ru.AggrementAccepted, ru.DrivingLicenseImageUrl, ru.DrkID, ru.Email, ru.MobileNumber,\n" + 
			"			ru.Name, ru.Pan as PanNo, ru.PanImageUrl, ru.RegionID, ru.RoleName, ru.Status, ru.UserImageUrl, rbd.BankName, rbd.AccountNumber, rbd.IFSCCode, rbd.BankImageUrl,GR.Name as RegionName\n" + 
			"			from cdt_website.rl_users ru inner join cdt_website.rl_bank_details rbd on rbd.RlUserID = ru.ID\n" + 
			"			inner join cdt_master_data.geo_region GR on (ru.RegionID = GR.RegionID)\n" + 
			"			LEFT JOIN cdt_master_data.geo_state GS ON (GR.StateCode = GS.StateCode)", countQuery = "select ru.ID, ru.AadharImageUrl, ru.AadharNo, ru.AggrementAccepted, ru.DrivingLicenseImageUrl, ru.DrkID, ru.Email, ru.MobileNumber,\n" + 
					"			ru.Name, ru.Pan as PanNo, ru.PanImageUrl, ru.RegionID, ru.RoleName, ru.Status, ru.UserImageUrl, rbd.BankName, rbd.AccountNumber, rbd.IFSCCode, rbd.BankImageUrl,GR.Name as RegionName\n" + 
					"			from cdt_website.rl_users ru inner join cdt_website.rl_bank_details rbd on rbd.RlUserID = ru.ID\n" + 
					"			inner join cdt_master_data.geo_region GR on (ru.RegionID = GR.RegionID)\n" + 
					"			LEFT JOIN cdt_master_data.geo_state GS ON (GR.StateCode = GS.StateCode)", nativeQuery = true)
	public Page<RlUserVo> getRlUserDataToExport(Pageable sortedByIdDesc);

	@Query(value = "select CEIL(COUNT(ru.ID) / 200) as totalRecordsCount "
			+ "from cdt_website.rl_users ru inner join cdt_website.rl_bank_details rbd on rbd.RlUserID = ru.ID", nativeQuery = true)
	public Integer totalNoOfPages();
	
	@Query(value = "SELECT \n" + 
			"    GR.RegionID, CONCAT(GR.RegionID, '-', GR.Name) AS Name\n" + 
			"FROM\n" + 
			"    cdt_master_data.geo_region GR\n" + 
			"        LEFT JOIN\n" + 
			"    cdt_master_data.geo_state GS ON (GR.StateCode = GS.StateCode) order by GR.RegionID", nativeQuery = true)
	List<GeoRegionDto> getGeoRegionList();

}
