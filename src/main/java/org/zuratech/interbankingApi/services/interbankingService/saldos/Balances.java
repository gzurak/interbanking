package org.zuratech.interbankingApi.services.interbankingService.saldos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Balances {
    @JsonProperty("countable_balance")
    private Double countableBalance;
    @JsonProperty("current_operating_balance")
    private Double currentOperatingBalance;
    @JsonProperty("initial_operating_balance")
    private Double initialOperatingBalance;
    @JsonProperty("projected_balance_24hs")
    private Double projectedBalance24Hs;
    @JsonProperty("projected_balance_48hs")
    private Double projectedBalance48Hs;

}
