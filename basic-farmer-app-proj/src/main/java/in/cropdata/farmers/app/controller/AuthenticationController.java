package in.cropdata.farmers.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.farmers.app.constants.APIConstants;
import in.cropdata.farmers.app.drk.model.DrkFarmer;
import in.cropdata.farmers.app.exception.InactiveUserException;
import in.cropdata.farmers.app.exception.InvalidUserCredentialsException;
import in.cropdata.farmers.app.model.CustomUserDetails;
import in.cropdata.farmers.app.model.Farmer;
import in.cropdata.farmers.app.model.JwtRequest;
import in.cropdata.farmers.app.service.FarmerLoginService;
import in.cropdata.farmers.app.service.JwtUserDetailsService;
import in.cropdata.farmers.app.utils.JwtTokenUtil;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private FarmerLoginService appService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public Map<String, Object> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        String mobile = null;
        mobile = authenticationRequest.getMobile();

        authenticate(mobile, authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        final String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, Object> dataMap = new HashMap<>();
        
        DrkFarmer appDrkFarmerDetail = null;
        if (userDetails instanceof CustomUserDetails) {
            CustomUserDetails user = (CustomUserDetails) userDetails;
            appDrkFarmerDetail = user.getUser();
            if (user.getUser() != null) {
            	if (user.getUser().getAuthToken() == null || user.getUser().getAuthToken().isEmpty()) {
                    appDrkFarmerDetail.setAuthToken(token);
                    this.appService.updateAuthToken(appDrkFarmerDetail.getAuthToken(), mobile);
                }
                dataMap.put("user", appDrkFarmerDetail);
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
