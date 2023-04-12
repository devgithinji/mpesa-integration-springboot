package com.densoft.darajaapi.services;

import com.densoft.darajaapi.config.MpesaConfiguration;
import com.densoft.darajaapi.dtos.AccessTokenResponse;
import com.densoft.darajaapi.utils.HelperUtility;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import okhttp3.OkHttpClient;

import java.io.IOException;

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
}
