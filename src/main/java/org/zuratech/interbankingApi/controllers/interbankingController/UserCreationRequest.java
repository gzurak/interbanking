package org.zuratech.interbankingApi.controllers.interbankingController;

import org.zuratech.interbankingApi.models.interbankingUser.InterbankingCreds;

import java.util.List;

public class UserCreationRequest {
    private List<InterbankingCreds> creds;

    public List<InterbankingCreds> getCreds() {
        return creds;
    }

    public void setCreds(List<InterbankingCreds> creds) {
        this.creds = creds;
    }
}
