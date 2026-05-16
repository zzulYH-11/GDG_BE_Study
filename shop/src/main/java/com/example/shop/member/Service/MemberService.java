package com.example.shop.member.Service;

import com.example.shop.member.DTO.MemberCreateRequest;
import com.example.shop.member.DTO.MemberDetail;
import com.example.shop.member.DTO.MemberUpdateRequest;

import java.util.List;

public interface MemberService {

    Long createMember(MemberCreateRequest request);

    MemberDetail getMember(Long id);

    List<MemberDetail> getAllMembers();

    void updateMember(Long id, MemberUpdateRequest request);

    void deleteMember(Long id);

}
