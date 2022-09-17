package in.cropdata.aefc.controller;

import in.cropdata.aefc.entity.ApplicantDetails;
import in.cropdata.aefc.model.RegisterBuyer;
import in.cropdata.aefc.model.ResponseMessage;
import in.cropdata.aefc.service.BayerService;
import in.cropdata.aefc.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;



/**
 * @author RinkeshKM
 * @date 05/12/21
 */

@RestController
public class BuyerController {

    @Autowired
    private BayerService bayerService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("register-buyer")
    public Map<String, Object> registerBuyer(@RequestBody RegisterBuyer registerBuyer) throws IOException {
        return bayerService.registerBuyer(registerBuyer);
    }

    @GetMapping("/verify-email/{email}")
    public Map<String, Object> verifyEmail(@PathVariable String email) {
        return bayerService.verifyEmail(email);
    }

    @PostMapping("/validateOTP")
    private Map<String, Object> validateOTP(@RequestBody ApplicantDetails details) throws Exception{

        return bayerService.validateOTP(details);

    }

    @PostMapping("getOTP")
    public Map<String, Object> getOTP(@RequestBody ApplicantDetails details) throws IOException
    {

        return bayerService.getOTP(details.getMobileNumber());
    }

    @GetMapping("verifiedUserDetails/{loginMobileNo}")
    public Map<String, Object> verifiedUserDetails(@PathVariable("loginMobileNo") String loginMobileNo){
        return bayerService.verifiedUserDetails(loginMobileNo);
    }

    @GetMapping("resendEmail/{emailAddress}/{mobileNumber}")
    public Map<String, Object> resendEmail(@PathVariable String emailAddress,@PathVariable String mobileNumber){
        return bayerService.resendEmail(emailAddress,mobileNumber);
    }

}
