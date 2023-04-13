package com.densoft.darajaapi.services;

import com.densoft.darajaapi.dtos.AccessTokenResponse;
import com.densoft.darajaapi.dtos.RegisterUrlRequest;
import com.densoft.darajaapi.dtos.RegisterUrlResponse;

public interface DarajaApi {
    AccessTokenResponse getAccessToken();

    RegisterUrlResponse registerUrl();
}
