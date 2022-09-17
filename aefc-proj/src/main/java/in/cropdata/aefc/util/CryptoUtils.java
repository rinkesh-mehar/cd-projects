package in.cropdata.aefc.util;

import in.cropdata.aefc.exception.CryptoException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

/**
 * Utility class for processing encryption/decryption operations using AES-GCM
 * algorithm.
 * <p>
 * It requires 12 bytes IV and 16 bytes salt which will be used for encryption
 * and decryption.
 * </p>
 * 
 * @author RinkeshKM
 * @Date 06-Dec-2021
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

	/**
	 * Used for encryption of provided input using AES algorithm.
	 * 
	 * @param inputText the input to be encrypted
	 * 
	 * @throws CryptoException
	 * 
	 * @author RinkeshKM
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
	 * Used for getting AES secret key of 256 bits from password.
	 * 
	 * @param password the password used for encryption/decryption process
	 * @param salt     the salt used for encryption/decryption process
	 * 
	 * @return {@link SecretKey}
	 * 
	 * @author RinkeshKM
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
	 * @author RinkeshKM
	 */
	private static byte[] getRandomBytes(int numBytes) {
		byte[] randomBytes = new byte[numBytes];
		new SecureRandom().nextBytes(randomBytes);
		return randomBytes;
	}


}