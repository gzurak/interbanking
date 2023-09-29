package org.zuratech.interbankingApi.services.interbankingService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCreds;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsDTO;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsRepository;
import org.zuratech.interbankingApi.services.CredsService;

@Service
public class InterBankingAuthenticationService {
    private static final Logger log = LoggerFactory.getLogger(InterBankingAuthenticationService.class);
    private final RestTemplate restTemplate;

    private final CredsService credsService;
    public InterBankingAuthenticationService(@Value("${interbanking.auth.url}") String url, @Value("${interbanking.service.url}") String service, RestTemplateBuilder restTemplateBuilder, CredsService credsService) {
        this.credsService = credsService;
        this.restTemplate = restTemplateBuilder.rootUri(url).errorHandler(new RestTemplateResponseErrorHandler()).defaultHeader("service", service).build();
    }

    public String authenticate(InterbankingCredsDTO interbankingCreds){
        String token = "";
        ResponseEntity<InterbankingTokenResponse> response = restTemplate.postForEntity("/cas/oidc/accessToken?scope=info-financiera - Informacion Financiera", getHttpEntity(interbankingCreds), InterbankingTokenResponse.class);
        if(response.getStatusCode().isError()){
            log.error("Error in core auth code: {}", response.getStatusCodeValue());
        }else{
            InterbankingTokenResponse tokenResponse = response.getBody();
            token = String.format("Bearer %s", tokenResponse.getAccessToken());
            interbankingCreds.setAccessToken(token);
            credsService.updateToken(interbankingCreds);
        }
        return token;
    }

    private HttpEntity<MultiValueMap<String, HttpEntity<?>>> getHttpEntity(InterbankingCredsDTO creds){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        multipartBodyBuilder.part("client_id", creds.getClientId());
        multipartBodyBuilder.part("client_secret", creds.getClientSecret());
        multipartBodyBuilder.part("grant_type", "client_credentials");
        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();
        return new HttpEntity<>(multipartBody, httpHeaders);
    }


    public String getToken(InterbankingCredsDTO interbankingCreds){
        return interbankingCreds.getAccessToken();
    }
}
