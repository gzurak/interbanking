package org.zuratech.interbankingApi.services;

import org.springframework.stereotype.Service;

@Service
public class DemoService {
    public String getString(){
        return "Demo";
    }
    public String postString(String string){
        return string;
    }
}
