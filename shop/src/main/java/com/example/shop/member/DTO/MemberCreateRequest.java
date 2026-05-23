package com.example.shop.member.DTO;

import com.example.shop.common.message.ErrorMessage;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCreateRequest {

    @NotNull(message = ErrorMessage.LOGIN_ID_NOT_NULL)
    private String loginId;

    @NotNull(message = ErrorMessage.PASSWORD_NOT_NULL)
    private String password;

    @NotNull(message = ErrorMessage.PHONE_NUMBER_NOT_NULL)
    @Pattern(regexp = "^010-\\d{4}-\\d{4}$", message = ErrorMessage.PHONE_NUMBER_FORMAT_ERROR)
    private String phoneNumber;

    @NotNull(message = ErrorMessage.ADDRESS_NOT_NULL)
    private String address;

}
