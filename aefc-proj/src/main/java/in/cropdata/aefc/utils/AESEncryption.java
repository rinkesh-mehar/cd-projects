package in.cropdata.aefc.utils;

import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AESEncryption
{
	private static final String AES_KEY = "aesEncryptionKey";
	private static final String INIT_VECTOR = "encryptionIntVec";
	private static final String ALGORITHM_NAME = "AES";
	private static final String ALGORITHM_WITH_PADDING = "AES/CBC/PKCS5PADDING";

	public String encrypt(String value) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM_NAME);

			Cipher cipher = Cipher.getInstance(ALGORITHM_WITH_PADDING);
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

			byte[] encrypted = cipher.doFinal(value.getBytes());
			Base64.Encoder encoder = Base64.getEncoder();
			return encoder.encodeToString(encrypted);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public String decrypt(String encrypted) {
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes(StandardCharsets.UTF_8));
			SecretKeySpec skeySpec = new SecretKeySpec(AES_KEY.getBytes(StandardCharsets.UTF_8), ALGORITHM_NAME);

			Cipher cipher = Cipher.getInstance(ALGORITHM_WITH_PADDING);
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			Base64.Decoder decoder = Base64.getDecoder();
			byte[] original = cipher.doFinal(decoder.decode(encrypted));

			return new String(original);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}