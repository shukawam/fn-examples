package com.example.fn;

import com.fnproject.fn.api.InputEvent;
import com.fnproject.fn.api.httpgateway.HTTPGatewayContext;

public class HelloFunction {

    public String handleRequest(HTTPGatewayContext ctx) {
        return ctx.getRequestURL();
    }

//    private Pet getPetById(int id) {
//        return new null;
//    }

}