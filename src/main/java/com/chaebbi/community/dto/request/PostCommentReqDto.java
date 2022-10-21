package com.chaebbi.community.dto.request;

import lombok.Data;

@Data
public class PostCommentReqDto {
    private int postIdx;
    private String content;
}
