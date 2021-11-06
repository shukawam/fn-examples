package com.example.fn;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
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

@ApmTrace
public class HelloFunction {
    static final Logger logger = Logger.getLogger(HelloFunction.class.getName());
    String apmUrl;
    Sender sender;
    AsyncZipkinSpanHandler zipkinSpanHandler;
    Tracing tracing;
    Tracer tracer;
    TraceContext traceContext;
    @Inject
    private GreetService greetService;

    public String handleRequest(String input, TracingContext tracingContext) {
        try {
            initializeZipkin(tracingContext);
            // Start a new tracer or a span within an existing trace representing an operation.
            Span span = tracer.newChild(traceContext).name("MainHandle").start();
            logger.log(Level.INFO, "Inside Java Hello World function");
            System.out.println("isTracingEnabled: " + tracingContext.isTracingEnabled());
            try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
                method1();
                method2();
                method3();
            } catch (RuntimeException e) {
                span.error(e); // Unless you handle exceptions, you might not know the operation failed!
                throw e;
            } finally {
                span.finish(); // note the scope is independent of the span. Always finish a span.
                tracing.close();
                zipkinSpanHandler.close();
            }
        } catch (Exception e) {
            return e.getMessage();
        }
        Injector injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bindInterceptor(Matchers.annotatedWith(ApmTrace.class), Matchers.any(), new ApmTraceInterceptor());
            }
        });
        HelloFunction helloFunction = injector.getInstance(HelloFunction.class);
        return helloFunction.greetService.say(input, tracingContext);
//        return "Hello, AppName " + tracingContext.getAppName() + " :: fnName " + tracingContext.getFunctionName();
    }

    private void initializeZipkin(TracingContext tracingContext) {
        logger.log(Level.INFO, "Initializing the variables");
        apmUrl = tracingContext.getTraceCollectorURL();
        sender = URLConnectionSender.create(apmUrl);
        zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);
        tracing = Tracing.newBuilder()
                .localServiceName(tracingContext.getServiceName())
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

    private void method1() {
        logger.log(Level.INFO, "Inside Method1 function");
        TraceContext traceContext = tracing.currentTraceContext().get();
        Span span = tracer.newChild(traceContext).name("Method1").start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }

    private void method2() {
        logger.log(Level.INFO, "Inside Method2 function");
        TraceContext traceContext = tracing.currentTraceContext().get();
        Span span = tracer.newChild(traceContext).name("Method2").start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }

    private void method3() {
        logger.log(Level.INFO, "Inside Method3 function");
        TraceContext traceContext = tracing.currentTraceContext().get();
        Span span = tracer.newChild(traceContext).name("Method3").start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            span.finish();
        }
    }
}