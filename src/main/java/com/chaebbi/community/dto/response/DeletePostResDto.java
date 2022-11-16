package com.chaebbi.community.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeletePostResDto {
    @ApiModelProperty(value = "사용자 idx")
    private Long userIdx;
}
