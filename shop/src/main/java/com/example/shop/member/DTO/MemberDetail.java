package com.example.shop.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

//응답용이라 유효성 검사 X
@Getter
@AllArgsConstructor
public class MemberDetail {

    private Long memberId;

    private String phoneNumber;

    private String address;

    private int point;

}
