import java.security.*;
import javax.crypto.spec.*;
import java.util.Arrays;
import javax.xml.bind.DatatypeConverter;

public class AES {
	/**
	 * Converter array de bytes para string em hexadecimal
	 * @param array
	 * @return
	 */
	public static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}
	
	/**
	 * Converter uma string em hexadecimal para um array de bytes
	 * @param s
	 * @return
	 */
	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}
	
	// Gera uma chave a partir de uma string
	// Retorna a chave secreta a partir dos 16 bytes da funcao hash
	// aplicada sobre a string
	public static SecretKeySpec getSecretKey(String passwd) throws Exception {
		byte[] dataBytes = passwd.getBytes();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(dataBytes, 0, passwd.length());
		byte[] mdBytes = md.digest();
		
		return new SecretKeySpec(Arrays.copyOfRange(mdBytes, 0, 16),"AES");
	}
	
}
