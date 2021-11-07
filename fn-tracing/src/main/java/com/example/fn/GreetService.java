package com.example.fn;

import com.example.fn.annotations.ApmTrace;
import com.fnproject.fn.api.tracing.TracingContext;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shukawam
 */
@ApmTrace
public class GreetService {
    private static Logger logger = Logger.getLogger(GreetService.class.getName());

    public String say(String input, TracingContext tracingContext) {
        return "Hello, AppName " + tracingContext.getAppName() + " :: fnName " + tracingContext.getFunctionName();
    }

    public void method1() {
        logger.log(Level.INFO, "Inside Method1 function");
    }

    public void method2() {
        logger.log(Level.INFO, "Inside Method2 function");
    }

    public void method3() {
        logger.log(Level.INFO, "Inside Method3 function");
    }
}
