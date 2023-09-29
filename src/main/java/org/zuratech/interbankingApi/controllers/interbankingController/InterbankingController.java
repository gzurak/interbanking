package org.zuratech.interbankingApi.controllers.interbankingController;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingUser;
import org.zuratech.interbankingApi.models.interbankingUser.InterbankingUserDTO;
import org.zuratech.interbankingApi.services.interbankingService.InterbankingService;
import org.zuratech.interbankingApi.services.interbankingService.saldos.MultipleAccountBalancesResponse;
import org.zuratech.interbankingApi.services.interbankingService.saldos.Saldos;
import org.zuratech.interbankingApi.services.userService.UserService;

@RestController
@RequestMapping(path = "/api")
public class InterbankingController {

    private final UserService userService;
    private final InterbankingService interbankingService;

    public InterbankingController(UserService userService, InterbankingService interbankingService) {
        this.userService = userService;
        this.interbankingService = interbankingService;
    }

    @GetMapping("/user")
    public @ResponseBody ResponseEntity<InterbankingUserDTO> getCurrentBalance(@RequestParam String token){
        try{
            return ResponseEntity.ok(userService.getUserByToken(token));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/user")
    public @ResponseBody ResponseEntity<String> createUser(@RequestBody UserCreationRequest request){
        try{
            return ResponseEntity.ok(userService.createUser(request.getCreds()));
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
    private static final Logger log = LoggerFactory.getLogger(InterbankingController.class);

    @GetMapping("/saldos")
    public @ResponseBody ResponseEntity<MultipleAccountBalancesResponse> obtenerSaldosHoy(@RequestParam String token){
        try{
            InterbankingUserDTO user = userService.getUserByToken(token);
            return ResponseEntity.ok(interbankingService.getBalancesForMultipleAccounts(user.getCredentials()));
        }catch (Exception e){
            log.error("Error obtaining balances", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}
