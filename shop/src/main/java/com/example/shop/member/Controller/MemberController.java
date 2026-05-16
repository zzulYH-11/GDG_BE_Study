package com.example.shop.member.Controller;

import com.example.shop.member.Service.MemberServiceImpl;
import com.example.shop.member.DTO.MemberCreateRequest;
import com.example.shop.member.DTO.MemberDetail;
import com.example.shop.member.DTO.MemberUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping
    public ResponseEntity<Void> createMember(@RequestBody MemberCreateRequest request) {
        Long memberId = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    // Id로 멤버 하나 조회하기
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDetail> getMember(@PathVariable Long memberId) {

        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @GetMapping
    public ResponseEntity<List<MemberDetail>> getAllMembers() {
        //서비스 계층에서 회원 목록을 가져온다.
        List<MemberDetail> memberDetail = memberService.getAllMembers();
        return ResponseEntity.ok(memberDetail);
    }

    // 멤버 정보 수정하기
    @PatchMapping("/{memberId}")
    public ResponseEntity<Void> updateMember(@PathVariable("memberId") Long memberId, @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(memberId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable("memberId") Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
