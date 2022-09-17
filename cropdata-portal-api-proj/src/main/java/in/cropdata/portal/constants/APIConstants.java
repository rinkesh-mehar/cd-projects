package in.cropdata.portal.constants;

public class APIConstants {
	/** status */
	public static final String STATUS_INACTIVE = "Inactive";
	public static final String STATUS_APPROVED = "Approved";
	public static final String STATUS_REJECTED = "Rejected";
	public static final String STATUS_DELETED = "Deleted";
	public static final String STATUS_ACTIVE = "Active";
	/** user registration status */
	public static final String USER_REG_PENDING = "Pending";
	public static final String USER_WORKING = "Working";
	public static final String USER_REG_COMPLETED = "Completed";
	public static final String USER_EXPIRED = "Expired";

	/** Response messages */
	public static final String RESPONSE_ADD_SUCCESS = " Added Successfully";
	public static final String RESPONSE_UPDATE_SUCCESS = " Updated Successfully with id : ";
	public static final String RESPONSE_UPDATE_ERROR = " Not Updated with id : ";
	public static final String RESPONSE_DELETE_SUCCESS = " Deleted Successfully with id : ";
	public static final String RESPONSE_DELETE_ERROR = " Not Deleted with id : ";
	public static final String RESPONSE_DOES_NOT_EXIST = " Does Not Exist with id : ";
	public static final String RESPONSE_NO_RECORD_FOUND = " No Record Found : ";
	public static final String RESPONSE_ALREADY_EXIST = " Already Exist with : ";
	public static final String RESPONSE_PRIMARY_APPROVAL_SUCCESS = " Primary Approve Successful";
	public static final String RESPONSE_PRIMARY_APPROVAL_ERROR = " Primary Approve Failed";
	public static final String RESPONSE_FINAL_APPROVAL_SUCCESS = " Final Approve Successful";
	public static final String RESPONSE_FINAL_APPROVAL_ERROR = " Final Approve Failed";
	public static final String RESPONSE_INVALID_CREDENTIALS = "Invalid Login Credentials";
	public static final String RESPONSE_INACTIVE_USER = "Your profile is not Activated, please contact Administrator.";
	public static final String RESPONSE_REJECT_SUCCESS = " Rejected";
	public static final String RESPONSE_REJECT_ERROR = " Failed to Reject";
	public static final String LOGIN_OTP_VERIFICATION = "LOGIN_OTP_VERIFICATION";
	public static final String SUCCESSFULL_DOCUMENT_TEMPLATE = "SUCCESSFULL_DOCUMENT_TEMPLATE";
	public static final String BUYER_REGISTRATION_TEMPLATE = "BUYER_REGISTRATION_TEMPLATE";

	/** SendGrid Mail API end-point */
	public static final String SENDGRID_API_ENDPOINT = "/mail/send";


	public static final String BUYER_APP_PATH_KEY = "buyer";

	/** User Application Types*/

	public static final int FRANCHISE = 1;
	public static final int BUYER = 3;
	public static final int SELLER = 5;



}
