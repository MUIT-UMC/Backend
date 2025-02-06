package muit.backend.service;

import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import muit.backend.apiPayLoad.code.status.ErrorStatus;
import muit.backend.apiPayLoad.exception.GeneralException;
import muit.backend.config.jwt.TokenDTO;
import muit.backend.config.jwt.TokenProvider;
import muit.backend.converter.MemberConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.domain.enums.Role;
import muit.backend.dto.memberDTO.*;
import muit.backend.repository.MemberRepository;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    private final TokenProvider tokenProvider;

    //== 개인회원 가입 - 이메일 ==//
    @Override
    @Transactional
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto) {

        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new GeneralException(ErrorStatus.MEMBER_ALREADY_EXIST);
        }
        if (!dto.getPw().equals(dto.getPw_check())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }

        String encodedPw = encoder.encode(dto.getPw());
        Member member = MemberConverter.EmailtoMember(dto, encodedPw);
        memberRepository.save(member);

        return MemberConverter.MemberToEmailRegisterResponseDTO(member);

    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new IllegalStateException("unexpected member"));
    }


    //== 이메일 로그인 == //
    @Override
    public EmailLoginAccessTokenResponse EmailLogin(LoginRequestDTO dto){

        String email = dto.getEmail();
        TokenDTO token = login(dto);
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        return MemberConverter.TokenLoginResponseDTO(token, member);
    }

    // 1. 로그인 메서드
    public TokenDTO login(LoginRequestDTO dto) {

        String clientEmail = dto.getEmail();
        String clientPw = dto.getPw();

        // 해당 이메일이 있는지 검증
        Member member = memberRepository.checkEmail(clientEmail, "MUIT")
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        // 비밀번호 검증
        if (!encoder.matches(clientPw, member.getPassword())) {
            throw new GeneralException(ErrorStatus.PASSWORD_NOT_MATCH);
        }


        // 사용자 인증에 성공하면 JWT 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(clientEmail, null);
        return tokenProvider.generateTokenDto(authentication);
    }

    // == 회원 검증 로직 == //
    @Override
    public Member getMemberByToken(String receivedBearerToken) {
        String token = receivedBearerToken.substring("Bearer ".length()).trim();

        // 토큰이 유효한지 검증
        if (!tokenProvider.validateToken(token)) {
            throw new GeneralException(ErrorStatus.MEMBER_INVALID_CODE);
        }
        log.info("일단 토큰 받기는함. 유효하기도함");

        // 토큰에서 인증 정보를 추출
        Authentication authentication = tokenProvider.getAuthentication(token);
        log.info(" 생성된 Authentication 객체: {}", authentication);

        // 인증 정보에서 사용자 이메일을 가져와 회원 조회
        String email = authentication.getName();
        System.out.println("회원조회 체크 "+email);

        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
    }

    @Override
    public Member getAdminByToken(String receivedBearerToken) {
        String token = receivedBearerToken.substring("Bearer ".length()).trim();
        if (!tokenProvider.validateToken(token)) {
            throw new GeneralException(ErrorStatus.MEMBER_INVALID_CODE);
        }
        Authentication authentication = tokenProvider.getAuthentication(token);
        String email = authentication.getName();
        System.out.println("회원조회 체크 "+email);
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));

        if (member.getRole() != Role.ADMIN) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_ADMIN);
        }

        log.info("관리자 권한 확인 완료 - 이메일: {}", email);
        return member;
    }


    @Override
    public MyPageResponseDTO getMyPage(Long tokenId, Long  memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new GeneralException(ErrorStatus.MEMBER_NOT_FOUND));
        if (!tokenId.equals(memberId)) {
            throw new GeneralException(ErrorStatus.MEMBER_NOT_AUTHORIZED);
        }
        return MyPageResponseDTO.builder()
                .id(memberId)
                .name(member.getName())
                .username(member.getUsername()).build();
    }






}


