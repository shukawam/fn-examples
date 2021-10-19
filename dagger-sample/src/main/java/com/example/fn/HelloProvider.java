package com.example.fn;

import javax.inject.Inject;

public final class HelloProvider {

    @Inject
    HelloProvider() {}

    public String say(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;

        System.out.println("Inside Java Hello World function");
        return "Hello, " + name + "!";
    }
}
