package com.example.shop.member.Controller;

import com.example.shop.member.Service.MemberServiceImpl;
import com.example.shop.member.DTO.MemberCreateRequest;
import com.example.shop.member.DTO.MemberDetail;
import com.example.shop.member.DTO.MemberUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
@Tag(name = "멤버 컨트롤러", description = "멤버 CRUD 수행")
public class MemberController {

    private final MemberServiceImpl memberService;

    @PostMapping
    @Operation(summary = "멤버 등록", description = "한 사람의 아이디, 비밀번호, 전화번호, 주소를 받아 멤버로 등록한다.")
    public ResponseEntity<Void> createMember(@Valid @RequestBody MemberCreateRequest request) {
        Long memberId = memberService.createMember(request);
        return ResponseEntity.created(URI.create("/members/" + memberId)).build();
    }

    @GetMapping("/{memberId}")
    @Operation(summary = "멤버 조회", description = "Id로 멤버 한 명을 조회한다.")
    public ResponseEntity<MemberDetail> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @GetMapping
    @Operation(summary = "모든 멤버 조회", description = "모든 멤버를 조회한다.")
    public ResponseEntity<List<MemberDetail>> getAllMembers() {
        //서비스 계층에서 회원 목록을 가져온다.
        List<MemberDetail> memberDetail = memberService.getAllMembers();
        return ResponseEntity.ok(memberDetail);
    }

    @PatchMapping("/{memberId}")
    @Operation(summary = "멤버 정보 수정", description = "멤버의 정보를 업데이트한다.")
    public ResponseEntity<Void> updateMember(@PathVariable Long memberId, @Valid @RequestBody MemberUpdateRequest request) {
        memberService.updateMember(memberId, request);
        return ResponseEntity.ok().build();
    }

    // 멤버 하나 삭제하기
    @DeleteMapping("/{memberId}")
    @Operation(summary = "멤버 삭제", description = "Id로 멤버 한 명을 삭제한다.")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
