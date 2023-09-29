package org.zuratech.interbankingApi.services.interbankingService;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class InterbankingTokenResponse {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("token_type")
    private String tokenType;
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("id_token")
    private String idToken;
}
