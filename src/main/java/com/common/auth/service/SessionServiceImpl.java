package com.common.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class SessionServiceImpl {

    private Map<String,AuthUser> authUserMap = new HashMap<>();

    public void addAuthMap(AuthUser authUser) {
        authUserMap.put(authUser.getUsername(),authUser);
    }
    public void removeAuthMap(AuthUser authUser) {
        authUserMap.remove(authUser.getUsername());
    }

    public AuthUser getAuthUser(String userName) {
        return  authUserMap.get(userName);
    }

}
