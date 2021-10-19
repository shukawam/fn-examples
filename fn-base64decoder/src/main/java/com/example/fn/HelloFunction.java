package com.example.fn;

import com.example.fn.data.NotificationInput;
import com.example.fn.data.StreamingInput;

import java.util.Base64;

public class HelloFunction {

    public NotificationInput handleRequest(StreamingInput input) {
        String decodedValue = new String(Base64.getDecoder().decode(input.value));
        NotificationInput notificationInput = new NotificationInput();
        notificationInput.stream = input.stream;
        notificationInput.value = decodedValue;
        notificationInput.timestamp = input.timestamp;
        return notificationInput;
    }

}