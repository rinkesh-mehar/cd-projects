package in.cropdata.cdtmasterdata.acl.interceptor;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import in.cropdata.cdtmasterdata.acl.model.AclRestriction;
import in.cropdata.cdtmasterdata.acl.model.CustomUserDetails;
import in.cropdata.cdtmasterdata.acl.service.JwtUserDetailsService;
import in.cropdata.cdtmasterdata.exceptions.AccessDeniedException;

public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
	System.err.println("Intercepted: " + request.getRequestURI());

//	Iterator<String> it = request.getHeaderNames().asIterator();
//	while (it.hasNext()) {
//	    String header = it.next();
//	    System.out.println(header+"\t"+request.getHeader(header));
//	}

	UserDetails userDetails;

	if (!request.getRequestURI().equals("/authenticate")) {
	    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    // To first check if the user is logged in, check that the current
	    // Authentication is not a AnonymousAuthenticationToken.
	    if (!(auth instanceof AnonymousAuthenticationToken)) {
		userDetails = (UserDetails) auth.getPrincipal();

		// authorization
		if (userDetails instanceof CustomUserDetails) {
		    CustomUserDetails user = (CustomUserDetails) userDetails;
		    String requestURI = request.getRequestURI();
		    int roleId = user.getAclUser().getRoleId();
		    //set Role to the request attribute
		    request.setAttribute("RoleID", roleId);
//		    System.err.println("RoleID : " + roleId);
		    List<AclRestriction> blockedResources = jwtUserDetailsService.getRestrictionsForRoleId(roleId);
		    for (AclRestriction aclRestriction : blockedResources) {
			String tempURI = aclRestriction.getResourceURI();
			String verifyURI = "";
			Map<String, Object> pathParams = (Map<String, Object>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
			if(pathParams!=null && pathParams.keySet()!=null) {
			    for (String pathVarKey : pathParams.keySet()) {
				try {
				    String pathK = "{" + pathVarKey + "}";
				    String pathVarValue = (String) pathParams.get(pathVarKey);
//				    System.out.println(pathK+"\t"+pathParams.get(pathVarKey)+"\t\t"+pathVarKey+"\t\t"+pathVarValue);
				    if (pathVarValue!=null && !pathVarValue.contains("null")) {
					if (tempURI!=null) {
//					    System.err.println(tempURI+"\t\t"+verifyURI);
					    verifyURI = tempURI.replace(pathK, pathVarValue);
					}
				    }
				} catch (Exception e) {
				    throw e;
				}
				    
			    }//for
	                            System.err.println(verifyURI);
				if (requestURI.equals(aclRestriction.getResourceURI())) {
				    throw new AccessDeniedException("User is not Authorized to access this resource.");
				}

				if (requestURI.equals(verifyURI)) {
				    throw new AccessDeniedException("User is not Authorized to access this resource.");
				}
				
			}//if
			
		    }//for restrictions
		System.err.println("Blocked Resources: \n"+blockedResources); 

		} // authorize

	    } // if user logged in
	} // if request is not for /authenticate

	System.err.println("Passed: " + request.getRequestURI());
	return true;// HandlerInterceptor.super.preHandle(request, response, handler);
    }// preHandle
}