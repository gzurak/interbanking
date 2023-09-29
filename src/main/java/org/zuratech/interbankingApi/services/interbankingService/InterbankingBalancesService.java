package org.zuratech.interbankingApi.services.interbankingService;

import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCreds;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsDTO;
import org.zuratech.interbankingApi.services.interbankingService.saldos.MultipleAccountBalances;
import org.zuratech.interbankingApi.services.interbankingService.saldos.RequestSaldos;
import org.zuratech.interbankingApi.services.interbankingService.saldos.Saldos;

import java.util.Collections;
@Service
public class InterbankingBalancesService {
    private final RestTemplate restTemplate;
    private final InterBankingAuthenticationService authenticationService;

    public InterbankingBalancesService(@Value("${interbanking.balances.url}") String url, RestTemplateBuilder restTemplateBuilder, InterBankingAuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.restTemplate = restTemplateBuilder
                .rootUri(url)
                .build();
    }


    public Saldos getSaldos(RequestSaldos requestSaldos , InterbankingCredsDTO interbankingCreds){
        restTemplate.setInterceptors(Collections.singletonList(new RetryInterceptor(interbankingCreds, authenticationService)));
        ResponseEntity<Saldos> response =  restTemplate.getForEntity(String.format("/%s/balances?%s", requestSaldos.getAccountNumber(), buildParams(requestSaldos, interbankingCreds)), Saldos.class);
        return response.getBody();
    }

    public MultipleAccountBalances getMultipleAccountsBalances(RequestSaldos requestSaldos, InterbankingCredsDTO interbankingCreds){
        restTemplate.setInterceptors(Collections.singletonList(new RetryInterceptor(interbankingCreds, authenticationService)));
        ResponseEntity<MultipleAccountBalances> response = restTemplate.getForEntity(String.format("/balances?%s", buildParams(requestSaldos, interbankingCreds)), MultipleAccountBalances.class);
        return response.getBody();
    }

    private String buildParams(RequestSaldos requestSaldos, InterbankingCredsDTO interbankingCreds) {
        String params = String.format("customer-id=%s&", interbankingCreds.getCustomerId());
        if(requestSaldos.getBankNumber() != null){
            params += String.format("bank-number=%s&", requestSaldos.getBankNumber());
        }
        if(requestSaldos.getAccountType() != null){
            params += String.format("account-type=%s&", requestSaldos.getAccountType());
        }
        if(requestSaldos.getCurrency() != null){
            params += String.format("currency=%s&", requestSaldos.getCurrency());
        }
        if(requestSaldos.getDateSince() != null){
            params += String.format("date-since=%s&", requestSaldos.getDateSince());
        }
        if(requestSaldos.getDateUntil() != null){
            params += String.format("date-until=%s&", requestSaldos.getDateUntil());
        }
        if(requestSaldos.getLimit() != null){
            params += String.format("limit=%s&", requestSaldos.getLimit());
        }
        if(requestSaldos.getPage() != null){
            params += String.format("page=%s&", requestSaldos.getPage());
        }
        return params;
    }
}
