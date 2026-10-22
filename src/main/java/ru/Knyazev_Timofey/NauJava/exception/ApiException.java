package ru.Knyazev_Timofey.NauJava.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiException {
    private String message;

    private ApiException(String message) {
        this.message = message;
    }

    public static ApiException create(String message) {
        return new ApiException(message);
    }

    public static ApiException create(Throwable e) {
        return new ApiException(e.getMessage());
    }
}
