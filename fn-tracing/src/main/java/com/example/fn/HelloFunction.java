package com.example.fn;

import brave.Span;
import brave.Tracer;
import brave.Tracing;
import brave.propagation.TraceContext;
import com.example.fn.annotations.ApmTrace;
import com.example.fn.interceptor.ApmTraceInterceptor;
import com.fnproject.fn.api.tracing.TracingContext;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.matcher.Matchers;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HelloFunction {
    static final Logger logger = Logger.getLogger(HelloFunction.class.getName());
    AsyncZipkinSpanHandler zipkinSpanHandler;
    Tracing tracing;
    Tracer tracer;
    TraceContext traceContext;
    @Inject
    private GreetService greetService;

    public String handleRequest(String input, TracingContext tracingContext) {
        try {
            // Start a new tracer or a span within an existing trace representing an operation.
            Span span = tracer.newChild(traceContext).name("MainHandle").start();
            Injector injector = Guice.createInjector(new AbstractModule() {
                @Override
                protected void configure() {
                    bindInterceptor(Matchers.annotatedWith(ApmTrace.class),
                            Matchers.any(),
                            new ApmTraceInterceptor(tracingContext)
                    );
                }
            });
            HelloFunction helloFunction = injector.getInstance(HelloFunction.class);
            logger.log(Level.INFO, "Inside Java Hello World function");
            try (Tracer.SpanInScope ws = tracer.withSpanInScope(span)) {
                helloFunction.greetService.method1();
                helloFunction.greetService.method2();
                helloFunction.greetService.method3();
            } catch (RuntimeException e) {
                span.error(e); // Unless you handle exceptions, you might not know the operation failed!
                throw e;
            } finally {
                span.finish(); // note the scope is independent of the span. Always finish a span.
                tracing.close();
                zipkinSpanHandler.close();
            }
            return helloFunction.greetService.say(input, tracingContext);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

}