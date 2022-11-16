package com.chaebbi.community.api;

import com.chaebbi.community.domain.Thumbup;
import com.chaebbi.community.dto.request.DeleteThumbupReqDto;
import com.chaebbi.community.dto.request.PostThumbupReqDto;
import com.chaebbi.community.dto.response.GetCheckThumbupResDto;
import com.chaebbi.community.dto.response.PostThumbupResDto;
import com.chaebbi.community.dto.response.StringResponseDto;
import com.chaebbi.community.service.ThumbupService;
import com.chaebbi.community.validation.ThumbupValidationController;
import com.chaebbi.community.validation.UserValidationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(tags = "Thumbup API", description = "좋아요 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/thumbup")
public class ThumbupApiController {
    private final ThumbupService thumbupService;

    private final UserValidationController userValidationController;
    private final ThumbupValidationController thumbupValidationController;

    /**
     * [Post] 41-1 좋아요 등록 API
     * /thumbup/:userIdx
     */
    @ApiOperation(value = "[POST] 41-1 좋아요 등록 ", notes = "userIdx와 postIdx를 넣어 좋아요를 등록합니다")
    @PostMapping("/{userIdx}")
    public ResponseEntity<?> createThumbup(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody PostThumbupReqDto request) {
        //validation 로직
        userValidationController.validateuser((long) userIdx);
        thumbupValidationController.validatePost(request.getPostIdx());

        Long check = thumbupService.checkThumbup(userIdx, request.getPostIdx());
        if(check != 0) {
            thumbupService.deleteThumbup(userIdx, request.getPostIdx());
        }

        Thumbup thumbup = Thumbup.createThumbup(userIdx, request.getPostIdx());
        int thumbupIdx = thumbupService.createThumbup(thumbup);
        return ResponseEntity.ok().body(new PostThumbupResDto(thumbupIdx));
    }

    /**
     * [Delete] 41-2 좋아요 삭제 API
     * /thumbup/:userIdx
     */
    @ApiOperation(value = "[POST] 41-2 좋아요 삭제 ", notes = "userIdx와 postIdx를 넣어 좋아요를 삭제합니다")
    @DeleteMapping("/{userIdx}")
    public ResponseEntity<?> deleteThumbup(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody DeleteThumbupReqDto request) {
        //validation 로직
        userValidationController.validateuser((long) userIdx);
        thumbupValidationController.validatePost(request.getPostIdx());
        thumbupValidationController.validateDeleteThumbup(userIdx, request);

        thumbupService.deleteThumbup(userIdx, request.getPostIdx());
        return ResponseEntity.ok().body(new StringResponseDto("삭제되었습니다."));
    }

    /**
     * [Get] 41-3 좋아요 확인
     */
    @ApiOperation(value = "[GET] 41-3 좋아요 확인 ", notes = "userIdx와 postIdx를 넣어 사용자가 좋아요 했는지 확인합니다")
    @GetMapping("/{userIdx}/{postIdx}")
    public ResponseEntity<?> checkThumbup(@PathVariable(value = "userIdx", required = false) int userIdx,
                                          @PathVariable(value = "postIdx", required = false) int postIdx) {
        //validation 로직
        userValidationController.validateuser((long) userIdx);
        thumbupValidationController.validatePost(postIdx);

        int isThumbup = 0;
        Long count = thumbupService.checkThumbup(userIdx, postIdx);
        if(count == 0) {
            isThumbup = 0;
        } else if(count > 0) {
            isThumbup = 1;
        }

        return ResponseEntity.ok().body(new GetCheckThumbupResDto(isThumbup));
    }
}
