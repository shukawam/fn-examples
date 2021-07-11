package com.example.fn;

import com.example.fn.data.AuthorizerRequest;
import com.example.fn.data.AuthorizerResponse;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class HelloFunction {
    private static final String TYPE = "TOKEN";
    private static final String TOKEN_PREFIX = "Basic ";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssXXX";

    // Simple Http-basic authentication.
    private static final String USER = "guest";
    private static final String PASSWORD = "password#123";

    public AuthorizerResponse handleRequest(AuthorizerRequest authorizerRequest) {
        // request validation
        if (!TYPE.equals(authorizerRequest.getType()) || authorizerRequest.getType() == null) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Basic realm=\"Input type is invalid.\"");
            return response;
        }
        if ("".equals(authorizerRequest.getToken()) ||
                authorizerRequest.getToken() == null ||
                !authorizerRequest.getToken().startsWith(TOKEN_PREFIX)) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Basic realm=\"Input token is invalid.\"");
            return response;
        }
        // token validation
        var credential = new String(Base64.getUrlEncoder().encode(String.format("%s:%s", USER, PASSWORD).getBytes(StandardCharsets.UTF_8)));
        var inputToken = authorizerRequest.getToken().substring(TOKEN_PREFIX.length());
        if (!credential.equals(inputToken)) {
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Basic realm=\"Username or password is wrong.\"");
            return response;
        }
        var response = new AuthorizerResponse();
        response.setActive(true);
        var user = new String(Base64.getUrlDecoder().decode(inputToken)).split(":")[0];
        response.setPrincipal(user);
        response.setScope(new String[]{"list: hello", "read: hello", "create: hello", "update: hello", "delete: hello"});
        response.setExpiresAt(new SimpleDateFormat(DATE_FORMAT).format(new Date()));
        return null;
    }

}