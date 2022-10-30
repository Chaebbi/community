package com.chaebbi.community.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PostCommentReqDto {
    @ApiModelProperty(value = "게시글 idx")
    private int postIdx;
    @ApiModelProperty(value = "댓글 내용")
    private String content;
}
