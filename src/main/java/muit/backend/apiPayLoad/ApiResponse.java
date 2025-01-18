package muit.backend.apiPayLoad;

import muit.backend.apiPayLoad.code.BaseCode;
import muit.backend.apiPayLoad.code.BaseErrorCode;
import muit.backend.apiPayLoad.code.status.SuccessStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "result"}) //JSON 속성의 순서 지정
public class ApiResponse<T> {

    @JsonProperty("isSuccess")  //JSON에서 해당 속성의 이름 정의
    private final Boolean isSuccess;

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)  //JSON에 포함 여부 결정(ALWAYS, NON_NULL, ABSENT, NON_EMPTY)
    private T result;

    // 성공한 경우
    public static <T> ApiResponse<T> onSuccess(T result) {
        return new ApiResponse<>(true, SuccessStatus._OK.getCode(), SuccessStatus._OK.getMessage(), result);
    }

    //특정 성공 상태 코드가 필요할 때
    public static <T> ApiResponse<T> of(BaseCode code, T result){
        return new ApiResponse<>(true, code.getReasonHttpStatus().getCode() , code.getReasonHttpStatus().getMessage(), result);
    }

    // 실패한 경우
    public static <T> ApiResponse<T> onFailure(String code, String message, T data) {
        return new ApiResponse<>(false, code, message, data);
    }

    public static <T> ApiResponse<T> ofFailure(BaseErrorCode code, T result) {
        return new ApiResponse<>(false, code.getErrorReasonHttpStatus().getCode(), code.getErrorReasonHttpStatus().getMessage(), result);
    }
}
