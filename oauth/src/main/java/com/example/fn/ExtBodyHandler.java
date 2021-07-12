package com.example.fn;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.io.ByteArrayInputStream;
import java.net.http.HttpResponse;
import java.util.Objects;

public class ExtBodyHandler {
    private static final Jsonb jsonb = JsonbBuilder.create();

    private ExtBodyHandler() {
        // do nothing.
    }

    public static <T>HttpResponse.BodyHandler ofObjectAsJson(final Class<T> type) {
        Objects.requireNonNull(type);
        return (responseInfo) -> HttpResponse.BodySubscribers.mapping(
                HttpResponse.BodySubscribers.ofByteArray(),
                byteArray -> jsonb.fromJson(new ByteArrayInputStream(byteArray), type));
    }
}
