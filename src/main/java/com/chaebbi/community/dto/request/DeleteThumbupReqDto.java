package com.chaebbi.community.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DeleteThumbupReqDto {
    @ApiModelProperty(value = "게시글 idx")
    private int postIdx;
}
