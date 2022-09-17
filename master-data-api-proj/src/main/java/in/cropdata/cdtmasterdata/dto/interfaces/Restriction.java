package in.cropdata.cdtmasterdata.dto.interfaces;

//interface to get only required data
public interface Restriction{
    public int getID();
    public int getRoleID();
    public int getResourceID();
    public int getResourceGroupID();
    public int getSubResourceID();
    public String getRoleName();
    public String getResourceName();
    public String getResourceURL();
    public String getParentResource();
    public String getResourceGroupName();
    public String getSubResourceURL();
    public String getSubResources();
//    public String getGroupName();
}//Restriction