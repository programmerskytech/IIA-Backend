package com.astro.exception;

public class FilesNotFoundException extends RuntimeException {
    private ErrorDetails errorDetails;
    private Throwable throwable;

    public FilesNotFoundException(ErrorDetails errorDetails,Throwable throwable) {

        super();
        this.errorDetails = errorDetails;
        this.throwable = throwable;
    }

    public FilesNotFoundException(ErrorDetails errorDetails) {
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