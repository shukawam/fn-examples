package com.example.fn.interceptor;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import com.example.fn.HelloFunction;
import com.fnproject.fn.api.tracing.TracingContext;
import com.github.kristofa.brave.IdConversion;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author shukawam
 */
public class ApmTraceInterceptor implements MethodInterceptor {
    private static final Logger logger = Logger.getLogger(ApmTraceInterceptor.class.getName());
    private String apmUrl;
    private Sender sender;
    private AsyncZipkinSpanHandler zipkinSpanHandler;
    private Tracing tracing;
    private Tracer tracer;
    private TraceContext traceContext;

    public ApmTraceInterceptor(TracingContext tracingContext) {
        initializeZipkin(tracingContext);
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        // Before proceed
        Span span = tracer.newChild(traceContext)
                .name(methodInvocation.getMethod().getName()).start();
        // invoke the method
        Object result = methodInvocation.proceed();
        // After proceed
        span.finish();
        return result;
    }

    private void initializeZipkin(TracingContext tracingContext) {
        logger.log(Level.INFO, "Initializing the variables");
        apmUrl = tracingContext.getTraceCollectorURL();
        logger.log(Level.INFO, "Apm URL: " + apmUrl);
        sender = URLConnectionSender.create(apmUrl);
        logger.log(Level.INFO, "Sender: " + sender);
        zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);
        logger.log(Level.INFO, "ZipkinSpanHandler: " + zipkinSpanHandler);
        tracing = Tracing.newBuilder()
                .localServiceName(tracingContext.getServiceName())
                .addSpanHandler(zipkinSpanHandler)
                .build();
        logger.log(Level.INFO, "Tracing: " + tracing);
        tracer = tracing.tracer();
        logger.log(Level.INFO, "Tracer: " + tracer);
        tracing.setNoop(!tracingContext.isTracingEnabled());
        traceContext = TraceContext.newBuilder()
                .traceId(IdConversion.convertToLong(tracingContext.getTraceId()))
                .spanId(IdConversion.convertToLong(tracingContext.getSpanId()))
                .sampled(tracingContext.isSampled())
                .build();
        logger.log(Level.INFO, "TraceContext: " + traceContext);
    }
}
