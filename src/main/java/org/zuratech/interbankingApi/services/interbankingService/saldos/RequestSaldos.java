package org.zuratech.interbankingApi.services.interbankingService.saldos;

import lombok.Data;

@Data
public class RequestSaldos {
    private String accountNumber;
    private String accountType;
    private String bankNumber;
    private String currency;
    private String customerId;
    private String dateSince;
    private String dateUntil;
    private Integer limit;
    private Integer page;
}
