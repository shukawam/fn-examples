package com.example.fn.interceptor;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
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
    private Tracing tracing;

    public ApmTraceInterceptor(Tracer tracer, Tracing tracing) {
        this.tracer = tracer;
        this.tracing = tracing;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // Before proceed
        TraceContext traceContext = tracing.currentTraceContext().get();
        beforeProceed(methodInvocation, traceContext);
        Span span = tracer.newChild(traceContext)
                .name(methodInvocation.getMethod().getName()).start();
        // invoke the method
        Object result = methodInvocation.proceed();
        // After proceed
        afterProceed(methodInvocation);
        span.finish();
        return result;
    }

    private void beforeProceed(MethodInvocation methodInvocation, TraceContext traceContext) {
        logger.log(Level.INFO, "Before proceed: " + methodInvocation.getMethod().getName());
        logger.log(Level.INFO, "traceContext.traceId: " + traceContext.traceIdString());
        logger.log(Level.INFO, "traceContext.parentId: " + traceContext.parentIdString());
        logger.log(Level.INFO, "traceContext.spanId: " + traceContext.spanIdString());
    }

    private void afterProceed(MethodInvocation methodInvocation) {
        logger.log(Level.INFO, "After proceed: " + methodInvocation.getMethod().getName());
    }
}
