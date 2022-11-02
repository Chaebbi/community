package com.chaebbi.community.api;

import com.chaebbi.community.aws.S3Uploader;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.service.ImagesService;
import com.chaebbi.community.validation.ImageValidationController;
import com.chaebbi.community.validation.UserValidationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Api(tags = "Images API", description = "게시글 이미지 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImagesApiController {
    private final ImagesService imagesService;
    private final S3Uploader s3Uploader;
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
        log.info("Delete 32-1 /images/{userIdx}/{imageIdx}");

        userValidationController.validateuser(userIdx);
        imageValidationController.validateImageIdx(imageIdx);

        imagesService.deleteImage(imageIdx);

        return ResponseEntity.ok().build();

    }


    /**
     * [Post] 32-1 게시글 이미지 수정 API
     * */
    @ApiOperation(value = "[POST] 32-2 게시글 이미지 수정 API", notes = "게시글의 수정할 이미지 id와 이미지순서, 이미지 파일을 입력해 교체")
    @PostMapping("/{userIdx}/{imageIdx}")
    public ResponseEntity<Void> updateImage(@PathVariable (value = "userIdx") Long userIdx,
                                            @PathVariable (value = "imageIdx") Long imageIdx,
                                            @ApiParam(value = "교체할 이미지파일 ") @RequestPart(value = "multipartFile")MultipartFile multipartFile,
                                            @ApiParam(value = "교체할 이미지파일의 순서 ")@RequestPart(value = "imgRank") int imgRank
                                            ) throws IOException {
        log.info("Post 32-2 /images/{userIdx}/{imageIdx}");

        userValidationController.validateuser(userIdx);
        Images targetImage = imageValidationController.validateImageIdx(imageIdx);
        imageValidationController.validateEmptyFile(multipartFile);

        String updateImgUrl = "empty";
        updateImgUrl = s3Uploader.upload(multipartFile, "update-static");

        imagesService.update(targetImage, updateImgUrl, imgRank);
        return ResponseEntity.ok().build();
    }

}
