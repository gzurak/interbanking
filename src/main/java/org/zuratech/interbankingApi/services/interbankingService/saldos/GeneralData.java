package org.zuratech.interbankingApi.services.interbankingService.saldos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GeneralData {
    @JsonProperty("bank_number")
    private String bankNumber;
    @JsonProperty("row_date")
    private String rowDate;
    @JsonProperty("date_since")
    private String dateSince;
    @JsonProperty("date_until")
    private String dateUntil;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("account_number")
    private String accountNumber;
    @JsonProperty("control_code")
    private String controlCode;
    @JsonProperty("page")
    private Integer page;
    @JsonProperty("limit")
    private Integer limit;
    @JsonProperty("account_type")
    private String accountType;
    @JsonProperty("total_rows")
    private Integer totalRows;
}
