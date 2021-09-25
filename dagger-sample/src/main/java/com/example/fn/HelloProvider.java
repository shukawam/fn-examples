package com.example.fn;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class HelloProvider {

    @Inject
    HelloProvider() {}

    public String say(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;

        System.out.println("Inside Java Hello World function");
        return "Hello, " + name + "!";
    }
}
