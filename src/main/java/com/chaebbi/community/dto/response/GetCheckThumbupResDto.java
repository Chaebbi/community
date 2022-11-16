package com.chaebbi.community.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCheckThumbupResDto {
    @ApiModelProperty(value = "사용자 좋아요 여부")
    private int isThumbup;
}
