package muit.backend.apiPayLoad.exception;

import muit.backend.apiPayLoad.code.BaseErrorCode;
import muit.backend.apiPayLoad.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException{
    private BaseErrorCode code;

    public ReasonDTO getErrorReason() {
        return this.code.getErrorReason();
    }

    public ReasonDTO getErrorReasonHttpStatus(){
        return this.code.getErrorReasonHttpStatus();
    }
}
