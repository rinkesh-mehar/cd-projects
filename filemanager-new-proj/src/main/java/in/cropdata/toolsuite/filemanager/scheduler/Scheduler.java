package in.cropdata.toolsuite.filemanager.scheduler;
//package in.cropdata.toolsuite.filemanager.scheduler;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.List;
//
//import org.apache.commons.lang.time.DateUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import in.cropdata.toolsuite.filemanager.hdfs.HdfsApi;
//import in.cropdata.toolsuite.filemanager.model.TempDirectory;
//import in.cropdata.toolsuite.filemanager.repository.TempDirRepository;
//
//@Component
//public class Scheduler {
//
//    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
//
//    @Autowired
//    private TempDirRepository tempDirRepository;
//
//    @Autowired
//    private HdfsApi hdfsApi;
//
//    @Scheduled(cron = "0 0 0 * * *")//0 0 0 * * *
//    public void cronJobRemoveTempDirs() {
//	Date now = new Date();
//	Date minus12HoursDate = DateUtils.addHours(now, -12);
//	List<TempDirectory> dirToDelete = tempDirRepository.findAllByAddedOnLessThanEqual(minus12HoursDate);
//	if (dirToDelete != null) {
//	    for (TempDirectory tempDirectory : dirToDelete) {
//		boolean recursive = true;
//		boolean skiptrash = true;
//		try {
//		    hdfsApi.rmdir(tempDirectory.getDirPath(), recursive, skiptrash);
//		    logger.info("Removed Temp Dir : "+tempDirectory.getDirPath());
//		} catch (IOException | InterruptedException e) {
//		    logger.error("Temp Directory Deleting Failed :", e);
//		}
//	    }//for
//	} // if
//    }//cronJobRemoveTempDirs
//}