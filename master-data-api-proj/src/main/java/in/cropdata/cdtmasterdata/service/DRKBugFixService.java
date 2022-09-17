package in.cropdata.cdtmasterdata.service;

import in.cropdata.cdtmasterdata.dto.DRKBugDto;
import in.cropdata.cdtmasterdata.properties.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author RinkeshKM
 * @Date 10/12/20
 */

@Service
public class DRKBugFixService {

    private static final Logger logger = LoggerFactory.getLogger(DRKBugFixService.class);

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppProperties appProperties;

    public ResponseEntity<DRKBugDto> fixBug(String tableName) {

        logger.info("table name -> {}", tableName);

        DRKBugDto forObject = restTemplate.getForObject(appProperties.getDrkBugFix().concat("?tableName=").concat(tableName), DRKBugDto.class);
        logger.debug("Api response -> {}", forObject);
        return ResponseEntity.status(HttpStatus.OK).body(forObject);
    }
}
