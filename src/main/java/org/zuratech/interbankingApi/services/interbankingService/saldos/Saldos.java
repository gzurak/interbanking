package org.zuratech.interbankingApi.services.interbankingService.saldos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Saldos {
    @JsonProperty("general_data")
    private GeneralData generalData;
    @JsonProperty("balances")
    private Balances balances;
    @JsonProperty("historical_balances")
    private List<HistoricalBalances> historicalBalances;
}
