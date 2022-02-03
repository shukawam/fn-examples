package com.example.fn;

import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;

public class HelloFunction {

    public Pet handleRequest(HTTPGatewayContext ctx) {
        String url = ctx.getRequestURL();
        int id = Integer.parseInt(url.substring(url.lastIndexOf("/") + 1));
        return getPetById(id);
    }

    private static Pet getPetById(int id) {
        switch (id) {
            case 1:
                return new Pet(1, "dog");
            case 2:
                return new Pet(2, "cat");
            case 3:
                return new Pet(3, "bird");
            case 4:
                return new Pet(4, "fish");
            case 5:
                return new Pet(5, "snake");
            default:
                return new Pet(0, "unknown");
        }
    }

}