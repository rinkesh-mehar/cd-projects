/**
 * 
 */
package in.cropdata.cdtmasterdata.website.controller;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.website.dto.RlUserDTO;
import in.cropdata.cdtmasterdata.website.model.RlUser;
import in.cropdata.cdtmasterdata.website.model.vo.GeoRegionDto;
import in.cropdata.cdtmasterdata.website.model.vo.RlUserVo;
import in.cropdata.cdtmasterdata.website.service.RlUserService;

/**
 * This Controller is used for managing the various operations for RL Users and
 * uploading the bulk data for user registration.
 * 
 * @since 1.0
 * @author Vivek Gajbhiye
 * @author PranaySK
 * @Date 02-09-2020
 */

@RestController
@RequestMapping("/site/rluser")
public class RLController {

	@Autowired
	private RlUserService rlUserService;

	@PostMapping("/import-data")
	public ResponseMessage importUserRegistrationExcel(@RequestParam("file") MultipartFile file,
			@RequestParam("roleName") String roleName) throws IOException {
		return this.rlUserService.importUserRegistrationExcel(file, roleName);
	}

	@GetMapping("/get-list")
	public List<RlUserVo> getList() {
		return this.rlUserService.getRlUserList();
	}

	@GetMapping("/list")
	public Page<RlUserVo> rlList(@RequestParam("page") Integer page, @RequestParam("size") Integer size,
			@RequestParam(required = false, defaultValue = "") String searchText) {
		return this.rlUserService.getRlUserListPaginated(page, size, searchText);
	}

	@GetMapping("/get-user/{id}")
	public RlUserVo getRlUserDetail(@PathVariable(name = "id", required = true) Integer userId) {
		if (Objects.isNull(userId)) {
			throw new InvalidDataException("UserId can not be empty!");
		}

		return this.rlUserService.getRlUserDetail(userId);
	}

	@PostMapping("/update-rl/{id}")
	public ResponseMessage updateSelectedRlUser(@PathVariable(name = "id", required = true) Integer userId,
			@ModelAttribute RlUserDTO rlUserDTO) throws IOException {
		if (Objects.isNull(userId)) {
			throw new InvalidDataException("UserId can not be empty!");
		}
		if (Objects.isNull(rlUserDTO) || Objects.isNull(rlUserDTO.getData())) {
			throw new InvalidDataException("User Details can not be empty!");
		}

		return this.rlUserService.updateSelectedRlUser(userId, rlUserDTO);
	}

	@PostMapping("/add-rluser")
	public ResponseMessage addRlUser(@RequestBody RlUser rlUser) {
		if (Objects.isNull(rlUser)) {
			throw new InvalidDataException("User Details can not be empty!");
		}
		if (Objects.isNull(rlUser.getRoleName())) {
			throw new InvalidDataException("RoleName can not be empty!");
		}
		if (Objects.isNull(rlUser.getRegionId())) {
			throw new InvalidDataException("RegionID can not be empty!");
		}
		if (Objects.isNull(rlUser.getName())) {
			throw new InvalidDataException("User name can not be empty!");
		}
		if (Objects.isNull(rlUser.getEmail())) {
			throw new InvalidDataException("User email can not be empty!");
		}

		return this.rlUserService.addRlUser(rlUser);
	}

	@GetMapping("/total-pages")
	public List<Integer> listOfPages() {
		return this.rlUserService.listOfPages();
	}

	/**
	 * Get RL Users data as a downloadable excel file.
	 * 
	 * @param pageNo the page for which the data is required
	 * 
	 * @return ResponseEntity
	 */
	@GetMapping("/download")
	public ResponseEntity<Resource> exportRlDataToExcel(@RequestParam("page") Integer pageNo) {
		if (pageNo == null) {
			throw new InvalidDataException("page can not be null!");
		}

		return this.rlUserService.getRLDataInExcelFile(pageNo);
	}
	
	@GetMapping("/region-list")
	public List<GeoRegionDto> getAllGeoRegion() {
		return rlUserService.getAllGeoRegion();
	}

}
