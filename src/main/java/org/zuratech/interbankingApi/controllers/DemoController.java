package org.zuratech.interbankingApi.controllers;

import org.zuratech.interbankingApi.services.DemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/demo")
public class DemoController {
    private static final Logger log = LoggerFactory.getLogger(DemoController.class);

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/")
    public @ResponseBody String getDemoValue(){
        log.info("get demo");
        return demoService.getString();
    }

    @PostMapping("/")
    public String postDemoValue(@RequestBody String demo){
        log.info("post demo");
        return demoService.postString(demo);
    }
}
