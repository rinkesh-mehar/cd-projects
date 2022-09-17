package in.cropdata.aefc.controller;

import in.cropdata.aefc.constants.APIConstants;
import in.cropdata.aefc.entity.ApplicantDetails;
import in.cropdata.aefc.entity.CustomUserDetails;
import in.cropdata.aefc.entity.JwtRequest;
import in.cropdata.aefc.exception.InactiveUserException;
import in.cropdata.aefc.exception.InvalidUserCredentialsException;
import in.cropdata.aefc.service.JwtUserDetailsService;
import in.cropdata.aefc.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.aefc.controller
 * @date 05/12/21
 * @time 5:32 PM
 */
@RestController
public class AuthenticationController
{
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Map<String, Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception{
        String mobile = null;
        mobile = authenticationRequest.getMobile();
        authenticate(mobile, authenticationRequest.getPassword());
        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(mobile);
        final String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> dataMap = new HashMap<>();

        ApplicantDetails applicantDetails = null;

        if (userDetails instanceof CustomUserDetails){
            CustomUserDetails user = (CustomUserDetails) userDetails;
            applicantDetails = user.getUser();
            if (user.getUser() != null) {
                if (user.getUser().getAuthToken() == null || user.getUser().getAuthToken().isEmpty()) {
                    applicantDetails.setAuthToken(token);
                    this.jwtUserDetailsService.updateAuthToken(applicantDetails.getAuthToken(), mobile);
                }
                dataMap.put("user", applicantDetails);
            }
        }
        return dataMap;
    }

    private void authenticate(String mobile, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(mobile, password));
        } catch (InternalAuthenticationServiceException e) {
            throw new InactiveUserException(APIConstants.RESPONSE_INACTIVE_USER, "Inactive User");
        } catch (BadCredentialsException e) {
            throw new InvalidUserCredentialsException(APIConstants.RESPONSE_INVALID_CREDENTIALS, "Invalid User Credentials");
        } catch (LockedException e) {

        }
    }
}
