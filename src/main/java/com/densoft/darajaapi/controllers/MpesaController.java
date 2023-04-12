package com.densoft.darajaapi.controllers;

import com.densoft.darajaapi.dtos.AccessTokenResponse;
import com.densoft.darajaapi.services.DarajaApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mobile-money")
@RequiredArgsConstructor
public class MpesaController {
    private final DarajaApi darajaApi;

    @GetMapping(path = "/token", produces = "application/json")
    public ResponseEntity<AccessTokenResponse> getAccessToken() {
        return ResponseEntity.ok(darajaApi.getAccessToken());
    }

    //register url

}
