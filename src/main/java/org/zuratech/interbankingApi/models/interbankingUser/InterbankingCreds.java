package org.zuratech.interbankingApi.models.interbankingUser;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="interbanking_creds")
@Data
public class InterbankingCreds {
    @Id
    @GeneratedValue
    private Long id;
    private String clientId;
    private String clientSecret;
    private String customerId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "interbanking_user_id", updatable = false)
    @JsonIgnore
    private InterbankingUser user;
    private String accessToken;
}
