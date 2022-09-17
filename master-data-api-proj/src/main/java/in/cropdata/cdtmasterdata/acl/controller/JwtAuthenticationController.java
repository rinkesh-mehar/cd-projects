package in.cropdata.cdtmasterdata.acl.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.acl.dto.NavData;
import in.cropdata.cdtmasterdata.acl.model.AclUser;
import in.cropdata.cdtmasterdata.acl.model.CustomUserDetails;
import in.cropdata.cdtmasterdata.acl.model.JwtRequest;
import in.cropdata.cdtmasterdata.acl.service.JwtUserDetailsService;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.exceptions.InactiveUserException;
import in.cropdata.cdtmasterdata.exceptions.InvalidUserCredentialsException;

@RestController
//@CrossOrigin("*")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
	authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
	final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
	final String token = jwtTokenUtil.generateToken(userDetails);
	AclUser aclUser = null;
	 Map<String, NavData> namMap = null;
	if (userDetails instanceof CustomUserDetails) {
	    CustomUserDetails user = (CustomUserDetails) userDetails;
	    aclUser = user.getAclUser();
	    aclUser.setToken(token);
	    namMap = user.getNavMap();
	}
	
	Map<String, Object> dataMap = new HashMap<String, Object>();
	dataMap.put("user", aclUser);
	dataMap.put("nav", namMap);
	
	return ResponseEntity.ok(dataMap);
    }

    private void authenticate(String username, String password) throws Exception {
	try {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	} catch (DisabledException e) {
	    throw new InactiveUserException(APIConstants.RESPONSE_INACTIVE_USER);
	} catch (BadCredentialsException e) {
	    throw new InvalidUserCredentialsException(APIConstants.RESPONSE_INVALID_CREDENTIALS);
	}
    }
}