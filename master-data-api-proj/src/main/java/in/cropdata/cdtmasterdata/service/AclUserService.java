package in.cropdata.cdtmasterdata.service;

import java.io.File;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.dto.Menu;
import in.cropdata.cdtmasterdata.acl.dto.NavData;
import in.cropdata.cdtmasterdata.acl.model.AclUser;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.User;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.repository.AclUserRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AclUserService {

	@Autowired
	private AclUserRepository aclUserRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EmailSenderService emailSender;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public ResponseMessage addUser(AclUser user) {
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		try {
			aclUserRepository.save(user);
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
		return responseMessageUtil.sendResponse(true, "User" + APIConstants.RESPONSE_ADD_SUCCESS, "");
	}// addUser

	public List<User> getAllUser() {
		List<User> list = aclUserRepository.getAllUserData();
		return list;
	}// getAllUser

	public ResponseMessage updateUserById(int id, AclUser user) {
		Optional<AclUser> foundUser = aclUserRepository.findById(id);

		if (foundUser.isPresent()) {
			AclUser updateUser = foundUser.get();
			if (user.getEmail() != null) {
				updateUser.setEmail(user.getEmail());
			}

			if (user.getName() != null) {
				updateUser.setName(user.getName());
			}

			if (user.getStatus() != null) {
				updateUser.setStatus(user.getStatus());
			}

			if (user.getRoleId() != 0) {
				updateUser.setRoleId(user.getRoleId());
			}

			aclUserRepository.save(updateUser);

			return responseMessageUtil.sendResponse(true, "User" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
		} else {
			return responseMessageUtil.sendResponse(false, "", "User" + APIConstants.RESPONSE_UPDATE_ERROR + id);
		}
	}// updateUserById

	public ResponseMessage deleteUserById(int id) {

		try {
			aclUserRepository.deleteById(id);
			return responseMessageUtil.sendResponse(true, "User" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "User" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}
	}// deleteUserById

	public User findUserById(int id) {

		try {
			User user = aclUserRepository.getUserById(id);

			if (user != null) {
				return user;
			} else {
				throw new DoesNotExistException("User" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findUserById

	public ResponseMessage updatePassword(String oldPassword, String password, int id) {

		try {
			ResponseMessage responseMessage = new ResponseMessage();

			Optional<AclUser> foundVareity = aclUserRepository.findById(id);
			if (foundVareity.isPresent()) {
				AclUser user = foundVareity.get();
				if (bcryptEncoder.matches(oldPassword, user.getPassword())) {
					user.setPassword(bcryptEncoder.encode(password));

					try {
						aclUserRepository.save(user);
					} catch (Exception e) {
						return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
					}

					responseMessage.setSuccess(true);
					responseMessage.setMessage("Password changed successfully.");
				} else {
					responseMessage.setSuccess(false);
					responseMessage.setError("Invalid old password.");
				}

			} else {
				responseMessage.setSuccess(false);
				responseMessage.setError("Failed to change password.");
			}
			return responseMessage;
		} catch (Exception e) {
			throw e;
		}

	}// updatePassword

	public ResponseMessage forgetPassword(String email) {
		ResponseMessage responseMessage = new ResponseMessage();

		try {

			User user = aclUserRepository.getUserByEmail(email);

			if (user == null) {
				responseMessage.setMessage("email account does not exist");
			} else {
				// Email message
				SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
//				passwordResetEmail.setFrom("support@demo.com");
				passwordResetEmail.setTo(email);
				passwordResetEmail.setSubject("Password Request");
				passwordResetEmail
						.setText("your password: " + "Password Reset Link : http://cropdatadev.tk/generate-password");

				emailSender.sendEmail(passwordResetEmail);
				responseMessage.setSuccess(true);
				responseMessage.setMessage("password send successfully to the user e-mail");
			}

		} catch (MailSendException e) {
			throw e;
		}
		return responseMessage;
	}
	
	public Map<String, NavData> getMenusByRoleAndSearchText(Integer roleId, String searchText) {
		
		searchText = "%" + searchText + "%";
		System.err.println("roleId : " + roleId + " searchText : " + searchText);
		List<Menu> menuList = aclUserRepository.getMenusByRoleAndSearchText(roleId,searchText);
		
		Map<String, NavData> navMap = new LinkedHashMap<>();
		System.err.println("menuList : " + menuList.toString());
		for (Menu menu : menuList) {
			String group = menu.getResourceGroupName();
			if (navMap.get(group) == null) {
				NavData navData = new NavData();
				navData.setName(group);
				navData.setIcon("fa fa-indent");
				if (menu.getResourceURL() != null && menu.getResourceURL().contains("/")) {
					String[] urls = menu.getResourceURL().split(File.separator);
					if (urls.length > 1) {
						navData.setUrl("/" + urls[1]);
					} else {
						navData.setUrl("#");
					}
				}//if menu url not null
				navMap.put(group, navData);
			}//if not exist in navMap

			NavData navData = new NavData();
			navData.setName(menu.getResourceName());
			navData.setUrl(menu.getResourceURL());
			navData.setIcon("fa fa-indent");

			if (navMap.get(group) != null) {
				navMap.get(group).getChildren().add(navData);
			}
		}//for

		for (Map.Entry<String, NavData> navMapEntry : navMap.entrySet()) {
			List<NavData> sortedNavChildren = navMapEntry.getValue().getChildren().stream().sorted(Comparator.comparing(NavData::getName)).collect(Collectors.toList());
			navMapEntry.getValue().getChildren().clear();
			navMapEntry.getValue().getChildren().addAll(sortedNavChildren);
		}
		
		if(navMap.isEmpty() || navMap.size() == 0) {
			return null;	
		}else {
			return navMap;	
		}
		
		
	}

}
