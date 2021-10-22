package com.example.fn;

import dagger.Component;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HelloProvider {

    private final HelloLogger logger;

    @Inject
    public HelloProvider(HelloLogger logger) {
        this.logger = logger;
    }

    public String say(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;
        logger.log("Inside Java Hello World function");
        return "Hello, " + name + "!";
    }
}
