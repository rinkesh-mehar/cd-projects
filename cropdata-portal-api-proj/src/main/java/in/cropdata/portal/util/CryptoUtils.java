package in.cropdata.portal.util;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import in.cropdata.portal.exceptions.CryptoException;

/**
 * Utility class for processing encryption/decryption operations using AES-GCM
 * algorithm.
 * <p>
 * It requires 12 bytes IV and 16 bytes salt which will be used for encryption
 * and decryption.
 * </p>
 * 
 * @author PranaySK
 * @Date 30-Sep-2020
 */

@Component
public class CryptoUtils {
	private static final String ALGORITHM_NAME = "AES";
	private static final String ENCRYPT_ALGO = "AES/GCM/NoPadding";
	private static final String SECRET_KEY_ALGORITHM = "PBKDF2WithHmacSHA256";
	private static final String PASSWORD = "#(^-P$@n@&";
	/** TLen value must be one of {128, 120, 112, 104, 96} */
	private static final int TAG_LENGTH_BIT = 128;
	private static final int IV_LENGTH_BYTE = 12;
	private static final int SALT_LENGTH_BYTE = 16;

	public static void main(String[] args) {
		String pText = "Pranay";
		String encryptedTextBase64 = encrypt(pText);
		System.out.println("\n------ AES GCM Password-based Encryption ------\n");
		System.out.println("Input (plain text) -> " + pText);
		System.out.println("Encrypted (base64) -> " + encryptedTextBase64);

		System.out.println("\n------ AES GCM Password-based Decryption ------\n");
		System.out.println("Input (base64) -> " + encryptedTextBase64);

		String decryptedText = decrypt(encryptedTextBase64);
		System.out.println("Decrypted (plain text) -> " + decryptedText);
	}

	/**
	 * Used for encryption of provided input using AES algorithm.
	 * 
	 * @param inputText the input to be encrypted
	 * 
	 * @throws CryptoException
	 * 
	 * @author PranaySK
	 */
	public static String encrypt(String inputText) {
		try {
			/** generate salt of 16 bytes and IV of 12 bytes */
			byte[] salt = CryptoUtils.getRandomBytes(SALT_LENGTH_BYTE);
			byte[] iv = CryptoUtils.getRandomBytes(IV_LENGTH_BYTE);
			/** get AES secret key from password */
			SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(PASSWORD.toCharArray(), salt);
			/** AES-GCM initialization */
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
			cipher.init(Cipher.ENCRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
			/** encrypting the input */
			byte[] cipherText = cipher.doFinal(inputText.getBytes(StandardCharsets.UTF_8));
			/** prefix IV and Salt to cipher text which will be used while decryption */
			byte[] cipherTextWithIvSalt = ByteBuffer.allocate(iv.length + salt.length + cipherText.length).put(iv)
					.put(salt).put(cipherText).array();
			/** encode the byte array to Base64 string */
			return Base64.encodeBase64URLSafeString(cipherTextWithIvSalt);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException | IllegalBlockSizeException | BadPaddingException
				| InvalidKeyException | InvalidAlgorithmParameterException | NoSuchPaddingException ex) {
			throw new CryptoException("Encryption failed -> " + ex.getMessage());
		}
	}

	/**
	 * Used for decryption of provided input using AES algorithm.
	 * 
	 * @param encryptedText the encrypted input to be decrypted
	 * 
	 * @throws CryptoException
	 * 
	 * @author PranaySK
	 */
	public static String decrypt(String encryptedText) {
		try {
			/** decode the Base64 encrypted input */
			byte[] decode = Base64.decodeBase64(encryptedText.getBytes(StandardCharsets.UTF_8));
			/** get back the IV and salt from the cipher text */
			ByteBuffer bb = ByteBuffer.wrap(decode);
			byte[] iv = new byte[IV_LENGTH_BYTE];
			bb.get(iv);
			byte[] salt = new byte[SALT_LENGTH_BYTE];
			bb.get(salt);
			byte[] cipherText = new byte[bb.remaining()];
			bb.get(cipherText);
			/** get the AES key from the same password and salt */
			SecretKey aesKeyFromPassword = CryptoUtils.getAESKeyFromPassword(PASSWORD.toCharArray(), salt);
			/** AES-GCM initialization */
			Cipher cipher = Cipher.getInstance(ENCRYPT_ALGO);
			cipher.init(Cipher.DECRYPT_MODE, aesKeyFromPassword, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
			/** decrypting the input */
			byte[] plainText = cipher.doFinal(cipherText);
			/** return the plain text */
			return new String(plainText, StandardCharsets.UTF_8);

		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| InvalidKeySpecException | NoSuchPaddingException | IllegalBlockSizeException
				| BadPaddingException ex) {
			throw new CryptoException("Decryption failed -> " + ex.getMessage());
		}
	}

	/**
	 * Used for getting AES secret key of 256 bits from password.
	 * 
	 * @param password the password used for encryption/decryption process
	 * @param salt     the salt used for encryption/decryption process
	 * 
	 * @return {@link SecretKey}
	 * 
	 * @author PranaySK
	 */
	private static SecretKey getAESKeyFromPassword(char[] password, byte[] salt)
			throws NoSuchAlgorithmException, InvalidKeySpecException {
		SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_KEY_ALGORITHM);
		/** iterationCount = 65536 and keyLength = 256 */
		KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
		return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), ALGORITHM_NAME);
	}

	/**
	 * Used for getting random bytes data for the provided size using
	 * {@link SecureRandom}.
	 * 
	 * @param numBytes the size of which the bytes to be generated
	 * 
	 * @return byte[]
	 * 
	 * @author PranaySK
	 */
	private static byte[] getRandomBytes(int numBytes) {
		byte[] randomBytes = new byte[numBytes];
		new SecureRandom().nextBytes(randomBytes);
		return randomBytes;
	}

	public static String referalCoder(Integer userId) {
//		String fullName = firstName +" "+ lastName;
//		int idxLastWhitespace = fullName.lastIndexOf(' ');
//		String initials = fullName.substring(0, 1) + fullName.substring(idxLastWhitespace + 1, idxLastWhitespace + 2);
		Integer number = 429+userId;
		LocalDate currentdate = LocalDate.now();
		int monthValue = currentdate.getMonthValue();
		int lastTwoDigits = Calendar.getInstance().get(Calendar.YEAR) % 100;
		return "A"+monthValue+"-"+number+lastTwoDigits;
	}

}