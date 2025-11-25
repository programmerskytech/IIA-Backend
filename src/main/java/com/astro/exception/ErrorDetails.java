package com.astro.exception;

public class ErrorDetails {
    private int errorCode;
    private int errorTypeCode;
    private String errorType;
    private String message;
    
    public ErrorDetails(int errorCode, int errorTypeCode, String errorType, String message){
        this.errorCode = errorCode;
        this.errorTypeCode = errorTypeCode;
        this.errorType = errorType;
        this.message= message;
    }

   
    public int getErrorCode() {
        return errorCode;
    }
    
    public int getErrorTypeCode() {
        return errorTypeCode;
    }

    public String getErrorType() {
        return errorType;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }


	@Override
	public String toString() {
		return "ErrorDetails [errorCode=" + errorCode + ", errorTypeCode="
				+ errorTypeCode + ", errorType=" + errorType + ", message="
				+ message + "]";
	}

}
