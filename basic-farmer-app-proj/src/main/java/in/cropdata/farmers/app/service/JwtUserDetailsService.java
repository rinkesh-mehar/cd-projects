package in.cropdata.farmers.app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.drk.repository.DrkFarmerRepository;
import in.cropdata.farmers.app.model.CustomUserDetails;
import in.cropdata.farmers.app.model.Farmer;
import in.cropdata.farmers.app.repository.FarmerRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
 
	@Autowired
	DrkFarmerRepository drkFarmerRepository;

	@Override
	public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException {
		Optional<DrkFarmer> foundDrkFarmerDetail = drkFarmerRepository.findAllByPrimaryMobNumber(mobile.strip());
		DrkFarmer appDrkFarmerDetail = null;
		
	  if (foundDrkFarmerDetail.isPresent()) {
			appDrkFarmerDetail = new DrkFarmer();
			appDrkFarmerDetail.setId(foundDrkFarmerDetail.get().getId());
			appDrkFarmerDetail.setFarmerName(foundDrkFarmerDetail.get().getFarmerName());
			appDrkFarmerDetail.setPrimaryMobNumber(foundDrkFarmerDetail.get().getPrimaryMobNumber());
			appDrkFarmerDetail.setVillageCode(foundDrkFarmerDetail.get().getVillageCode());
			appDrkFarmerDetail.setAuthToken(foundDrkFarmerDetail.get().getAuthToken());
			appDrkFarmerDetail.setAddressId(foundDrkFarmerDetail.get().getAddressId());
			appDrkFarmerDetail.setFarmSize(foundDrkFarmerDetail.get().getFarmSize());
			return new CustomUserDetails(appDrkFarmerDetail);
		}else {
			throw new UsernameNotFoundException("Farmer not found with mobile: " + mobile);
		}

	}
}