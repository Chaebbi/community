package com.chaebbi.community.api;

import com.chaebbi.community.domain.Thumbup;
import com.chaebbi.community.dto.request.DeleteThumbupReqDto;
import com.chaebbi.community.dto.request.PostThumbupReqDto;
import com.chaebbi.community.dto.response.PostThumbupResDto;
import com.chaebbi.community.dto.response.StringResponseDto;
import com.chaebbi.community.service.ThumbupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ThumbupApiController {
    private final ThumbupService thumbupService;

    /*
    좋아요 등록 API
    [POST] /api/thumbup/:userIdx
     */
    @PostMapping("/api/thumbup/{userIdx}")
    public ResponseEntity<?> createThumbup(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody PostThumbupReqDto request) {
        /*
        if(String.valueOf(userIdx).isEmpty() || String.valueOf(userIdx).equals("")) {
            return new BaseResponse<>(EMPTY_USERIDX);
        }
        CUser user = cuserService.findOne(userIdx); 유저 관련 생기면 다시 하기
        if(user == null) {
            return new BaseResponse<>(INVALID_USER);
        }

        if(String.valueOf(request.getPostIdx()).isEmpty() || String.valueOf(request.getPostIdx()).equals("")) {
            return new BaseResponse<>(EMPTY_POSTIDX);
        }

         게시글 관련 생기면 존재하지 않는 게시글입니다. validation 처리
        */

        Thumbup thumbup = Thumbup.createThumbup(userIdx, request.getPostIdx());
        int thumbupIdx = thumbupService.createThumbup(thumbup);
        return ResponseEntity.ok().body(new PostThumbupResDto(thumbupIdx));
    }

    /*
    좋아요 삭제 API
    [DELETE] /api/thumbup/:userIdx
     */
    @DeleteMapping("/api/thumbup/{userIdx}")
    public ResponseEntity<?> deleteThumbup(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody DeleteThumbupReqDto request) {
        /*
        if(String.valueOf(userIdx).isEmpty() || String.valueOf(userIdx).equals("")) {
            return new BaseResponse<>(EMPTY_USERIDX);
        }
        CUser user = cuserService.findOne(userIdx); 유저 관련 생기면 다시 하기
        if(user == null) {
            return new BaseResponse<>(INVALID_USER);
        }

        if(String.valueOf(request.getPostIdx()).isEmpty() || String.valueOf(request.getPostIdx()).equals("")) {
            return new BaseResponse<>(EMPTY_POSTIDX);
        }

         게시글 관련 생기면 존재하지 않는 게시글입니다. validation 처리

         if(thumbupService.checkThumbup(userIdx, postIdx) == 0) {
            return new BaseResponse<>(INVALID_THUMBUP); 유효하지 않은 좋아요
        }
        */

        thumbupService.deleteThumbup(userIdx, request.getPostIdx());
        return ResponseEntity.ok().body(new StringResponseDto("삭제되었습니다."));
    }
}
