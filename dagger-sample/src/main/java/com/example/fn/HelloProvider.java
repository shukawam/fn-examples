package com.example.fn;

import javax.inject.Singleton;

@Singleton
public class HelloProvider {

    public String say(String input) {
        String name = (input == null || input.isEmpty()) ? "world" : input;
        return "Hello, " + name + "!";
    }
}
