package org.zuratech.interbankingApi.services.interbankingService.saldos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class HistoricalBalances {
    @JsonProperty("operation_date")
    private String operationDate;
    @JsonProperty("day_balance")
    private Double dayBalance;
    @JsonProperty("total_credits")
    private Double totalCredits;
    @JsonProperty("total_debits")
    private Double totalDebits;
}
