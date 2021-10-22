package com.example.fn;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

/**
 * @author shukawam
 */
@Singleton
public final class HelloLogger {
    private final List<String> logs = new ArrayList<>();

    @Inject
    HelloLogger() {
    }

    public void log(String msg) {
        logs.add(msg);
    }

    public List<String> logs() {
        return new ArrayList<>(logs);
    }
}
