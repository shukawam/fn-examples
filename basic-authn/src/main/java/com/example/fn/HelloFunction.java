package com.example.fn;

import com.example.fn.data.AuthorizerRequest;
import com.example.fn.data.AuthorizerResponse;
import com.fnproject.fn.api.FnConfiguration;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class HelloFunction {
    private static final String TYPE = "TOKEN";
    private static final String TOKEN_PREFIX = "Basic ";
    private static final DateTimeFormatter ISO8601 = DateTimeFormatter.ISO_DATE_TIME;

    // Simple Http-basic authentication.
    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASSWORD = "password#123";

    @FnConfiguration
    public void init() {
        System.out.println(String.format("Docker Host: %s", System.getenv("DOCKER_HOST")));
    }

    public AuthorizerResponse handleRequest(AuthorizerRequest authorizerRequest) {
        // request validation
        if (!TYPE.equals(authorizerRequest.getType()) || authorizerRequest.getType() == null) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Basic realm=\"foo\"");
            return response;
        }
        if ("".equals(authorizerRequest.getToken()) ||
                authorizerRequest.getToken() == null ||
                !authorizerRequest.getToken().startsWith(TOKEN_PREFIX)) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Basic realm=\"foo\"");
            return response;
        }
        // token validation
        var adminCredential = new String(Base64.getUrlEncoder().encode(String.format("%s:%s", ADMIN_USER, ADMIN_PASSWORD)
                .getBytes(StandardCharsets.UTF_8)));
        var inputToken = authorizerRequest.getToken().substring(TOKEN_PREFIX.length());
        if (!adminCredential.equals(inputToken)) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Basic realm=\"foo\"");
            return response;
        }
        var response = createResponse(inputToken);
        return response;
    }

    private AuthorizerResponse createResponse(String inputToken) {
        var response = new AuthorizerResponse();
        response.setActive(true);
        var user = new String(Base64.getUrlDecoder().decode(inputToken)).split(":")[0];
        response.setPrincipal(user);
        response.setScope(new String[]{"list:hello", "read:hello", "create:hello", "update:hello", "delete:hello"});
        var now = new Date();
        var expiresAt = Calendar.getInstance();
        expiresAt.setTime(now);
        expiresAt.add(Calendar.MINUTE, 10);
        response.setExpiresAt(ISO8601.format(expiresAt.getTime().toInstant().atOffset(ZoneOffset.UTC)));
        return response;
    }

}