package com.span.score.exception;

public class ScorecardException  extends Exception{

    private String errorCode;
    private String errorMessage;
    public ScorecardException(String errorMessage){
        super(errorMessage);
    }

    public ScorecardException(String errorCode, String errorMessage){
        super(errorMessage);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
