package com.example.fn.data;

import java.util.Map;

public class AuthorizerResponse {
    // required
    private boolean active;
    private String principal;
    private String[] scope;
    private String expiresAt;
    // optional
    private String wwwAuthenticate;
    private String clientId;
    private Map<String, Object> context;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPrincipal() {
        return principal;
    }

    public void setPrincipal(String principal) {
        this.principal = principal;
    }

    public String[] getScope() {
        return scope;
    }

    public void setScope(String[] scope) {
        this.scope = scope;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getWwwAuthenticate() {
        return wwwAuthenticate;
    }

    public void setWwwAuthenticate(String wwwAuthenticate) {
        this.wwwAuthenticate = wwwAuthenticate;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContext(Map<String, Object> context) {
        this.context = context;
    }
}