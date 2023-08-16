package com.wantedpreonboardingbackend.dto;

public record Response<T>(
        String resultCode,
        String resultMessage,
        T result
) {

    public static Response<Void> error(String errorCode) {
        return new Response<>(errorCode, null, null);
    }

    public static Response<Void> error(String errorCode, String errorMessage) {
        return new Response<>(errorCode, errorMessage, null);
    }

    public static <T> Response<T> success() {
        return new Response<>("SUCCESS", null, null);
    }

    public static <T> Response<T> success(T result) {
        return new Response<>("SUCCESS", null, result);
    }

    public String toStream() {

        if (result == null) {
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"resultMessage\":" + "\"" + resultMessage + "\"," +
                    "\"result\":" + null + "}";
        }

        return "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"resultMessage\":" + "\"" + resultMessage + "\"," +
                "\"result\"" + "\"" + result + "\""+ "}";
    }
}
