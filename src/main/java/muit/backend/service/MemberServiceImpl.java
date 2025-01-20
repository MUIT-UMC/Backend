package muit.backend.service;

import lombok.RequiredArgsConstructor;
import muit.backend.converter.MemberConverter;
import muit.backend.domain.entity.member.Member;
import muit.backend.dto.memberDTO.EmailRegisterRequestDTO;
import muit.backend.dto.memberDTO.EmailRegisterResponseDTO;
import muit.backend.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public EmailRegisterResponseDTO emailSignUp(EmailRegisterRequestDTO dto){

        if (memberRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalStateException("이미 사용 중인 이메일입니다.");
        }
        if (!dto.getPw().equals(dto.getPw_check())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        Member member = MemberConverter.EmailtoMember(dto);
        memberRepository.save(member);

        return MemberConverter.MemberToEmailRegisterResponseDTO(member);

    }


}
