import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {

    private static final Encoder ENCODER = Base64.getEncoder();
    private static final Decoder DECODER = Base64.getDecoder();

    private static final String ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String IV = "12345678b0z2345n"; // 16 bytes IV
    private static final IvParameterSpec IV_PARAMS = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
    
    public static SecretKeySpec getKey(String myKey) throws UnsupportedEncodingException {
        byte[] key = myKey.getBytes(StandardCharsets.UTF_8);
        key = Arrays.copyOf(key, 32); // use only first 256 bits
        return new SecretKeySpec(key, "AES");
    }

    public static String encrypt(String strToEncrypt, SecretKeySpec secretKey) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, IV_PARAMS);
        byte[] encodedBytes = strToEncrypt.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedBytes = cipher.doFinal(encodedBytes);
        return ENCODER.encodeToString(encryptedBytes);
    }

    public static String decrypt(String strToDecrypt, SecretKeySpec secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException
    {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, IV_PARAMS);
        byte[] decodedBytes = DECODER.decode(strToDecrypt);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8).trim();
    }
}
