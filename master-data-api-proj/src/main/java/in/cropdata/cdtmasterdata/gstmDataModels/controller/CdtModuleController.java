package in.cropdata.cdtmasterdata.gstmDataModels.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.gstmDataModels.dto.CdtModuleDto;
import in.cropdata.cdtmasterdata.gstmDataModels.model.CdtManage;
import in.cropdata.cdtmasterdata.gstmDataModels.model.Model;
import in.cropdata.cdtmasterdata.gstmDataModels.service.CdtModuleService;
import in.cropdata.cdtmasterdata.gstmDataModels.vo.CdtManageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.controller
 * @date 29/07/20
 * @time 7:47 PM
 * To change this template use File | Settings | File and Code Templates
 */
@RestController
@RequestMapping("/modules")
public class CdtModuleController
{
    private static final Logger LOGGER = LoggerFactory.getLogger(CdtModuleController.class);

    @Autowired
    CdtModuleService cdtModuleService;

    @GetMapping("/list")
    public Map<String, Object> getModules(){
        return cdtModuleService.getModels();
    }

    @GetMapping("/model/list")
    public List<Model> getAllModel(){

        return cdtModuleService.getAllModel();
    }
    
	@GetMapping("/paginatedList")
	public Page<Model> getDepartmentListByPagenation(@RequestParam("page") Integer page,
			@RequestParam("size") Integer size, @RequestParam(required = false, defaultValue = "") String searchText) {
		
		if (page == null || size == null) {
			throw new InvalidDataException("page or size can not be null!");
		}

		return cdtModuleService.getModelListByPagenation(page, size, searchText);
	}

    @GetMapping("/manage/list")
    public List<CdtModuleDto> getManageList(){
       return cdtModuleService.getListOfManage();
    }

    @GetMapping("/manage")
    public Page<CdtModuleDto> getPageableManageList(@RequestParam("page") int page,
                                                    @RequestParam("size") int size,
                                                    @RequestParam(required = false, defaultValue = "") String searchText){
        return cdtModuleService.getPageableList(page, size, searchText);
    }

    @PostMapping("/manage/add")
    public ResponseMessage storeManage(@ModelAttribute CdtManageVo cdtModuleManageVo) throws IOException
    {

        return cdtModuleService.storeManage(cdtModuleManageVo);
    }

    @PutMapping("/delete/{id}")
    public ResponseMessage deleteRecord( @PathVariable("id") int id){
        return cdtModuleService.updateRecordById(id);
    }

    @GetMapping("/manage/{id}")
    public Optional<CdtManage> getReocrdById(@PathVariable(name = "id", required = true) Integer id){
        return cdtModuleService.getRecordById(id);
    }

    @PutMapping("update/{id}")
    public ResponseMessage updateRecord(@PathVariable("id") int id,
                                        @ModelAttribute CdtManageVo cdtModuleManageVo) throws IOException
    {
        return cdtModuleService.updateRecord(id, cdtModuleManageVo);
    }

    @PostMapping("/model/add")
    public ResponseMessage storeModel(@ModelAttribute CdtManageVo cdtModelVo) throws IOException{

        return cdtModuleService.storeModel(cdtModelVo);
    }

    @GetMapping("/model/template")
    public Optional<Model> getTemplate(@RequestParam Integer id){
        return cdtModuleService.getTemplateByModelId(id);
    }

}
