package kodlamaio.HRMS.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public class HashingHelper {

	public static String CreatePasswordHash(String password) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(password.getBytes());
		byte[] digest = md.digest();
		String myHash = HexFormat.of().formatHex(digest).toUpperCase();
		return myHash;
	}

	public static boolean VerifyPasswordHash(String password, String passwordHash) {

		String hash = "";
		try {
			hash = CreatePasswordHash(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (hash.equals(passwordHash)) {
			return true;
		}
		return false;
	}
}
