package com.astro.exception;

public class UnauthorizedException extends RuntimeException {

    private ErrorDetails errorDetails;
    private Throwable throwable;

    public UnauthorizedException(ErrorDetails errorDetails,Throwable throwable) {
        super();
        this.errorDetails = errorDetails;
        this.throwable = throwable;
    }

    public UnauthorizedException(ErrorDetails errorDetails) {
        super();
        this.errorDetails = errorDetails;
        this.throwable = null;
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}