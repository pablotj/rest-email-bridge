package com.pablotj.restemailbridge.infrastructure.encryption;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Value;

@Converter
public class EncryptionConverter implements AttributeConverter<String, String> {


    @Value("${app.encryption.secret}")
    private String secret;

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute == null ? null : new EncryptionUtils(secret).encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData == null ? null : new EncryptionUtils(secret).decrypt(dbData);
    }
}
