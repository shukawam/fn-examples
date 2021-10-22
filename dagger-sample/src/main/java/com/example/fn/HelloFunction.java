package com.example.fn;

import javax.inject.Inject;

public class HelloFunction {
    private final HelloProvider provider;

    @Inject
    public HelloFunction(HelloProvider provider) {
        this.provider = provider;
    }

    public String handleRequest(String input) {
        return provider.say(input);
    }

}