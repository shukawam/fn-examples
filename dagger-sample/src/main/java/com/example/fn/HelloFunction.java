package com.example.fn;

import javax.inject.Inject;

public class HelloFunction {
    private final HelloProvider helloProvider;

    @Inject
    public HelloFunction(HelloProvider helloProvider) {
        this.helloProvider = helloProvider;
    }

    public String handleRequest(String input) {
        return helloProvider.say(input);
    }

}