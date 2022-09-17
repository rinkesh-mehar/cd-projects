/**
 * 
 */
package in.cropdata.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.portal.dto.ResponseMessage;
import in.cropdata.portal.dto.RlUserInfoDTO;
import in.cropdata.portal.exceptions.InvalidDataException;
import in.cropdata.portal.model.RlUser;
import in.cropdata.portal.service.RlUserService;
import in.cropdata.portal.vo.RlUserInfoVO;

/**
 * This Controller is used for managing the various operations for RL Users.
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

	@GetMapping("/list")
	public List<RlUser> rlList() {
		return this.rlUserService.getRlUserList();
	}

	@GetMapping("/get-status/{id}")
	public RlUserInfoVO getRlUserInfoById(@PathVariable(name = "id", required = true) String userId) {
		if (userId == null) {
			throw new InvalidDataException("User Id can not be null or empty!");
		}

		return this.rlUserService.getRlUserInfoById(userId);
	}

	@PostMapping("/register")
	public ResponseMessage registerRlUser(@ModelAttribute RlUserInfoDTO rlUserInfoDTO) {
		/** validating input data */
		if (rlUserInfoDTO == null) {
			throw new InvalidDataException("User Details can not be null or empty!");
		}
		if (rlUserInfoDTO.getUserId() == null) {
			throw new InvalidDataException("UserId can not be empty!");
		}
		if (rlUserInfoDTO.getFirstName() == null && rlUserInfoDTO.getLastName() == null) {
			throw new InvalidDataException("User First and Last Name can not be empty!");
		}
		if (rlUserInfoDTO.getMobileNo() == null) {
			throw new InvalidDataException("User Mobile Number can not be empty!");
		}
		if (rlUserInfoDTO.getAadharNo() == null) {
			throw new InvalidDataException("User Aadhaar Number can not be empty!");
		}

		return this.rlUserService.registerRlUser(rlUserInfoDTO);
	}

}
