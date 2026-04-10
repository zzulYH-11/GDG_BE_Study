package com.example.shop.member;

import com.example.shop.member.dto.MemberCreateRequest;
import com.example.shop.member.dto.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long createMember(MemberCreateRequest request) {

        //이미 존재하는지 확인
        Member existingMember = memberRepository.findByLoginId(request.getLoginId());
        if(existingMember != null){
            throw new RuntimeException("이미 존재하는 로그인 아이디입니다. : " + request.getLoginId());
        }

        //저장
        Member member = new Member(request.getLoginId(),request.getPassword(),request.getPhoneNumber(),request.getAddress());
        memberRepository.save(member);

        return member.getId();
    }

    @Transactional(readOnly = true)
    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member getMember(Long id) {
        Member member = memberRepository.findById(id);

        //실제로 존재하는 id인지 분기처리
        if (member == null) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        return member;
    }

    @Transactional
    public void updateMember(Long id, MemberUpdateRequest request) {

        Member member = memberRepository.findById(id);

        //존재하는 회원인지 확인
        if (member == null) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        //도메인 객체의 메서드를 활용해 정보 수정
        member.updateInfo(request.getPassword(), request.getPhoneNumber(), request.getAddress());
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id);

        //존재하는 회원인지 확인
        if (member == null) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        memberRepository.deleteById(id);
    }


}
