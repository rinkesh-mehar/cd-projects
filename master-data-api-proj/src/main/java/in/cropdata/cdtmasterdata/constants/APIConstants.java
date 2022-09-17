package in.cropdata.cdtmasterdata.constants;

public class APIConstants {

	// status
	public static final String STATUS_INACTIVE = "Inactive";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String STATUS_DELETED = "Deleted";
	public static final String STATUS_ACTIVE = "Active";
	public static final String STATUS_PENDING = "Pending";

	// Response messages
	public static final String RESPONSE_ADD_SUCCESS = " Added Successfully";
	public static final String RESPONSE_UPDATE_SUCCESS = " Updated Successfully with ID : ";
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
	public static final String RESPONSE_REJECT_SUCCESS = " Rejected Approve Successful";
	public static final String RESPONSE_REJECT_ERROR = " failed to Reject";
	public static final String RESPONSE_CLOSE_SUCCESS = " Closed Successfully with ID : ";
	public static final String RESPONSE_ACTIVE_SUCCESS = " Activated Successfully with ID : ";
	public static final String RESPONSE_DEACTIVE_SUCCESS = " Deactivated Successfully with id : ";
	public static final String RESPONSE_MOVE_TO_MASTER_SUCCESS = " Moved To Master Successfully";

	// SendGrid Mail API end-point
	public static final String SENDGRID_API_ENDPOINT = "/mail/send";
}
