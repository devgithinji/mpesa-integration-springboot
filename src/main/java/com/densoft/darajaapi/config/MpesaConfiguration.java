package com.densoft.darajaapi.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "mpesa.daraja")
public class MpesaConfiguration {
    private String consumerKey;
    private String consumerSecret;
    private String grantType;
    private String oauthEndpoint;
    private String shortCode;
    private String responseType;
    private String confirmationURL;
    private String validationURl;
    private String registerUrlEndpoint;


    @Override
    public String toString() {
        return String.format("{consumerKey='%s', consumerSecret='%s', grantType='%s' , oauthEndpoints='%s'}",
                consumerKey, consumerSecret, grantType, oauthEndpoint);
    }


}
