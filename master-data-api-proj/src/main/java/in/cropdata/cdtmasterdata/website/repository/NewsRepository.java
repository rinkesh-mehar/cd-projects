package in.cropdata.cdtmasterdata.website.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import in.cropdata.cdtmasterdata.website.dto.NewsDataDto;
import in.cropdata.cdtmasterdata.website.model.News;

/**
 * Repository class for interaction with database.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {

	@Query(value = "SELECT n.ID, upper((case n.LatestNews when 'Yes' then concat(pm.Name, ' (LatestNews)') else pm.Name end)) platForm, \n" + 
			"						upper(n.Subject) as Subject, n.Description, n.ExternalUrl, n.ImageUrl, DATE_FORMAT(n.PublishedDate, '%d-%m-%Y') as PublishedDate,n.Source imageSource, n.Status \n" + 
			"						FROM cdt_website.news n INNER JOIN cdt_master_data.platform_master pm on pm.ID = n.PlatformID \n" + 
			"						WHERE pm.name like :searchText OR n.Subject like :searchText\n" + 
			"						OR n.PublishedDate like :searchText", countQuery = "SELECT n.ID, upper((case n.LatestNews when 'Yes' then concat(pm.Name, ' (LatestNews)') else pm.Name end)) platForm, \n" + 
					"						upper(n.Subject) as Subject, n.Description, n.ExternalUrl, n.ImageUrl, DATE_FORMAT(n.PublishedDate, '%d-%m-%Y') as PublishedDate,n.Source imageSource, n.Status \n" + 
					"						FROM cdt_website.news n INNER JOIN cdt_master_data.platform_master pm on pm.ID = n.PlatformID \n" + 
					"						WHERE pm.name like :searchText OR n.Subject like :searchText\n" + 
					"						OR n.PublishedDate like :searchText" + 
					"			OR n.PublishedDate like :searchText", nativeQuery = true)
	Page<NewsDataDto> findAllWithSearch(Pageable sortedByPublishedDateDesc, String searchText);

/*	@Query(value = "SELECT ID, Platform, Title, FileUrl FROM cdt_website.reports"
			+ "WHERE Platform like :searchText OR Title like :searchText", countQuery = "SELECT ID, Platform, Title, FileUrl FROM cdt_website.reports"
			+ "WHERE Platform like :searchText OR Title like :searchText", nativeQuery = true)*/
	
	@Modifying
    @Transactional
	@Query(value="update news set Status = 'Inactive'\n" + 
			"where id = ?1",nativeQuery = true)
	int deactiveNews(Integer id);
	
	@Modifying
    @Transactional
	@Query(value="update news set Status = 'Active'\n" + 
			"where id = ?1",nativeQuery = true)
	int activeNews(Integer id);
	
	@Query(value = "SELECT \n" + 
			"		    n.ID, \n" + 
			"		    UPPER(n.Subject) AS Subject,\n" + 
			"		    n.ExternalUrl,\n" + 
			"		    n.ImageUrl,\n" + 
			"		    date_format(n.PublishedDate, '%d-%m-%Y') as PublishedDate,\n" + 
			"		    n.Status,\n" + 
			"		    n.Priority\n" + 
			"		FROM\n" + 
			"		    cdt_website.news n\n" + 
			"		        INNER JOIN\n" + 
			"		    cdt_master_data.platform_master pm ON pm.ID = n.PlatformID\n" + 
			"		WHERE \n" + 
			"		    n.PlatformID = 5 AND n.LatestNews = 'No'\n" + 
			"		    order by Priority", nativeQuery = true)
	List<NewsDataDto> cropdataPriorityNewsList();
	
	@Query(value = "SELECT \n" + 
			"		    n.ID,\n" + 
			"		    UPPER(n.Subject) AS Subject,\n" + 
			"		    n.ExternalUrl,\n" + 
			"		    n.ImageUrl,\n" + 
			"		    date_format(n.PublishedDate, '%d-%m-%Y') as PublishedDate,\n" + 
			"		    n.Status,\n" + 
			"		    n.Priority\n" + 
			"		FROM\n" + 
			"		    cdt_website.news n\n" + 
			"		        INNER JOIN \n" + 
			"		    cdt_master_data.platform_master pm ON pm.ID = n.PlatformID\n" + 
			"		WHERE \n" + 
			"		    n.PlatformID = 5\n" + 
			"		        AND n.LatestNews = 'Yes'\n" + 
			"		ORDER BY Priority", nativeQuery = true)
	List<NewsDataDto> cropdataLatestNewsPriorityList();
	
	@Query(value = "SELECT max(n.Priority)\n" + 
			"FROM\n" + 
			"    cdt_website.news n\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.platform_master pm ON pm.ID = n.PlatformID\n" + 
			"WHERE\n" + 
			"    n.PlatformID = 5 AND n.LatestNews = 'No'", nativeQuery = true)
	Integer getCropdataNewsMaxPriority();
	
	@Query(value = "SELECT max(n.Priority)\n" + 
			"FROM\n" + 
			"    cdt_website.news n\n" + 
			"        INNER JOIN\n" + 
			"    cdt_master_data.platform_master pm ON pm.ID = n.PlatformID\n" + 
			"WHERE\n" + 
			"    n.PlatformID = 5\n" + 
			"        AND n.LatestNews = 'Yes'", nativeQuery = true)
	Integer getCropdataLatestNewsMaxPriority();
	
	@Modifying
    @Transactional
	@Query(value="update news news set news.Priority = (news.Priority + 1)\n" + 
			"where news.PlatformID = 5 and news.LatestNews = 'Yes'",nativeQuery = true)
	int modifyCropdataLatestNewsPriority();
	
	@Modifying
    @Transactional
	@Query(value="update news news set news.Priority = (news.Priority - 1)\n" + 
			"			where news.PlatformID = 5 and news.LatestNews = 'No'\n" + 
			"            And news.Priority > ?1",nativeQuery = true)
	int rearrangeCropdataNewsPriority(Integer priority);
	
	@Modifying
    @Transactional
	@Query(value="update news news set news.Priority = (news.Priority + 1)\n" + 
			"where news.PlatformID = 5 and news.LatestNews = 'No'",nativeQuery = true)
	int modifyCropdataNewsPriority();
	
	@Modifying
    @Transactional
	@Query(value="update news news set news.Priority = (news.Priority - 1)\n" + 
			"			where news.PlatformID = 5 and news.LatestNews = 'Yes'\n" + 
			"            And news.Priority > ?1",nativeQuery = true)
	int rearrangeCropdataLatestNewsPriority(Integer priority);
}
