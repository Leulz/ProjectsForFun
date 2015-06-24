package vernam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.SecureRandom;

//TODO Make it work with non-ASCII characters. 24-06-2015

/**
 * A Vernam Cipher implementation just for fun. Currently
 * only working for ASCII characters.
 * 
 * @author Léo Vital
 * 
 * Last change: 24-06-2015
 *
 */
public class VernamCipher {
	
	private static final byte MAX_BYTE = (byte) 127;
	
	private static final byte MIN_BYTE = (byte) -128;
	
	private static final Integer BINARY_BASE = 2;
	
	private static Integer MESSAGE_LENGTH;
	
	private static BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws IOException {
		System.out.println("Write a message to be ciphered: ");
		String message = getMessage();
		 
		SecureRandom cipher = new SecureRandom();
		
		byte[] messageAsBytes = message.getBytes();
		
		byte[] cipherAsBytes = new byte[MESSAGE_LENGTH];
		byte[] cipheredMessage = new byte[MESSAGE_LENGTH];
		byte[] decipheredMessage = new byte[MESSAGE_LENGTH];
		
		cipher.nextBytes(cipherAsBytes);
		
		adjustCipher(cipherAsBytes);
		
		cipherMessage(cipheredMessage, messageAsBytes, cipherAsBytes);
		
		System.out.println("This is the ciphered message as characters: ");
		System.out.println(new String(cipheredMessage));
		System.out.println("This is the ciphered message as bytes: ");
		
		decipherMessage(cipheredMessage, decipheredMessage, cipherAsBytes);
		
		System.out.println("This is the deciphered message: " + new String(decipheredMessage));
	}
	
	/**
	 * Method responsible for ciphering the message.
	 * 
	 * @param cipheredMessage
	 * 			The array of bytes in which the ciphered message shall be stored.
	 * @param messageAsBytes
	 * 			The message as an array of bytes.
	 * @param cipherAsBytes
	 * 			The cipher to be used.
	 */
	private static void cipherMessage(byte[] cipheredMessage, byte[] messageAsBytes, byte[] cipherAsBytes) {
		for (int i = 0; i < cipherAsBytes.length; i++) {
			cipheredMessage[i] = (byte) (messageAsBytes[i] ^ cipherAsBytes[i]);
		}
	}
	
	/**
	 * Adjusts the cipher so that it does not contain any negative number.
	 * Since ASCII only contains positive numbers, negative ones are shown
	 * as unknown characters. This is only done so that the message is more 
	 * readable as a stream of characters.
	 * 
	 * @param cipherAsBytes
	 * 			The cipher to be adjusted.
	 */
	private static void adjustCipher(byte[] cipherAsBytes) {
		for (int i = 0; i < cipherAsBytes.length; i++) {
			byte localCipherAsBytes = cipherAsBytes[i];
			cipherAsBytes[i] =  (localCipherAsBytes == MIN_BYTE) ? (byte) MAX_BYTE : (byte) Math.abs(localCipherAsBytes);
		}
	}
	
	/**
	 * Deciphers the message using the cipher passed.
	 * 
	 * @param cipheredMessage
	 * 			The ciphered message.
	 * @param decipheredMessage
	 * 			The byte array in which the deciphered message
	 * 			will be stored.
	 * @param cipherAsBytes
	 * 			The cipher used to cipher the message.
	 */
	private static void decipherMessage(byte[] cipheredMessage, byte[] decipheredMessage, byte[] cipherAsBytes) {
		for (int i = 0; i < cipheredMessage.length; i++) {
			System.out.print(String.format("%08d ", Integer.valueOf(Integer.toString(cipheredMessage[i], BINARY_BASE))));
			decipheredMessage[i] = (byte) (cipheredMessage[i] ^ cipherAsBytes[i]);
		}
		
		System.out.println();
	}
	
	/**
	 * Method responsible for getting the message to be ciphered by the
	 * Vernam Cipher.
	 * 
	 * @return The message written by the user.
	 * 
	 * @throws IOException
	 */
	private static String getMessage() throws IOException{
		String messageRead = bfr.readLine();
		MESSAGE_LENGTH = messageRead.length();
		
		return messageRead;
	}
}