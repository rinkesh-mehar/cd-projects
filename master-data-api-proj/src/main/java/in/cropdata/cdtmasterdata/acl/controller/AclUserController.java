package in.cropdata.cdtmasterdata.acl.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.acl.dto.NavData;
import in.cropdata.cdtmasterdata.acl.model.AclUser;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.User;
import in.cropdata.cdtmasterdata.service.AclUserService;

@RestController
@RequestMapping("/acl/user")
//@CrossOrigin("*")
public class AclUserController {

	@Autowired
	private AclUserService userService;

	@PostMapping("/add")
	public ResponseMessage addUser(@RequestBody AclUser user) {
		return userService.addUser(user);
	}// addUser

	@GetMapping("/list")
	public List<User> getAllUser(){
		return userService.getAllUser();
	}// getAllUser

	@PutMapping("/{id}/update")
	public ResponseMessage updateUserById(@PathVariable int id, @RequestBody AclUser user) {
		return userService.updateUserById(id, user);
	}// updateUserById

	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteUserById(@PathVariable int id) {
		return userService.deleteUserById(id);
	}// deleteUserById

	@GetMapping("/{id}") // edit by admin
	public User findUserById(@PathVariable int id) {
		return userService.findUserById(id);
	}// findUserById

	@PutMapping("/{id}/changePassword")
	public ResponseMessage updatePassword(@RequestParam String oldPassword,@PathVariable int id, @RequestParam String password) {
		return userService.updatePassword(oldPassword,password,id);
	}//updatePassword
	
	@PostMapping("/{id}/forgetPassword")
	public ResponseMessage forgetPassword(@RequestParam String email) {
		return userService.forgetPassword(email);
	}// forgetPassword
	
	@GetMapping("/menu-list-by-search")
	public Map<String, NavData> getMenusByRoleAndSearchText(@RequestParam("roleId") Integer roleId, @RequestParam(required = false, defaultValue = "") String searchText) {
		return userService.getMenusByRoleAndSearchText(roleId, searchText);
	}

}// UserController
