package in.cropdata.gstm.studio.constants;

/**
 * Error constants for exception handling.
 * 
 * @author PranaySK
 * @since 1.0
 */

public class ErrorConstants {
	/** Invalid request data */
	public static final String INVALID_DATA = "GSAIDT-ERR-001";

	/** Failed to convert request parameters */
	public static final String TYPE_MISMATCH = "GSATMM-ERR-002";

	/** Requested data not found */
	public static final String DATA_FETCH_ERROR = "GSANRF-ERR-003";

	/** Failed to prepare data */
	public static final String DATA_PREP_ERROR = "GSADPE-ERR-004";

	/** Something went wrong - Internal Error Occurred */
	public static final String INTERNAL_SERVER_ERROR = "GSAISE-ERR-005";

	/** Failed to generate file */
	public static final String FILE_CREATION_ERROR = "GSAFCE-ERR-006";
}
