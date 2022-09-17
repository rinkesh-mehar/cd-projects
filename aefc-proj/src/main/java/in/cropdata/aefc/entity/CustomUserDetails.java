package in.cropdata.aefc.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.entity
 * @date 05/12/21
 * @time 6:46 PM
 */
public class CustomUserDetails implements UserDetails
{
    private static final long serialVersionUID = 1L;

    private ApplicantDetails applicantDetails;

    public CustomUserDetails(ApplicantDetails applicantDetails) {
        this.applicantDetails = applicantDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return null;
    }

    @Override
    public String getPassword()
    {
        return null;
    }

    @Override
    public String getUsername()
    {
        return applicantDetails.getMobileNumber();
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return false;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return false;
    }

    @Override
    public boolean isEnabled()
    {
        return false;
    }

    public ApplicantDetails getUser() {
        return applicantDetails;
    }
}
