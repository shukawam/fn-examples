package com.example.fn;

import com.fnproject.fn.api.tracing.TracingContext;

/**
 * @author shukawam
 */
@ApmTrace
public class GreetService {
    public String say(String input, TracingContext tracingContext) {
        return "Hello, AppName " + tracingContext.getAppName() + " :: fnName " + tracingContext.getFunctionName();
    }
}
