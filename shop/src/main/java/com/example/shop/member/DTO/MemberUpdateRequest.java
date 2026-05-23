package com.example.shop.member.DTO;

import com.example.shop.common.message.ErrorMessage;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberUpdateRequest {

    private String password;

    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = ErrorMessage.PHONE_NUMBER_FORMAT_ERROR)
    private String phoneNumber;

    private String address;
}
