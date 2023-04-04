package com.jmp.security.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jmp.security.cache.CachedValue;
import com.jmp.security.service.BruteforceHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

@Service
public class BruteforceHandlerImpl implements BruteforceHandler {

    @Value("${app.restrict.max-attempt-login}")
    private int maxAttempt;

    @Value("${app.blocking.duration.sec}")
    private int blockedDurationSec;

    private LoadingCache<String, CachedValue> blockedUsers;

    @PostConstruct
    public void initCache() {
        blockedUsers = CacheBuilder.newBuilder()
                .expireAfterWrite(blockedDurationSec, TimeUnit.SECONDS)
                .build(new CacheLoader<>() {
                    @Override
                    public CachedValue load(String key) {
                        return new CachedValue(0, LocalDateTime.now());
                    }
                });
    }

    @Override
    public void onLoginFailed(String key) {
        CachedValue cachedValue = new CachedValue();
        try {
            cachedValue = blockedUsers.get(key);
            cachedValue.setAttempts(cachedValue.getAttempts() + 1);
        } catch (ExecutionException e) {
            cachedValue.setAttempts(0);
        }
        if (isBlocked(key) && isNull(cachedValue.getBlockedTimestamp())) {
            cachedValue.setBlockedTimestamp(LocalDateTime.now());
        }
        blockedUsers.put(key, cachedValue);
    }

    @Override
    public CachedValue getCachedValue(String key) {
        return blockedUsers.getUnchecked(key);
    }

    @Override
    public boolean isBlocked(String key) {
        try {
            return blockedUsers.get(key).getAttempts() >= maxAttempt;
        } catch (ExecutionException e) {
            return false;
        }
    }

}
