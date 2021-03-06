package com.example.fn;

import com.example.fn.data.AuthorizerRequest;
import com.example.fn.data.AuthorizerResponse;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class HelloFunction {
    private static final String TYPE = "TOKEN";
    private static final String FN_API_KEY = System.getenv("FN_API_KEY");
    private static final DateTimeFormatter ISO8601 = DateTimeFormatter.ISO_DATE_TIME;

    public AuthorizerResponse handleRequest(AuthorizerRequest authorizerRequest) {
        // request validation
        if (!TYPE.equals(authorizerRequest.getType()) || authorizerRequest.getType() == null) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Input type is invalid.");
            return response;
        }
        // token validation
        if ("".equals(authorizerRequest.getToken()) ||
                authorizerRequest.getToken() == null ||
                !FN_API_KEY.equals(authorizerRequest.getToken())) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Input token is invalid.");
            return response;
        }
        // create response ... 4
        var response = new AuthorizerResponse();
        response.setActive(true);
        response.setPrincipal("foo");
        response.setScope(new String[]{"list:hello", "read:hello", "create:hello", "update:hello", "delete:hello"});
        var now = new Date();
        var expiresAt = Calendar.getInstance();
        expiresAt.setTime(now);
        expiresAt.add(Calendar.MINUTE, 10);
        response.setExpiresAt(ISO8601.format(expiresAt.getTime().toInstant().atOffset(ZoneOffset.UTC)));
        return response;
    }

}