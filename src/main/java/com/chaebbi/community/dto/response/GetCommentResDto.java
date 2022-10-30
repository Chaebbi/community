package com.chaebbi.community.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCommentResDto {
    @ApiModelProperty(value = "댓글 idx")
    private int commentIdx;
    @ApiModelProperty(value = "댓글 내용")
    private String content;
    @ApiModelProperty(value = "댓글 작성 날짜")
    private String date;
    @ApiModelProperty(value = "게시글 idx")
    private int postIdx;
}
