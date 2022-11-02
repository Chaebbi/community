package com.chaebbi.community.api;

import com.chaebbi.community.service.ImagesService;
import com.chaebbi.community.validation.ImageValidationController;
import com.chaebbi.community.validation.UserValidationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(tags = "Images API", description = "게시글 이미지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImagesApiController {
    private final ImagesService imagesService;
    private final UserValidationController userValidationController;
    private final ImageValidationController imageValidationController;


    /**
     * [Delete] 32-1 게시글 이미지 수정을 위한 삭제API
     * */
    @ApiOperation(value = "[DELETE] 32-1 게시글 수정을 위한 이미지 삭제 API", notes = "게시글의 삭제할 이미지 id를 입력해 삭제")
    @DeleteMapping("/{userIdx}/{imageIdx}")
    public ResponseEntity<Void> deleteImage(@PathVariable (value = "userIdx") Long userIdx,
                                            @PathVariable (value = "imageIdx") Long imageIdx
                                            ) {
        log.info("Delete 32-2 /images/{userIdx}/{imageIdx}");

        userValidationController.validateuser(userIdx);
        imageValidationController.validateImageIdx(imageIdx);

        imagesService.deleteImage(imageIdx);

        return ResponseEntity.ok().build();

    }


    /**
     * [Post] 32-1 게시글 이미지 수정 API
     * */
}
