package com.example.shop.member.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberDetail {

    private Long memberId;
    private String phoneNumber;
    private String address;
    private int point;

}
