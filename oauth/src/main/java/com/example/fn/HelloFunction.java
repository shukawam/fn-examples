package com.example.fn;

import com.example.fn.data.AuthorizerRequest;
import com.example.fn.data.AuthorizerResponse;
import com.example.fn.data.IdcsIntrospectionResponse;
import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HelloFunction {
    private static final String TYPE = "TOKEN";
    private static final String TOKEN_PREFIX = "Bearer ";
    private static final DateTimeFormatter ISO8601 = DateTimeFormatter.ISO_DATE_TIME;
    private static final String FALSE = "false";

    // from Functions env.
    private static final String IDCS_BASE_ENDPOINT = System.getenv("IDCS_BASE_ENDPOINT");
    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

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
        var credential = new String(Base64.getUrlEncoder().encode(String.format("%s:%s",
                CLIENT_ID, CLIENT_SECRET)
                .getBytes(StandardCharsets.UTF_8)));
        var accessToken = authorizerRequest.getToken().substring(TOKEN_PREFIX.length());
        // token introspection
        HttpClient httpClient = HttpClient.newBuilder().build();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(IDCS_BASE_ENDPOINT + "/oauth2/v1/introspect"))
                .headers("Content-Type", "application/x-www-form-urlencoded",
                        "Authorization", String.format("Basic %s", credential))
                .POST(HttpRequest.BodyPublishers.ofString(String.format("token=%s", accessToken)))
                .build();
        try {
            HttpResponse<String> httpResponse = httpClient.send(
                    httpRequest, HttpResponse.BodyHandlers.ofString());
            var idcsIntrospectionResponse = new Gson().fromJson(httpResponse.body(), IdcsIntrospectionResponse.class);
            if (!idcsIntrospectionResponse.getActive()) {
                var response = new AuthorizerResponse();
                response.setActive(false);
                response.setWwwAuthenticate("Bearer realm=\"Access Token is something wrong.\"");
                return response;
            }
            var response = new AuthorizerResponse();
            response.setActive(true);
            response.setPrincipal(idcsIntrospectionResponse.getSub());
            response.setExpiresAt(ISO8601.format(new Date(idcsIntrospectionResponse.getExp()).toInstant().atOffset(ZoneOffset.UTC)));
            var scopes = idcsIntrospectionResponse.getScope().split(" ");
            response.setScope(scopes);
            Map<String, Object> context = new HashMap<>();
            context.put("path", "pilot");
            response.setContext(context);
            return response;
        } catch (InterruptedException | IOException e) {
            e.printStackTrace();
            var response = new AuthorizerResponse();
            response.setActive(false);
            response.setWwwAuthenticate("Bearer realm=\"Token introspection is failed.\"");
            return response;
        }
    }

}