/**
 * 
 */
package in.cropdata.portal.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.cropdata.portal.model.RlUser;
import in.cropdata.portal.vo.RlUserInfoVO;

/**
 * For handling RL user operations.
 * 
 * @author Vivek Gajbhiye
 * @author PranaySK
 */

public interface RlUserRepository extends JpaRepository<RlUser, Integer> {

	@Query(value = "SELECT count(ru.ID) FROM cdt_website.rl_users ru \n"
			+ "inner join cdt_website.rl_user_aggrement rua on rua.RlUserID = ru.ID and rua.Status = ru.Status \n"
			+ "WHERE ru.ID = ?1 and ru.Status = ?2 ", nativeQuery = true)
	public Integer checkUserExists(Integer userId, String status);

	@Query(value = "SELECT ru.ID, ru.RegionID, gr.Name as RegionName, (CASE when ru.Status = 'Expired' then null ELSE rad.DocumentUrl end) DocumentUrl, ru.Status \n"
			+ "FROM cdt_website.rl_users ru inner join cdt_website.rl_user_aggrement rua on rua.RlUserID = ru.ID and rua.Status = ru.Status \n"
			+ "inner join cdt_website.rl_aggrement_document rad on rad.ID = rua.AggrementDocumentID \n"
			+ "inner join cdt_master_data.geo_region gr on gr.RegionID = ru.RegionID "
			+ "WHERE ru.ID = ?1 and ru.Status = ?2 ", nativeQuery = true)
	public RlUserInfoVO getUserInfoByUserIdAndStatus(Integer userId, String status);

	public Optional<RlUser> findByIdAndStatus(Integer userId, String status);

	@Query(value = "SELECT COUNT(ru.ID) from cdt_website.rl_users ru where ru.Email = ?1", nativeQuery = true)
	public Integer checkEmail(String email);

	@Query(value = "SELECT COUNT(ru.ID) from cdt_website.rl_users ru where ru.AadharNo = ?1", nativeQuery = true)
	public Integer checkAadharNo(String aadharNo);

}
