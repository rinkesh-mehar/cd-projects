package in.cropdata.aefc.util;

import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Utility class for generating OTP required for buyer sign-up process.
 * 
 * @since 1.0
 * @author cropdata RinkeshKM
 */

@Component
public class OTPGenerator {

	/**
	 * Generates the OTP of required length
	 * 
	 * @param otpLength length of the required OTP
	 */
	public String generateOTP(int otpLength) {
		String otp = "";
		int tempOtp = 0;
		for (int i = 0; i < otpLength; i++) {
			otp = otp + "9";
		}

		tempOtp = Integer.parseInt(otp);

		try {
			/** Number Generation Algorithm */
			SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");
			otp = Integer.toString(prng.nextInt(tempOtp));
			otp = (otp.length() < otpLength) ? this.padRight(otp, otpLength, '0') : otp;
		} catch (NoSuchAlgorithmException e) {
			System.out.println(e.getMessage());
		}
		return otp;
	}

	/**
	 * Padding the char on left if generated OTP length is less than required length
	 * 
	 * @param otpStr the generated OTP
	 * @param len    length of the required OTP
	 * @param c      the character to be appended
	 */
	private String padRight(String otpStr, int len, char c) {
		otpStr = otpStr.trim();
		StringBuilder builder = new StringBuilder(len);
		int fill = len - otpStr.length();
		while (fill-- > 0)
			builder.append(c);
		System.out.println("otp generated OTP is -> " + otpStr.concat(String.valueOf(builder)));
		return otpStr.concat(String.valueOf(builder));
	}

}
