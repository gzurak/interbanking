package org.zuratech.interbankingApi.services.interbankingService;

import org.springframework.stereotype.Service;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCreds;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsDTO;
import org.zuratech.interbankingApi.services.interbankingService.saldos.MultipleAccountBalances;
import org.zuratech.interbankingApi.services.interbankingService.saldos.MultipleAccountBalancesResponse;
import org.zuratech.interbankingApi.services.interbankingService.saldos.RequestSaldos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class InterbankingService {

    private final InterbankingBalancesService balancesService;

    public InterbankingService(InterbankingBalancesService balancesService) {
        this.balancesService = balancesService;
    }

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public MultipleAccountBalancesResponse getBalancesForMultipleAccounts(List<InterbankingCredsDTO> creds){
        List<MultipleAccountBalances> multipleAccountBalances = new ArrayList<>();
        for(InterbankingCredsDTO credsDTO : creds){
            RequestSaldos requestSaldos = new RequestSaldos();
            requestSaldos.setDateSince(formatter.format(LocalDateTime.now().minusMonths(1)));
            requestSaldos.setDateUntil(formatter.format(LocalDateTime.now().minusDays(1)));
            multipleAccountBalances.add(balancesService.getMultipleAccountsBalances(requestSaldos, credsDTO));
        }
        return new MultipleAccountBalancesResponse(multipleAccountBalances);
    }
}
