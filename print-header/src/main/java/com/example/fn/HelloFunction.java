package com.example.fn;

import com.fnproject.fn.api.InputEvent;

import java.util.logging.Logger;

public class HelloFunction {
    private static final Logger logger = Logger.getLogger(HelloFunction.class.getName());

    public String handleRequest(String input, InputEvent inputEvent) {
        String name = (input == null || input.isEmpty()) ? "world" : input;
        // simple header logging
        printHeader(inputEvent);
        System.out.println("Inside Java Hello World function");
        return "Hello, " + name + "!";
    }

    private static void printHeader(InputEvent inputEvent) {
        inputEvent.getHeaders().asMap().forEach((key, value) -> {
            logger.info(String.format("Header %s: %s", key, value));
        });
    }

}