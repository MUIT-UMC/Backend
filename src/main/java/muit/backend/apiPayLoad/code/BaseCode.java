package muit.backend.apiPayLoad.code;

//성공했을 떄
public interface BaseCode {

    public ReasonDTO getReason();  //일반적인 이유 반환

    public ReasonDTO getReasonHttpStatus(); //HTTP 상태 코드와 함께 이유를 반환
}