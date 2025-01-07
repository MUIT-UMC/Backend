package muit.backend.apiPayLoad.exception;

import muit.backend.apiPayLoad.code.BaseErrorCode;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode code) {
        super(code);
    }
}