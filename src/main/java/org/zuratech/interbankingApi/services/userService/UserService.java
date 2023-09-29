package org.zuratech.interbankingApi.services.userService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.zuratech.interbankingApi.models.interbankingUser.*;
import org.zuratech.interbankingApi.services.randomString.RandomString;

import java.util.List;

@Service
public class UserService {
    private InterbankingUserRepository userRepository;
    private InterbankingCredsRepository credsRepository;
    private RandomString randomStringGenerator;
    private ObjectMapper mapper;

    public UserService(InterbankingUserRepository userRepository, InterbankingCredsRepository credsRepository, ObjectMapper mapper) {
        this.userRepository = userRepository;
        this.credsRepository = credsRepository;
        this.mapper = mapper;
        this.randomStringGenerator = new RandomString();
    }

    public InterbankingUserDTO getUserByToken(String token){
        return mapper.convertValue(userRepository.findInterbankingUserByToken(token), InterbankingUserDTO.class);
    }

    public String createUser(List<InterbankingCreds> credsList){
        InterbankingUser interbankingUser = new InterbankingUser();
        interbankingUser.setToken(randomStringGenerator.nextString());
        userRepository.save(interbankingUser);
        for(InterbankingCreds creds: credsList){
            creds.setUser(interbankingUser);
            credsRepository.save(creds);
        }
        return interbankingUser.getToken();
    }
}
