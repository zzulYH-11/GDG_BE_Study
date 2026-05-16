package com.example.shop.member.Service;

import com.example.shop.member.Entity.Member;
import com.example.shop.member.Repository.MemberRepository;
import com.example.shop.member.DTO.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
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

    // 한 회원을 조회 후 memberId, phoneNumber, address, point를 담은 DTO를 반환
    @Override
    @Transactional(readOnly = true)
    public MemberDetail getMember(Long id) {

        Member member = memberRepository.findById(id);

        // 실제로 존재하는 id인지 확인
        if (member == null) { throw new RuntimeException("회원을 찾을 수 없습니다.");}

        return new MemberDetail(id, member.getPhoneNumber(), member.getAddress(), member.getPoint());
    }

    //
    @Override
    @Transactional(readOnly = true)
    public List<MemberDetail> getAllMembers() {
        List<Member> all = memberRepository.findAll();

        List<MemberDetail> memberDetails = new ArrayList<>();
        for (Member member : all) {
            MemberDetail memberDetail = new MemberDetail(
                    member.getId(),
                    member.getPhoneNumber(),
                    member.getAddress(),
                    member.getPoint()
                    );
            memberDetails.add(memberDetail);
        }
        return memberDetails;
    }


    @Override
    @Transactional
    public void updateMember(Long id, MemberUpdateRequest request) {

        Member member = memberRepository.findById(id);

        //존재하는 회원인지 확인
        if (member == null) {
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }

        //도메인 객체의 메서드를 활용해 정보 수정
        member.updateInfo(request.getPassword(), request.getPhoneNumber(), request.getAddress());
        memberRepository.save(member);
    }

    @Override
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
