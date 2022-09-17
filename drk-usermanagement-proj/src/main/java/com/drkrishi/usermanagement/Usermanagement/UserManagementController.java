package com.drkrishi.usermanagement.Usermanagement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.drkrishi.usermanagement.jwtsecurity.model.JwtUser;
import com.drkrishi.usermanagement.jwtsecurity.security.JwtGenerator;
import com.drkrishi.usermanagement.model.ActiveUserModel;
import com.drkrishi.usermanagement.model.ChangePasswordModel;
import com.drkrishi.usermanagement.model.CreateUserModel;
import com.drkrishi.usermanagement.model.ForgotPasswordModel;
import com.drkrishi.usermanagement.model.LoginModel;
import com.drkrishi.usermanagement.model.ResponseMessage;
import com.drkrishi.usermanagement.model.UpdateUserModel;
import com.drkrishi.usermanagement.model.UserModel;
import com.drkrishi.usermanagement.model.ViewUserModel;
import com.drkrishi.usermanagement.service.UserManagementService;

import io.swagger.annotations.ApiOperation;


/**
 * @author janardhan
 *
 */
@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController()
@ComponentScan({ "com.drkrishi.usermanagement.service", "com.drkrishi.usermanagement.dao"})
//@RequestMapping("/usermanagement-dev/")
public class UserManagementController {

	@Autowired
	private UserManagementService userManagement;

	@Value( "${user.jwtTokenUrl}" )
	private String jwtTokenUrl;
	
	
	private JwtGenerator jwtGenerator;

    public UserManagementController(JwtGenerator jwtGenerator) {
			this.jwtGenerator = jwtGenerator;
    }

    @PostMapping(value="token")
    public String generate(@RequestBody final JwtUser jwtUser) {
        return jwtGenerator.generate(jwtUser);
    }
    
