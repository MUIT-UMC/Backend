package muit.backend.apiPayLoad.code;

//실패했을 떄
public interface BaseErrorCode {

    public ReasonDTO getErrorReason();

    public ReasonDTO getErrorReasonHttpStatus();

}