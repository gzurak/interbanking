package org.zuratech.interbankingApi.models.interbankingUser;

import lombok.Data;

import java.util.List;

@Data
public class InterbankingUserDTO {
    private Long id;
    private List<InterbankingCredsDTO> credentials;
}
