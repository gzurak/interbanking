package org.zuratech.interbankingApi.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/")
public class PingController {
    @GetMapping("/ping")
    public @ResponseBody String getDemoValue(){
        return "PONG";
    }

}
