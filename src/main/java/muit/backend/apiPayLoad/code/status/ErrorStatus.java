package muit.backend.apiPayLoad.code.status;

import muit.backend.apiPayLoad.code.BaseErrorCode;
import muit.backend.apiPayLoad.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    //일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다. 로그인 정보를 확인해주세요."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),
    _NOT_FOUND(HttpStatus.NOT_FOUND, "COMMON404", "요청한 리소스를 찾을 수 없습니다"),
    _WRONG_PARAMETER(HttpStatus.BAD_REQUEST, "COMMON405", "잘못된 파라미터입니다."),

    //EMAIL ERROR
    EMAIL_FORMAT_ERROR(HttpStatus.NOT_FOUND, "EMAIL4000", "잘못된 이메일 형식입니다."),
    EMAIL_ALREADY_EXIST(HttpStatus.NOT_FOUND, "EMAIL4001", "이미 존재하는 이메일입니다."),
    INVALID_CODE(HttpStatus.NOT_FOUND, "EMAIL4010", "인증코드가 일치하지 않습니다."),

    //APPLICANT ERROR
    APPLICANT_NOT_FOUND(HttpStatus.NOT_FOUND, "APPLICANT4000", "존재하지 않는 회원입니다."),

    // MEMBER ERROR
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "MEMBER4000", "존재하지 않는 사용자입니다."),

    //JOIN ERROR
    JOIN_FORMAT_ERROR(HttpStatus.BAD_REQUEST, "JOIN4000", "아이디와 비밀번호는 필수 입력 값입니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "JOIN4001", "이미 존재하는 아이디입니다"),

    // AMATEURSHOW ERROR
    AMATEURSHOW_NOT_FOUND(HttpStatus.NOT_FOUND, "AMATEURSHOW4000", "존재하지 않는 소극장 공연입니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    //Error
    @Override
    public ReasonDTO getErrorReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ReasonDTO getErrorReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }

}
