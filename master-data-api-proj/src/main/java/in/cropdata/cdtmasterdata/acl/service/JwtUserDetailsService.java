package in.cropdata.cdtmasterdata.acl.service;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.dto.Menu;
import in.cropdata.cdtmasterdata.acl.dto.NavData;
import in.cropdata.cdtmasterdata.acl.model.AclRestriction;
import in.cropdata.cdtmasterdata.acl.model.AclUser;
import in.cropdata.cdtmasterdata.acl.model.CustomUserDetails;
import in.cropdata.cdtmasterdata.exceptions.InactiveUserException;
import in.cropdata.cdtmasterdata.repository.AclUserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private AclUserRepository aclUserRepository;

    @Autowired
    private AclRestrictionsService aclRestrictionsService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//	Optional<User> foundAcluser = aclUserRepository.findByEmail(username.trim());

	in.cropdata.cdtmasterdata.dto.interfaces.User foundAcluser = aclUserRepository.getUserByEmail(username.trim());
	AclUser aclUser = null;
	if (foundAcluser != null) {
	    aclUser = new AclUser();
	    aclUser.setId(foundAcluser.getId());
	    aclUser.setRoleId(foundAcluser.getRoleId());
	    aclUser.setName(foundAcluser.getName());
	    aclUser.setRole(foundAcluser.getRole());
	    aclUser.setEmail(foundAcluser.getEmail());
	    aclUser.setPassword(foundAcluser.getPassword());
	    aclUser.setStatus(foundAcluser.getStatus());
	    aclUser.setCreatedAt(foundAcluser.getCreatedAt());
	    aclUser.setUpdatedAt(foundAcluser.getUpdatedAt());

	    if (!aclUser.getStatus().equals("Active")) {
		throw new InactiveUserException("User '" + username + "' is not active.");
	    }

	    List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
//	    System.err.println("Role: "+aclUser.getRole());
	    grantedAuthorities.add(new SimpleGrantedAuthority(aclUser.getRole()));

	    User user = new User(aclUser.getEmail(), aclUser.getPassword(), grantedAuthorities);

	    List<Menu> menuList = aclUserRepository.getMenusByRole(foundAcluser.getRoleId());

	    Map<String, NavData> navMap = new LinkedHashMap<>();
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

//	    System.err.println(navMap.entrySet());
	    return new CustomUserDetails(user, aclUser, navMap);

	} else {
	    throw new UsernameNotFoundException("User not found with username: " + username);
	}

    }// loadUserByUsername

    public List<AclRestriction> getRestrictionsForRoleId(int roleId) {
	return aclRestrictionsService.findAllByRoleId(roleId);
    }
}