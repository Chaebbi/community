package com.chaebbi.community.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeAndMessage {

    // 유저 관련 1000 ~
    EMPTY_USER("1000", "해당 유저가 없습니다"),
    EMPTY_PATHVARIABLE_USERID("1001", "userIdx pathvariable이 없습니다"),

    // 게시글 관련 1100 ~
    EMPTY_CONTENT("1100", "게시글 내용이 비어있습니다"),
    EMPTY_TITLE("1101", "게시글 제목이 비어있습니다"),

    // S3 Util
    FILE_CONVERT_ERROR("1200", "파일 변환에 실패했습니다."),
    FILE_EXTENSION_ERROR("1201", "파일 확장자 인식에 실패했습니다.");


    private final String code;
    private final String message;

}
