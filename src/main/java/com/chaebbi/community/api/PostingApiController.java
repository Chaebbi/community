package com.chaebbi.community.api;

import com.chaebbi.community.aws.S3Uploader;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.service.ImagesService;
import com.chaebbi.community.service.PostingService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Api(tags = "Posting API", description = "게시글 API")
@RestController
@RequiredArgsConstructor

public class PostingApiController {
    private final PostingService postingService;
    private final ImagesService imagesService;
    private final S3Uploader s3Uploader;

    /**
     * [Post] 31-1 게시글 작성 API
     */
    @Operation(summary = "31-1 게시글 작성 ", description = "게시글 작성  API")
    @PostMapping("/posting/{userIdx}")
    public ResponseEntity<Void> uploadPost(@PathVariable(value = "userIdx") Long userIdx,
                                           @RequestPart (value= "multipartFileList", required = false) List<MultipartFile> multipartFileList,
                                           @RequestPart(value="posting") PostingDto postingDto ) throws IOException {

        Posting post = new Posting();
        post = postingService.create(userIdx, postingDto.content, postingDto.title);
        postingService.save(post);
        Long postIdx = post.getIdx();

        int img_rank = 1;

        for(int i=0; i<multipartFileList.size(); i++) {
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

    @Data
    static class PostingDto {
        private String title;
        private String content;
    }
}
