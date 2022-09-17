package in.cropdata.portal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.cropdata.portal.model.News;
import in.cropdata.portal.vo.NewsVO;

import java.util.List;

/**
 * Repository class for interaction with database.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

	@Query(value = "SELECT n.ID,n.PlatformID , pm.name Platform, n.LatestNews , n.Subject title, n.Description, n.ExternalUrl, n.ImageUrl,MONTH(n.PublishedDate) ,DATE(n.PublishedDate), date_format(n.PublishedDate, '%d-%b-%y') publishedDate, n.Status,\n" + 
			"						  n.Source source, n.Priority FROM cdt_website.news n INNER JOIN cdt_master_data.general_platform_master pm on pm.ID = n.PlatformID\n" + 
			"			              where n.Status = 'Active'\n" + 
			"						  order by month(publishedDate) desc,date(publishedDate) desc,year(publishedDate) ,publishedDate desc", nativeQuery = true )
	List<NewsVO> getNews();

	@Query(value = "SELECT n.ID, pm.name platform, n.Subject, n.Description, n.ExternalUrl, n.ImageUrl, n.PublishedDate, n.Status\n"
			+  "FROM cdt_website.news n INNER JOIN cdt_master_data.general_platform_master pm on pm.ID = n.PlatformID "
			+ "WHERE n.Subject like :searchText OR n.Description like :searchText "
			+ "OR n.PublishedDate like :searchText", countQuery = "SELECT n.ID, pm.name platform, n.Subject, n.Description, "
			+ "n.ExternalUrl, n.ImageUrl, n.PublishedDate, n.Status\n"
			+ "FROM cdt_website.news n INNER JOIN cdt_master_data.general_platform_master pm on pm.ID = n.PlatformID "
			+ "WHERE n.Subject like :searchText OR n.Description like :searchText "
			+ "OR n.PublishedDate like :searchText", nativeQuery = true)
	Page<NewsVO> findAllWithSearch(Pageable sortedByPublishedDateDesc, String searchText);

}
