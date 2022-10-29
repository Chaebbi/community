package com.chaebbi.community.exception;
import com.chaebbi.community.dto.response.ApiResponse;
import lombok.*;

@Getter
@Setter
public class ErrorResponse extends ApiResponse {
    @Builder
    public ErrorResponse (String code, String message) {
        super(code, message);
    }

}