    @ApiOperation(value = "Login for users")
	@RequestMapping(path = "login", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage login(@RequestBody LoginModel userModel ) {
		return userManagement.loginService(userModel.getMobileNumber(), userModel.getUserPassword().trim(), userModel.getChannel(), jwtTokenUrl);
	}
	
	@RequestMapping(path = "passwordChange", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage checkOldPassword(@RequestBody ChangePasswordModel changePasswordModel ) {
		return userManagement.changePasswordService(changePasswordModel.getUserId(), changePasswordModel.getOldPassword(), changePasswordModel.getNewPassword());
	}
	
	@RequestMapping(path = "resetPassword", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage resetPassword(@RequestBody ChangePasswordModel changePasswordModel ) {
		return userManagement.resetPasswordService(changePasswordModel.getUserId(), changePasswordModel.getOldPassword(), changePasswordModel.getNewPassword());
	}	

	@RequestMapping(path = "forgotPassword", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage forgotPassword(@RequestBody ForgotPasswordModel forgotpasswordModel ) {
		System.out.println(" forgotPassword ");
		return userManagement.forgotPasswordService( forgotpasswordModel );		
	}
	
	@RequestMapping(path = "createuser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage createUser(@RequestBody CreateUserModel createUserModel) {
		return userManagement.createUserService(createUserModel);
	}

	@RequestMapping(path = "updateUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage updateUserDetails(@RequestBody UpdateUserModel updateUserModel) {
		return userManagement.updateUserService(updateUserModel);
	}

	@RequestMapping(path = "userList", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage userList(@RequestBody UserModel userModel) {
		return userManagement.userListService(userModel.getUserId());
	}

	@RequestMapping(path = "userActive", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage userActive(@RequestBody ActiveUserModel model) {
		return userManagement.userActivationService(model.getUserId(), model.getComments());
	}

	@RequestMapping(path = "userInActive", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage userInActive(@RequestBody ActiveUserModel model) {
		return userManagement.userInActivationService(model.getUserId(), model.getComments());
	}

	@RequestMapping(path = "saveuserRole", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage createUserRole(@RequestBody UserModel userModel) {
		return userManagement.createUserRoleService(userModel.getUserId(), userModel.getRoleId());
	}
	
	@RequestMapping(path = "viewUser", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ViewUserModel viewUserDetails(@RequestBody UserModel userModel) {
		return userManagement.findUserDetails(userModel.getUserId());
	}

	@RequestMapping(path = "selfReportingToList", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> selfReportingTo(@RequestBody UserModel userModel) {
		return userManagement.getselfReportingTo(userModel);
	}
	
	/*@RequestMapping(path = "listOfreporting", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listoFReporting(@RequestBody UserModel userModel) {
		return userManagement.getListofReporting();
	}

	@RequestMapping(path = "listOfReportingTo", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listoFReportingTo(@RequestBody UserModel userModel) {
		return userManagement.getListofReportingTo(userModel);
	}
	
	@RequestMapping(path = "listofAssigningroles", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Roles> listofAssigningroles(@RequestBody UserModel userModel) {
		return userManagement.getListofAssigningroles(userModel);
	}

	@RequestMapping(path = "getJoineeRole", method = RequestMethod.GET, produces = "application/json")
	public List<Roles> getJoineeRole() {
		return userManagement.getJoineeRole();
	}

	@RequestMapping(path = "listofroles", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listoFRoles(@RequestBody UserModel userModel) {
		return userManagement.getListofRoles();
	}

	@RequestMapping(path = "listofregions", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Region> listoFRegions(@RequestBody UserModel userModel) {
		return userManagement.getRegions();
	}

	@RequestMapping(path = "listoftileId", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listoFTileIdFromRegions(@RequestBody UserModel userModel) {
		return userManagement.ListOfTileIdGromRegionId(userModel.getState(), userModel.getRegion());
	}


	@RequestMapping(path = "/health", method = RequestMethod.GET, produces = "application/json")
	public UserModel check() {
		UserModel model = new UserModel();
		return model;
	}
	
	@RequestMapping(path = "getRoleName", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage getRoleName(@RequestBody UserModel userModel) {

		String roleName = userManagement.getRoleName(userModel.getRoleId());
		if (roleName != null && !roleName.equals("")) {

			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(new StatusMessage("ST01", roleName));
			return responseMessage;
		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "Invalid role id"));
			return responseMessage;
		}
	}

	@RequestMapping(path = "getUserRole", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage getUserRole(@RequestBody UserModel userModel) {

		String roleName = userManagement.getUserRole(userModel.getUserId());

		if ((roleName != null) && (!roleName.equals(""))) {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(new StatusMessage("ST01", roleName));
			return responseMessage;
		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "No Role Added..."));
			return responseMessage;
		}
	}
	
	@RequestMapping(path = "getEcoSystemName", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage getEcosystemName(@RequestBody UserModel userModel) {

		String roleName = userManagement.getEcoSystemName(userModel.getRoleId());
		if (roleName != null && !roleName.equals("")) {

			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setStatus(new StatusMessage("ST01", roleName));
			return responseMessage;
		} else {
			ResponseMessage responseMessage = new ResponseMessage();
			responseMessage.setError(new ErrorMessage("ERR01", "Invalid role id"));
			return responseMessage;
		}
	} */
	
	@RequestMapping(path = "ecosystems", method = RequestMethod.GET, produces = "application/json")
	public List<Object> listOfRoles() {
		return userManagement.listOfRoles();
	}
	
	@RequestMapping(path = "states", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listofStates(@RequestBody UserModel userModel) {
		return userManagement.getListofStates(userModel.getRoleId());
	}
	
	@RequestMapping(path = "regions", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listofRegion(@RequestBody UserModel userModel) {
		return userManagement.getListofRegion(userModel.getState(), userModel.getRoleId() );
	}
	
	@RequestMapping(path = "reportingRole", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listofReportingRole(@RequestBody UserModel userModel) {
		return userManagement.getListofReportingRole( userModel.getRoleId() );	
	}
	
	@RequestMapping(path = "reportingTo", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> listofReportingTo(@RequestBody UserModel userModel) {
		return userManagement.reportingToService( userModel.getReportingRoleId(), userModel.getState(), userModel.getRegion() );	
	}
	
	@RequestMapping(path = "changeRole", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> changeRole(@RequestBody UserModel userModel) {
		return userManagement.changeRole(userModel.getUserId());
	}
	
	@RequestMapping(path = "userStatus", method = RequestMethod.POST)
	public boolean userStatus(@RequestBody UserModel userModel) {
		return userManagement.getStatus( userModel.getUserId() );
	}
	
	/*@RequestMapping(path = "rest/test", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public List<Object> testMethod(@RequestBody UserModel userModel) {
		return userManagement.changeRole(userModel.getUserId());
	}
	
	@RequestMapping(path = "test", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public String test(@RequestBody UserModel userModel) {
		
		userManagement.checkTaskCompletedAssigned(userModel.getUserId());
		return "";
	}
	
	@RequestMapping(path = "prstest", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public String prstasktest(@RequestBody UserModel userModel) {
		
		userManagement.checkPrsTaskCompletedAssigned(userModel.getUserId());
		return "";				
	}*/
	
}


