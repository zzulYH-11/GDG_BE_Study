package com.example.shop.common.message;

public class ErrorMessage {

    // 멤버 관련 에러 메시지
    public static final String MEMBER_NOT_FOUND = "회원을 찾을 수 없습니다.";
    public static final String MEMBER_ALREADY_EXIST = "이미 존재하는 로그인 아이디입니다.";

    // 멤버 DTO 관련 에러 메시지
    public static final String LOGIN_ID_NOT_NULL = "로그인 아이디는 필수입니다.";
    public static final String ADDRESS_NOT_NULL = "주소는 필수입니다.";
    public static final String PASSWORD_NOT_NULL = "비밀번호 필수입니다.";
    public static final String PHONE_NUMBER_NOT_NULL = "전화번호는 필수입니다.";
    public static final String PHONE_NUMBER_FORMAT_ERROR = "010-XXXX-XXXX 형식으로 작성하십시오.";


    // 상품 관련 에러 메시지
    public static final String PRODUCT_NOT_EXIST = "존재하지 않는 상품입니다.";
    public static final String PRODUCT_ALREADY_EXIST = "이미 존재하는 상품입니다.";
    public static final String PRODUCT_OUT_OF_STOCK = "상품의 재고가 부족합니다.";

    // 상품 DTO 관련 에러 메시지
    public static final String PRODUCT_NAME_NOT_NULL = "이름은 필수입니다.";
    public static final String PRODUCT_PRICE_NOT_NULL = "가격은 필수입니다.";
    public static final String PRODUCT_QUANTITY_NOT_NULL = "수량은 필수입니다.";
    public static final String PRODUCT_QUANTITY_AT_LEAST_1 = "수량은 1보다 커야합니다.";
    public static final String PRODUCT_PRICE_NOT_NEGATIVE = "가격은 양수여야 합니다.";

    // 주문 관련 에러 메시지
    public static final String ORDER_NOT_EXIST = "존재하지 않는 주문입니다.";

    // 주문 DTO 관련 에러 메시지
}
