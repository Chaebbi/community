package com.chaebbi.community.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostsListDto {
    @ApiModelProperty(value = "게시글 제목")
    private String title;
    @ApiModelProperty(value = "게시글 내용")
    private String content;
    @ApiModelProperty(value = "게시글 게시날자")
    private String createdAt;
}
