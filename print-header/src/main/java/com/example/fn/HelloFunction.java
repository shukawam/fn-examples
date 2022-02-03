package com.example.fn;

import com.fnproject.fn.api.InputEvent;

import java.util.logging.Logger;

public class HelloFunction {
    private static final Logger logger = Logger.getLogger(HelloFunction.class.getName());

    public String handleRequest(String input, InputEvent inputEvent) {
        String name = (input == null || input.isEmpty()) ? "world" : input;
        // simple header logging
        printXFFHeader(inputEvent);
        System.out.println("Inside Java Hello World function");
        return "Hello, " + name + "!";
    }

    private static void printXFFHeader(InputEvent inputEvent) {
        inputEvent.getHeaders().get("Fn-Http-H-X-Forwarded-For").ifPresent(header -> {
            logger.info("X-Forwarded-For: " + header);
        });
    }

}