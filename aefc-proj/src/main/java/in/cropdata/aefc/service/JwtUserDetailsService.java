package in.cropdata.aefc.service;


import in.cropdata.aefc.entity.ApplicantDetails;
import in.cropdata.aefc.entity.CustomUserDetails;
import in.cropdata.aefc.repository.ApplicantDetailsRepository;
import in.cropdata.aefc.utils.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.aefc.service
 * @date 05/12/21
 * @time 6:07 PM
 */
@Service
public class JwtUserDetailsService implements UserDetailsService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUserDetailsService.class);
    @Autowired
    private ApplicantDetailsRepository applicantDetailsRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    @Override
    public UserDetails loadUserByUsername(String mobile) throws UsernameNotFoundException
    {
        Optional<ApplicantDetails> foundUserDetails = applicantDetailsRepository.findAllByMobileNumber(mobile.strip());

        ApplicantDetails applicantDetails= null;
        if (foundUserDetails.isPresent()){

            applicantDetails = new ApplicantDetails();

            applicantDetails.setID(foundUserDetails.get().getID());
            applicantDetails.setFirstName(foundUserDetails.get().getFirstName());
            applicantDetails.setMobileNumber(foundUserDetails.get().getMobileNumber());
            applicantDetails.setEmailAddress(foundUserDetails.get().getEmailAddress());
//            applicantDetails.setAuthToken(foundUserDetails.get().getAuthToken());
            return new CustomUserDetails(applicantDetails);
        } else {
            throw new UsernameNotFoundException("User not found with mobile: " + mobile);
        }
    }


    public void updateAuthToken(String token, String mobile) {
        try {
            Optional<ApplicantDetails> applicantDetails = this.applicantDetailsRepository.findAllByMobileNumber(mobile);
            if(applicantDetails.isPresent()){

                applicantDetails.get().setAuthToken(token);
                LOGGER.info("authenticate from web " + applicantDetails);

                this.applicantDetailsRepository.save(applicantDetails.get());

                responseMessageUtil.sendResponse(true, "", 200);
            }else {
                responseMessageUtil.sendResponse(false, "", 200);
            }
        } catch (Exception e) {
            responseMessageUtil.sendResponse(false, "", 200);
        }
    }
}
