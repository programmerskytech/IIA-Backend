package com.astro.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Base64;

public class Base64ToByteArrayConverter extends StdConverter<String, byte[]> {

    @Override
    public byte[] convert(String value) {
        if (value == null || value.isEmpty()) {
            return new byte[0]; // Return empty byte array for empty strings
        }
        return Base64.getDecoder().decode(value);
    }

}
