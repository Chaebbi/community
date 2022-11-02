package com.chaebbi.community.dto.request;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdatePostDto {
    @ApiModelProperty(value = "수정할 게시글 제목")
    private String title;
    @ApiModelProperty(value = "수정할 게시글 내용")
    private String content;
}
