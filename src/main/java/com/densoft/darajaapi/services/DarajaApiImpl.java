package com.densoft.darajaapi.services;

import com.densoft.darajaapi.config.MpesaConfiguration;
import com.densoft.darajaapi.dtos.AccessTokenResponse;
import com.densoft.darajaapi.dtos.RegisterUrlRequest;
import com.densoft.darajaapi.dtos.RegisterUrlResponse;
import com.densoft.darajaapi.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.densoft.darajaapi.utils.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class DarajaApiImpl implements DarajaApi {
    private final MpesaConfiguration mpesaConfiguration;
    private final OkHttpClient okHttpClient;
    private final ObjectMapper objectMapper;

    @Override
    public AccessTokenResponse getAccessToken() {
        //get the base64 rep of consumerKey + ":" + secretKey;
        String encodedCredentials = HelperUtility.tobase64String(String.format("%s:%s",
                mpesaConfiguration.getConsumerKey(),
                mpesaConfiguration.getConsumerSecret()));
        Request request = new Request.Builder()
                .url(String.format("%s?grant_type=%s", mpesaConfiguration.getOauthEndpoint(), mpesaConfiguration.getGrantType()))
                .get()
                .addHeader(AUTHORIZATION_HEADER_STRING, String.format("%s %s", BASIC_AUTH_STRING, encodedCredentials))
                .addHeader(CACHE_CONTROL_HEADER, CACHE_CONTROL_HEADER_VALUE)
                .build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            //use jackson to decode the body
            return objectMapper.readValue(response.body().string(), AccessTokenResponse.class);
        } catch (IOException exception) {
            log.error(String.format("Could not get access token, -> %s", exception.getLocalizedMessage()));
            return null;
        }
    }

    @Override
    public RegisterUrlResponse registerUrl() {
        AccessTokenResponse accessTokenResponse = getAccessToken();
        RegisterUrlRequest registerUrlRequest = new RegisterUrlRequest();
        registerUrlRequest.setConfirmationURL(mpesaConfiguration.getConfirmationURL());
        registerUrlRequest.setResponseType(mpesaConfiguration.getResponseType());
        registerUrlRequest.setShortCode(mpesaConfiguration.getShortCode());
        registerUrlRequest.setValidationURL(mpesaConfiguration.getValidationURl());

        RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, Objects.requireNonNull(HelperUtility.toJson(registerUrlRequest)));
        Request request = new Request.Builder()
                .url(mpesaConfiguration.getRegisterUrlEndpoint())
                .post(body)
                .addHeader("Authorization", "Bearer " + accessTokenResponse.getAccessToken())
                .build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            assert response.body() != null;
            String bodyString = response.body().string();
            // System.out.println(bodyString);
            return objectMapper.readValue(bodyString, RegisterUrlResponse.class);
        } catch (IOException e) {
            log.error(String.format("could not register url -> %s", e.getMessage()));
            return null;
        }

    }
}
