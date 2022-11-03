package com.chaebbi.community.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AllPostsLists {
    @ApiModelProperty(value = "게시글 id")
    private Long postIdx;
    @ApiModelProperty(value = "게시글 제목")
    private String title;
    @ApiModelProperty(value = "게시글 내용")
    private String content;
    @ApiModelProperty(value = "게시글 작성자 닉네임 ")
    private String nickname;
    @ApiModelProperty(value = "게시글 게시일자 yyyy.MM.dd HH:mm")
    private String createdAt;
    @ApiModelProperty(value = "게시글 댓글 수")
    private Long thumbupCount;
    @ApiModelProperty(value = "게시글 따봉 수")
    private Long commentCount;
    @ApiModelProperty(value = "게시글 첫번째 imgUrl")
    private String frstImgUrl;


}
