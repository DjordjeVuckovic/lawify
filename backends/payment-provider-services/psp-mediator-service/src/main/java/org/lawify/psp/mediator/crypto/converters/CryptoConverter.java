package org.lawify.psp.mediator.crypto.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import lombok.RequiredArgsConstructor;
import org.lawify.psp.mediator.crypto.CryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Converter
@RequiredArgsConstructor
public class CryptoConverter implements AttributeConverter<String, byte[]> {
    @Value("${api-key.encrypt}")
    private String encryptionKey;
    private static final int IV_LENGTH = 16;
    private final CryptoService encryptionConverter;
    @Override
    public byte[] convertToDatabaseColumn(String attribute) {
        return encryptionConverter.encryptProperty(attribute,encryptionKey,IV_LENGTH);
    }

    @Override
    public String convertToEntityAttribute(byte[] dbData) {
        return encryptionConverter.decryptProperty(dbData,encryptionKey,IV_LENGTH);
    }
}
