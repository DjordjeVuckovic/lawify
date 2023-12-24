package org.lawify.psp.crypto.shared.crypto.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.crypto.shared.crypto.CryptoService;
import org.springframework.beans.factory.annotation.Value;

@Converter
@RequiredArgsConstructor
public class CryptoConverter implements AttributeConverter<String, byte[]> {
    @Value("${api-key.encrypt}")
    private String encryptionKey;
    private static final int IV_LENGTH = 16;
    @Override
    public byte[] convertToDatabaseColumn(String attribute) {
        return CryptoService.encryptProperty(attribute,encryptionKey,IV_LENGTH);
    }

    @Override
    public String convertToEntityAttribute(byte[] dbData) {
        return CryptoService.decryptProperty(dbData,encryptionKey,IV_LENGTH);
    }
}
