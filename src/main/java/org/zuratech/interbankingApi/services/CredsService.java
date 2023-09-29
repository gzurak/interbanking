package org.zuratech.interbankingApi.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCreds;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsDTO;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCredsRepository;

@Service
public class CredsService {
    private final InterbankingCredsRepository credsRepository;
    private final ObjectMapper mapper;

    public CredsService(InterbankingCredsRepository credsRepository, ObjectMapper mapper) {
        this.credsRepository = credsRepository;
        this.mapper = mapper;
    }

    public void updateToken(InterbankingCredsDTO credsDTO){
        credsRepository.save(mapper.convertValue(credsDTO, InterbankingCreds.class));
    }
}
