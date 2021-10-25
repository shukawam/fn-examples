package com.example.fn;

import dagger.Module;

/**
 * @author shukawam
 */
@Module
public class HelloModule {
    public HelloProvider newHelloProvider() {
        return new HelloProvider();
    }
}
