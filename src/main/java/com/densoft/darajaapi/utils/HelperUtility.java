package com.densoft.darajaapi.utils;

import com.densoft.darajaapi.dtos.RegisterUrlRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.internal.Base64;

import java.nio.charset.StandardCharsets;


public class HelperUtility {
    public static String tobase64String(String value) {
        byte[] data = value.getBytes(StandardCharsets.ISO_8859_1);
        return Base64.encode(data);
    }

    public static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
