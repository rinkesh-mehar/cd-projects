package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface User {

    public int getId();

    public int getRoleId();

    public String getName();

    public String getEmail();

//    @JsonIgnore
    public String getPassword();

    public String getRole();

    public String getStatus();
    
    public Date getCreatedAt();

    public Date getUpdatedAt();

}
