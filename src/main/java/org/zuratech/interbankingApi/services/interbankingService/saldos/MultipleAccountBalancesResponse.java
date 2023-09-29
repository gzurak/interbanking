package org.zuratech.interbankingApi.services.interbankingService.saldos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MultipleAccountBalancesResponse {
    private List<MultipleAccountBalances> multipleAccountBalances;
}
