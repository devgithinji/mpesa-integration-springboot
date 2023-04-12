package com.densoft.darajaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import okhttp3.OkHttpClient;

@SpringBootApplication
public class DarajaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DarajaApiApplication.class, args);
    }

    @Bean
    public OkHttpClient getOkhttpClient() {
        return new OkHttpClient();
    }

    @Bean
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

}
