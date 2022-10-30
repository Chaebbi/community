package com.chaebbi.community.api;

import com.chaebbi.community.aws.S3Uploader;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.dto.request.PostingDto;
import com.chaebbi.community.exception.ExceptionController;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.ImagesService;
import com.chaebbi.community.service.PostingService;
import com.chaebbi.community.validation.PostValidationController;
import com.chaebbi.community.validation.UserValidationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.chaebbi.community.exception.CodeAndMessage.*;

@Slf4j
@Api(tags = "Posting API", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingApiController {
    private final PostingService postingService;
    private final ImagesService imagesService;
    private final S3Uploader s3Uploader;
    private final UserValidationController userValidationController;
    private final PostValidationController postValidationController;
    /**
     * [Post] 31-1 게시글 작성 API
     */
    @ApiOperation(value = "[POST] 31-1 게시글 작성 ", notes = "제목, 글내용, 이미지들을 넣어 게시글을 등록합니다")
    @PostMapping("/{userIdx}")
    public ResponseEntity<Void> uploadPost(@PathVariable(value = "userIdx") Long userIdx,
                                           @ApiParam(value = "이미지파일 리스트") @RequestPart (value= "multipartFileList", required = false) List<MultipartFile> multipartFileList,
                                           @ApiParam(value = "게시글 제목과 내용 dto") @RequestPart(value="posting") PostingDto postingDto ) throws IOException {

        log.info("POST 31-1 /posting/{userIdx}");
        //validation 로직
        userValidationController.validateuser(userIdx);
        postValidationController.validationPost(postingDto);

        Posting post = new Posting();
        post = postingService.create(userIdx, postingDto.getContent(), postingDto.getTitle());
        postingService.save(post);
        Long postIdx = post.getIdx();

        int img_rank = 1;

        for(int i = 0; i < multipartFileList.size(); i++) {
            MultipartFile multipartFile = multipartFileList.get(i);
            String img_url = "empty";
            if(multipartFile != null) {
                if(!multipartFile.isEmpty()) {
                    img_url = s3Uploader.upload(multipartFile, "static");
                    Images images = imagesService.create(postIdx, img_url, img_rank);
                    imagesService.save(images);
                    img_rank++;
                }
            }
        }

        return ResponseEntity.ok().build();
    }


}
