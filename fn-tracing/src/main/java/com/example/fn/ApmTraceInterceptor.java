package com.example.fn;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 * @author shukawam
 */
public class ApmTraceInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println("before proceed");
        Object result = methodInvocation.proceed(); // invoke the method
        System.out.println("after proceed");
        return result;
    }
}
