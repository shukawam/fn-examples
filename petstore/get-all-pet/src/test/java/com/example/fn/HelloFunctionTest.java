package com.example.fn;

import com.fnproject.fn.testing.*;
import org.junit.*;

import static org.junit.Assert.*;

public class HelloFunctionTest {

    @Rule
    public final FnTestingRule testing = FnTestingRule.createDefault();

    @Test
    public void shouldReturnGreeting() {
        testing.givenEvent().enqueue();
        testing.thenRun(HelloFunction.class, "handleRequest");

        FnResult result = testing.getOnlyResult();
        String expected = "[{\"id\":1,\"name\":\"dog\"},{\"id\":2,\"name\":\"cat\"},{\"id\":3,\"name\":\"bird\"},{\"id\":4,\"name\":\"fish\"},{\"id\":5,\"name\":\"snake\"}]";
        assertEquals(expected, result.getBodyAsString());
    }

}