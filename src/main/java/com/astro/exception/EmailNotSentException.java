package com.astro.exception;

import com.astro.constant.AppConstant;

public class EmailNotSentException extends RuntimeException{

    private ErrorDetails errorDetails;

    public EmailNotSentException(String message) {
        super(message);
        this.errorDetails = new ErrorDetails(
                AppConstant.EMAIL_NOT_SENT_ERROR_CODE,
                AppConstant.ERROR_TYPE_CODE_VALIDATION,
                AppConstant.ERROR_TYPE_EMAIL,
                message
        );
    }

    public ErrorDetails getErrorDetails() {
        return errorDetails;
    }

}
