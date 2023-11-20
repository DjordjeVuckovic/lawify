package org.lawify.psp.mediator.shared.crypto;

import lombok.extern.slf4j.Slf4j;
import org.lawify.psp.mediator.shared.crypto.utils.HexKeyUtils;
import org.lawify.psp.mediator.shared.crypto.utils.KeyGeneratorUtils;
import org.lawify.psp.mediator.shared.crypto.utils.SymmetricUtils;
import org.lawify.psp.mediator.shared.exceptions.CryptoException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Service
@Slf4j
public class CryptoService {
    public byte[] encryptProperty(String attribute,String encryptionKey,int ivLength) {
        try {
            if (attribute == null) {
                return null;
            }
            var key = HexKeyUtils.hexStringToByteArray(encryptionKey);
            var ivBytes = KeyGeneratorUtils.generateRandomIV(ivLength);
            byte[] ciphertext = SymmetricUtils.encryptAes(
                    attribute.getBytes(StandardCharsets.UTF_8),
                    KeyGeneratorUtils.convertBytesToSecretKey(key),
                    ivBytes);
            if (ciphertext == null){
                log.error("Cannot encrypt data {}",attribute);
                throw new CryptoException("Cannot encrypt data!");
            }
            byte[] encryptedData = new byte[ivLength + ciphertext.length];
            System.arraycopy(ivBytes, 0, encryptedData, 0, ivLength);
            System.arraycopy(ciphertext, 0, encryptedData, ivLength, ciphertext.length);
            return encryptedData;
        } catch (Exception e) {
            log.error("Cannot encrypt data {}",attribute);
            throw new CryptoException("Error encrypting data", e);
        }
    }

    public String decryptProperty(byte[] dbData,String encryptionKey,int ivLength) {
        try {
            if (dbData == null) {
                return null;
            }
            byte[] ivBytes = Arrays.copyOfRange(dbData, 0, ivLength);
            byte[] ciphertext = Arrays.copyOfRange(dbData, ivLength, dbData.length);
            var key = HexKeyUtils.hexStringToByteArray(encryptionKey);
            SecretKey secretKey = KeyGeneratorUtils.convertBytesToSecretKey(key);
            byte[] decryptedData = SymmetricUtils.decryptAes(ciphertext, secretKey, ivBytes);
            if (decryptedData == null){
                log.error("Cannot decrypt data {}",dbData);
                throw new CryptoException("Cannot decrypt data!");
            }
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Cannot decrypt data {}",dbData);
            throw new CryptoException("Error decrypting data", e);
        }
    }
}
