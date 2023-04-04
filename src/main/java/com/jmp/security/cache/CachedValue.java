package com.jmp.security.cache;

import java.time.LocalDateTime;


public class CachedValue {

    private int attempts;
    private LocalDateTime blockedTimestamp;

    public CachedValue(int attempts, LocalDateTime blockedTimestamp) {
        this.attempts = attempts;
        this.blockedTimestamp = blockedTimestamp;
    }

    public CachedValue() {
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public LocalDateTime getBlockedTimestamp() {
        return blockedTimestamp;
    }

    public void setBlockedTimestamp(LocalDateTime blockedTimestamp) {
        this.blockedTimestamp = blockedTimestamp;
    }

    @Override
    public String toString() {
        return "CachedValue{" +
                "attempts=" + attempts +
                ", blockedTimestamp=" + blockedTimestamp +
                '}';
    }
}
