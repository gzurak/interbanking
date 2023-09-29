package org.zuratech.interbankingApi.services.interbankingService.saldos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class MultipleAccountBalances {
    @JsonProperty("general_data")
    private GeneralData generalData;
    private List<Account> Accounts;
}
