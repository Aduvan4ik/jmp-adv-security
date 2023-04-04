package com.jmp.security.service;


import com.jmp.security.cache.CachedValue;

public interface BruteforceHandler {

    boolean isBlocked(String key);

    void onLoginFailed(String key);

    CachedValue getCachedValue(String key);

}
