package org.zuratech.interbankingApi.services.interbankingService.saldos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Account {
    @JsonProperty("bank_number")
    private String bankNumber;
    private String currency;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("account_label")
    private String accountLabel;
    @JsonProperty("account_name")
    private String accountName;
    @JsonProperty("historical_balances")
    private List<HistoricalBalances> historicalBalances;
    private Balances balances;
}
