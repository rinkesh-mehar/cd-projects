package in.cropdata.cdtmasterdata.acl.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import in.cropdata.cdtmasterdata.acl.dto.NavData;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 1L;
    
    private User user;
    private AclUser aclUser;
    Map<String, NavData> navMap = new HashMap<String, NavData>();

    public CustomUserDetails(final User _user, AclUser aclUser, Map<String, NavData> menuList) {
	this.user = _user;
	this.aclUser = aclUser;
	this.navMap = menuList;
    }

    public CustomUserDetails() {
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
	Collection<GrantedAuthority> _grntdAuths = new HashSet<GrantedAuthority>();

	if (user != null) {
	    _grntdAuths = user.getAuthorities();
	}

	return _grntdAuths;
    }

    @Override
    public String getPassword() {
	return user.getPassword();
    }

    @Override
    public String getUsername() {
	if (this.user == null) {
	    return null;
	}
	return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
	return this.user.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
	return this.user.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
	return this.user.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
	return this.user.isEnabled();
    }

    public User getUser() {
	return user;
    }

    /**
     * @return the aclUser
     */
    public AclUser getAclUser() {
	return aclUser;
    }

    
    
    /**
     * @return the navMap
     */
    public Map<String, NavData> getNavMap() {
        return navMap;
    }

    /**
     * @param navMap the navMap to set
     */
    public void setNavMap(Map<String, NavData> navMap) {
        this.navMap = navMap;
    }

    @Override
    public String toString() {
	return "CustomUserDetails [user=" + user + "]";
    }
}