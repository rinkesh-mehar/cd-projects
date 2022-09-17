package in.cropdata.cdtmasterdata.region.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.region.model.SourceModel;
import in.cropdata.cdtmasterdata.region.service.SourceService;
import in.cropdata.cdtmasterdata.region.vo.SourceVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.controller
 * @date 18/01/21
 * @time 12:20 PM
 */
@RestController
@RequestMapping("/source")
//@CrossOrigin("*")
public class SourceController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SourceController.class);
    @Autowired
    private SourceService sourceService;

    @GetMapping("/list")
    public Page<SourceVO> getSourceList(@RequestParam("page") int page,
                                        @RequestParam("size") int size,
                                        @RequestParam(required = false, defaultValue = "") String searchText)
    {

        LOGGER.info("page is-----------> {}", page);
        return sourceService.getSourceList(page, size, searchText);
    }

    @PostMapping("/store")
    public ResponseMessage storeSource(@RequestBody SourceModel sourceModel)
    {
        return sourceService.storeSource(sourceModel);
    }

    @GetMapping("/{id}")
    public Optional<SourceModel> getSourceDetailsById(@PathVariable("id") Integer id)
    {
        return sourceService.getSourceById(id);
    }

    @PostMapping("/update/{id}")
    public ResponseMessage updateSourceDetails(@PathVariable("id") Integer id,
                                               @RequestBody SourceModel sourceModel)
    {
        return sourceService.updateSourceDetails(id, sourceModel);
    }

    @GetMapping("/cluster/list")
    public List<SourceModel> getSourceList()
    {
        return sourceService.getSourceList();
    }

}
