package in.cropdata.farmers.app.constants;
public class APIConstants {

	// status
	public static final String STATUS_INACTIVE = "Inactive";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String STATUS_DELETED = "Deleted";
	public static final String STATUS_ACTIVE = "Active";

	// Response messages
	public static final String RESPONSE_ADD_SUCCESS = "Farmer's Record Saved Successfully with id :";
	public static final String RESPONSE_UPDATE_SUCCESS = " Updated Successfully with id : ";
	public static final String RESPONSE_UPDATE_ERROR = " Not Updated with id : ";
	public static final String RESPONSE_DELETE_SUCCESS = " Deleted Successfully with id : ";
	public static final String RESPONSE_DELETE_ERROR = " Not Deleted with id : ";
	public static final String RESPONSE_DOES_NOT_EXIST = " Not Exist with id : ";
	public static final String RESPONSE_NO_RECORD_FOUND = " No Record Found : ";
	public static final String RESPONSE_ALREADY_EXIST = " Already Exist with : ";
	public static final String RESPONSE_PRIMARY_APPROVAL_SUCCESS = " Primary Approve Successful";
	public static final String RESPONSE_PRIMARY_APPROVAL_ERROR = " Primary Approve Failed";
	public static final String RESPONSE_FINAL_APPROVAL_SUCCESS = " Final Approve Successful";
	public static final String RESPONSE_FINAL_APPROVAL_ERROR = " Final Approve Failed";
	public static final String RESPONSE_INVALID_CREDENTIALS = "Invalid Login Credentials";
	public static final String RESPONSE_INACTIVE_USER = "Your profile is not Activated, please contact Administrator.";
	public static final String RESPONSE_REJECT_SUCCESS = " Rejected";
	public static final String RESPONSE_REJECT_ERROR = " failed to Reject";

	//Notification

	public static final int NORMAL_NOTIFICATION = 1;// For Simple Push Message Alert
	public static final int ADVISORY_NOTIFICATION = 2;// For Advisory Push Message Which is Case Based
	public static final int CASE_REGISTRATION = 7;// 3 For Advisory Push Message Which is Case Based
	public static final int CASE_RECOMMENDATION =4;
	public static final int CASE_DETAILS = 5;
	public static final int LOCATION_TRACKING_START_REGISTRATION = 6;// To  Start Location Service
	public static final int LOCATION_TRACKING_START_DELIVARY = 3; // 7
	public static final int LOCATION_TRACKING_STOP = 8;// To Stop Location Service

	
	

	//Address Type

	public static final int CITY_TYPE = 1;
	public static final int VILLAGE_TYPE = 2;
	public static final int PIN_TYPE = 3;


	public static class Values{
	    public static int HOURS_IN_DAY = 8;
	}
	
	//Company
	public static final String COMPANY_NAME= "Cropdata Technology Pvt Ltd";

	//video language
	public static final String ENGLISH ="en";
	public static final String HINDI ="hi";
	public static final String TELUGU = "te";

	//video Ids
	public static final String EN = "57doOe17nsI";
	public static final String HI = "WXygjR4iImg";
	public static final String TE = "SSafZkyleGE";






}
