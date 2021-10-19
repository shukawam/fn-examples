package com.example.fn;

import dagger.Component;

import javax.inject.Inject;

@Singleton
@Component
public class HelloProvider {

    @Inject
    HelloProvider() {}

    public String say(String input) {
        String name = (input == null || input.isEmpty()) ? "world"  : input;

        System.out.println("Inside Java Hello World function");
        return "Hello, " + name + "!";
    }
}
