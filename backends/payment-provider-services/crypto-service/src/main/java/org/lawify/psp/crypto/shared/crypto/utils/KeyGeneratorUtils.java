package org.lawify.psp.crypto.shared.crypto.utils;




import org.lawify.psp.crypto.shared.exceptions.CryptoException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;

public class KeyGeneratorUtils {
    private static final int KEY_LENGTH = 256;
    public static Optional<SecretKey> generateKey(int keyLength) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(keyLength);
            return Optional.of(keyGen.generateKey());
        }
        catch (NoSuchAlgorithmException ex){
            System.out.println(ex.getMessage());
        }
        return Optional.empty();
    }
    public static SecretKey convertBytesToSecretKey(byte [] key){

        return new SecretKeySpec(key,"AES");
    }
    public static String generateHexKey(){
        var key = generateKey(KEY_LENGTH)
                .orElseThrow(() -> new CryptoException("Cannot generate key!"));
        return HexKeyUtils.bytesToHex(key.getEncoded());
    }
    public static byte[] generateRandomIV(int length) {
        SecureRandom random = new SecureRandom();
        byte[] iv = new byte[length];
        random.nextBytes(iv);
        return iv;
    }
}
