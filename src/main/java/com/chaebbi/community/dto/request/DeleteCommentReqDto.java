package com.chaebbi.community.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteCommentReqDto {
    @ApiModelProperty(value = "댓글 idx")
    public int commentIdx;
}
