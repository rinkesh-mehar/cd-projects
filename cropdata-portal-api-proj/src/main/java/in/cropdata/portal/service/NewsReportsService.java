package in.cropdata.portal.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import in.cropdata.portal.masterData.model.PlatFormMaster;
import in.cropdata.portal.masterData.repository.PlatFormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.portal.constants.APIConstants;
import in.cropdata.portal.dto.ReportDownloadRequestDTO;
import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.model.News;
import in.cropdata.portal.model.ReportDownloadRequest;
import in.cropdata.portal.model.Reports;
import in.cropdata.portal.repository.NewsRepository;
import in.cropdata.portal.repository.ReportDownloadRequestRepository;
import in.cropdata.portal.repository.ReportsRepository;
import in.cropdata.portal.util.ResponseMessageUtil;
import in.cropdata.portal.vo.NewsVO;
import in.cropdata.portal.vo.ReportsVO;

import javax.xml.crypto.Data;

/**
 * Service to process the data received from request and perform necessary
 * operations.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Service
public class NewsReportsService {

	private static final Logger LOGGER = LoggerFactory.getLogger(NewsReportsService.class);

	@Autowired
	private NewsRepository newsRepository;

	@Autowired
	private ReportsRepository reportsRepository;

	@Autowired
	private ReportDownloadRequestRepository reportDownloadRequestRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	private static final String SERVER_ERROR_MSG = "Server Error : ";

	public Map<String, List<News>> getAllNews() {
		try {
			LOGGER.info("getting all news info...");
			List<NewsVO> newsList = newsRepository.getNews();

			return this.getNewsRecords(newsList);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No News Data Found!");
		}
	}

	/** read news Records */
	public Map<String, List<News>> getNewsRecords(List<NewsVO> newsList){
		Map<String, List<News>> newMap = new HashMap<>();
		for(NewsVO n : newsList){
			News news = new News();
			BeanUtils.copyProperties(n, news);
			String externalUrl = news.getExternalUrl().replace("http:", "");

			news.setExternalUrl(externalUrl);
			LOGGER.info("after removed---------> {}", externalUrl);
			String platFormName = news.getPlatform().replace(". ","");
			LOGGER.info("Before match latest news-----------> {}",platFormName);
			if(news.getPlatform().equals("CropData") && news.getLatestNews().equals("Yes")){
				String latestDate = news.getPublishedDate().replace("-", " ");
				news.setPublishedDate(latestDate.substring(3,9));
				platFormName = "LatestNews";
				LOGGER.info("After match latest news-----------> {}",platFormName);
				news.setDate(latestDate.substring(0,2));
			}
            LocalDate now = LocalDate.now();
            LOGGER.info("today's date -> {}", now);
            String date = DateTimeFormatter.ofPattern("dd-MMM-yy").format(now.minus(180, ChronoUnit.DAYS));
            LOGGER.info("date is -> {}", date);
            LocalDate sixMonthDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MMM-yy"));
            LOGGER.info("input date -> {}", sixMonthDate);

			if(newMap.containsKey(platFormName)){
				news.setPlatform(platFormName);
				List<News> list = newMap.get(news.getPlatform());
                filterNewsRecordsBaseOnDate(news, sixMonthDate, list);
//				list.add(news);
			}else{
				news.setPlatform(platFormName);
				List<News> list = new ArrayList<>();

                filterNewsRecordsBaseOnDate(news, sixMonthDate, list);
//				list.add(news);

				newMap.put(news.getPlatform(),list);
			}
		}
		return newMap;
	}

    private void filterNewsRecordsBaseOnDate(News news, LocalDate sixMonthDate, List<News> list)
    {
        if (news.getPlatform().equals("LatestNews")){

            list.add(news);
        } else if (news.getPlatform().equals("CropData")){
            list.add(news);
        } else {
            LocalDate publishDate = LocalDate.parse(news.getPublishedDate(), DateTimeFormatter.ofPattern("dd-MMM-yy"));
            if (publishDate.isAfter(sixMonthDate)){
                list.add(news);
            }
        }
    }

    public Page<NewsVO> getAllNewsPaginated(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all news info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByPublishedDateDesc = PageRequest.of(page, size, Sort.by("PublishedDate").descending());

			return newsRepository.findAllWithSearch(sortedByPublishedDateDesc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No News Data Found For Searched Text -> " + searchText);
		}
	}

	public News getSelectedNewsDetails(Integer newsId) {
		try {
			return newsRepository.findById(newsId).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("News " + APIConstants.RESPONSE_DOES_NOT_EXIST + newsId);
		}
	}

	public Map<String, List<Reports>> getAllReports() {
		try {
			Map<String, List<Reports>> reportMap = new HashMap<>();
			List<ReportsVO> reportList = reportsRepository.getReports();
//			newsList.sort(Comparator.comparing(ReportsVO::getPublishedDate).reversed());
			LOGGER.info("getting all reports info...");
			for(ReportsVO r : reportList){
				Reports reports = new Reports();
				BeanUtils.copyProperties(r, reports);
				String fileUrl = reports.getFileUrl().replace("http:", "");

				reports.setFileUrl(fileUrl);
				LOGGER.info("after removed---------> {}", fileUrl);

				String platFormName = reports.getPlatform().replace(". ","");
				if(reportMap.containsKey(platFormName)){
					reports.setPlatform(platFormName);
					List<Reports> list = reportMap.get(reports.getPlatform());
					list.add(reports);
				}else{

					reports.setPlatform(platFormName);
					List<Reports> list = new ArrayList<>();
					list.add(reports);
					reportMap.put(reports.getPlatform(),list);
				}
			}
			return reportMap;

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Report Data Found!");
		}
	}

	public Page<ReportsVO> getAllReportsPaginated(Integer page, Integer size, String searchText) {
		try {
			LOGGER.info("getting all reports info - paginated...");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").ascending());

			return reportsRepository.findAllWithSearch(sortedByIdAsc, searchText);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Report Data Found For Searched Text -> " + searchText);
		}
	}

	public Reports getSelectedReportDetails(Integer reportId) {
		try {
			return reportsRepository.findById(reportId).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Report " + APIConstants.RESPONSE_DOES_NOT_EXIST + reportId);
		}
	}

	public ResponseMessage downloadReportAndSaveReportRequest(Integer reportId,
			ReportDownloadRequestDTO reportDownloadRequestDto) {
		try {
			ReportDownloadRequest reportDownloadRequest = new ReportDownloadRequest();
			BeanUtils.copyProperties(reportDownloadRequestDto, reportDownloadRequest);

			if (reportsRepository.existsById(reportId)) {

				LOGGER.info("Saving Report Request...");
				reportDownloadRequestRepository.save(reportDownloadRequest);

				Reports reports = reportsRepository.findById(reportId).get();

				return responseMessageUtil.sendResponse(true, reports.getFileUrl(), "");

			} else {
				LOGGER.info("Report Not Exist...");
				return responseMessageUtil.sendResponse(false, "",
						"Report " + APIConstants.RESPONSE_DOES_NOT_EXIST + reportId);
			}
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}

	}

}
