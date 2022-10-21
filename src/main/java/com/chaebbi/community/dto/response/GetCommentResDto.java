package com.chaebbi.community.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetCommentResDto {
    private int commentIdx;
    private String content;
    private String date;
    private int postIdx;
}
