package com.example.fn;

import com.example.fn.annotations.ApmTrace;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shukawam
 */
@ApmTrace
public class GreetService {
    private static Logger logger = Logger.getLogger(GreetService.class.getName());

    public String say(String input) {
        return "Hello world!";
    }

    public void method1() {
        logger.log(Level.INFO, "Inside Method1 function");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method2() {
        logger.log(Level.INFO, "Inside Method2 function");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void method3() {
        logger.log(Level.INFO, "Inside Method3 function");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
