package com.example.fn.interceptor;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shukawam
 */
public class ApmTraceInterceptor implements MethodInterceptor {
    private static final Logger logger = Logger.getLogger(ApmTraceInterceptor.class.getName());
    private Tracer tracer;
    private TraceContext traceContext;

    public ApmTraceInterceptor(Tracer tracer, TraceContext traceContext) {
        this.tracer = tracer;
        this.traceContext = traceContext;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // Before proceed
        logger.log(Level.INFO, "Before proceed: " + methodInvocation.getMethod().getName());
        Span span = tracer.newChild(traceContext)
                .name(methodInvocation.getMethod().getName()).start();
        // invoke the method
        Object result = methodInvocation.proceed();
        // After proceed
        logger.log(Level.INFO, "After proceed: " + methodInvocation.getMethod().getName());
        span.finish();
        return result;
    }
}
