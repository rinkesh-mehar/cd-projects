package in.cropdata.farmers.app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import in.cropdata.farmers.app.drk.model.DrkFarmer;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	 
	private DrkFarmer drkFarmer;


	public CustomUserDetails(DrkFarmer appDrkFarmerDetail) {
		this.drkFarmer = appDrkFarmerDetail;
	}

	public CustomUserDetails() {
	}

	/**
	 * @return the FarmerApp Farmer
	 */
	public DrkFarmer getUser() {
		return drkFarmer;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		
		return  drkFarmer.getPrimaryMobNumber();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}