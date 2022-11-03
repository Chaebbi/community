package com.chaebbi.community.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class AllPostsListDto {
    @ApiModelProperty(value = "사용자 닉네임 ")
    private String nickname;
    @ApiModelProperty(value = "게시글 목록 개수")
    private Long postCount;
    @ApiModelProperty(value = "게시글 리스트")
    private List<AllPostsLists> allPostsLists;

}
