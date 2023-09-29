package org.zuratech.interbankingApi.services.interbankingService;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCreds;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsDTO;

import java.io.IOException;

public class RetryInterceptor implements ClientHttpRequestInterceptor {
    private final InterBankingAuthenticationService interBankingAuthenticationService;
    private static final String AUTHORIZATION = "Authorization";
    private static final String CLIENT_ID="client_id";
    private InterbankingCredsDTO interbankingCreds;


    public RetryInterceptor(InterbankingCredsDTO interbankingCreds, InterBankingAuthenticationService interBankingAuthenticationService) {
        this.interBankingAuthenticationService = interBankingAuthenticationService;
        this.interbankingCreds = interbankingCreds;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        request.getHeaders().add(AUTHORIZATION, interBankingAuthenticationService.getToken(interbankingCreds));
        request.getHeaders().add(CLIENT_ID, interbankingCreds.getClientId());
        ClientHttpResponse response = execution.execute(request, body);
        if(HttpStatus.UNAUTHORIZED.equals(response.getStatusCode())){
            request.getHeaders().remove(AUTHORIZATION);
            request.getHeaders().add(AUTHORIZATION, interBankingAuthenticationService.authenticate(interbankingCreds));
            response = execution.execute(request, body);
        }
        return response;
    }
}
