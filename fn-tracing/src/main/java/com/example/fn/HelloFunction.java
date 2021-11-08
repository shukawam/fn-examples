package com.example.fn;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import brave.sampler.Sampler;
import com.example.fn.annotations.ApmTrace;
import com.example.fn.interceptor.ApmTraceInterceptor;
import com.fnproject.fn.api.InvocationContext;
import com.fnproject.fn.api.tracing.TracingContext;
import com.github.kristofa.brave.IdConversion;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import zipkin2.reporter.Sender;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.urlconnection.URLConnectionSender;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloFunction {
    private static final Logger logger = Logger.getLogger(HelloFunction.class.getName());
    private String apmUrl;
    private Sender sender;
    private AsyncZipkinSpanHandler zipkinSpanHandler;
    private Tracing tracing;
    private Tracer tracer;
    private TraceContext traceContext;
    @Inject
    private GreetService greetService;

    public String handleRequest(String input, TracingContext tracingContext, InvocationContext invocationContext) {
        loggingFunctionsHeader(invocationContext);
        loggingTracingContext(tracingContext);
        try {
            initializeZipkin(tracingContext);
            // Start a new tracer or a span within an existing trace representing an operation.
            Span span = tracer.newChild(traceContext).name("MainHandle").start();
            Injector injector = Guice.createInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    bindInterceptor(Matchers.annotatedWith(ApmTrace.class),
                            Matchers.any(),
                            new ApmTraceInterceptor(tracer, tracing)
                    );
                }
            });
            HelloFunction helloFunction = injector.getInstance(HelloFunction.class);
            try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
                helloFunction.greetService.method1();
                helloFunction.greetService.method2();
                helloFunction.greetService.method3();
            } catch (RuntimeException e) {
                // Unless you handle exceptions, you might not know the operation failed!
                span.error(e);
                throw e;
            } finally {
                // note the scope is independent of the span. Always finish a span.
                span.finish();
                tracing.close();
                zipkinSpanHandler.close();
            }
            return helloFunction.greetService.say(input);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void initializeZipkin(TracingContext tracingContext) {
        logger.log(Level.INFO, "Initializing the variables");
        apmUrl = tracingContext.getTraceCollectorURL();
        sender = URLConnectionSender.create(apmUrl);
        zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);
        tracing = Tracing.newBuilder()
                .localServiceName(tracingContext.getServiceName())
                .sampler(Sampler.NEVER_SAMPLE)
                .addSpanHandler(zipkinSpanHandler)
                .build();
        tracer = tracing.tracer();
        tracing.setNoop(!tracingContext.isTracingEnabled());
        traceContext = TraceContext.newBuilder()
                .traceId(IdConversion.convertToLong(tracingContext.getTraceId()))
                .spanId(IdConversion.convertToLong(tracingContext.getSpanId()))
                .sampled(tracingContext.isSampled())
                .build();
    }

    private void loggingTracingContext(TracingContext tracingContext) {
        logger.log(Level.INFO, "tracingContext.functionName: " + tracingContext.getFunctionName());
        logger.log(Level.INFO, "tracingContext.appName: " + tracingContext.getAppName());
        logger.log(Level.INFO, "tracingContext.traceId: " + tracingContext.getTraceId());
        logger.log(Level.INFO, "tracingContext.serviceName: " + tracingContext.getServiceName());
        logger.log(Level.INFO, "tracingContext.spanId: " + tracingContext.getSpanId());
        logger.log(Level.INFO, "tracingContext.flags: " + tracingContext.getFlags());
        logger.log(Level.INFO, "tracingContext.parentSpanId: " + tracingContext.getParentSpanId());
        logger.log(Level.INFO, "tracingContext.traceCollectorUrl: " + tracingContext.getTraceCollectorURL());
        logger.log(Level.INFO, "tracingContext.tracingEnabled: " + tracingContext.isTracingEnabled());
        logger.log(Level.INFO, "tracingContext.sampled: " + tracingContext.isSampled());
    }

    private void loggingFunctionsHeader(InvocationContext invocationContext) {
        invocationContext.getRequestHeaders().asMap().forEach((key, value) -> {
            logger.log(Level.INFO, "key: " + key + " value: " + value);
        });
    }
}