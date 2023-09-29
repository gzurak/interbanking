package org.zuratech.interbankingApi.models.interbankingUser;

import lombok.Data;

@Data
public class InterbankingCredsDTO {
    private Long id;
    private String clientId;
    private String clientSecret;
    private String customerId;
    private String accessToken;
}
