package main.java.enums;

public enum HttpMethods {
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    @Override
    public String toString() {
        return methodName;
    }

    HttpMethods(String methodName) {
        this.methodName = methodName;
    }

    private final String methodName;



}
