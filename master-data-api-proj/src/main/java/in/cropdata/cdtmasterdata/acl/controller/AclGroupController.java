package in.cropdata.cdtmasterdata.acl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.acl.model.AclGroup;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.service.AclGroupService;

@RestController
@RequestMapping("/acl/group")
//@CrossOrigin("*")
public class AclGroupController {
	
	@Autowired
	private AclGroupService groupService;
	
	@PostMapping("/add")
	public ResponseMessage addGroup(@RequestBody AclGroup aclGroup) {
		return groupService.addGroup(aclGroup);
	}//addGroup
	
	@GetMapping("/list")
	public List<AclGroup> getAllGroup(){
		return groupService.getAllGroup();
	}//getAllGroup
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateGroupById(@PathVariable int id, @RequestBody AclGroup aclGroup) {
		return groupService.updateGroupById(id, aclGroup);
	}//updateGroupById
	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteGroupById(@PathVariable int id){
		return groupService.deleteGroupById(id);
	}//deleteGroupById
	
	@GetMapping("/{id}")
	public AclGroup findGroupById(@PathVariable int id) {
		return groupService.findGroupById(id);
	}//findGroupById

}//GroupController