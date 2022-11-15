package com.chaebbi.community.api;

import com.chaebbi.community.aws.S3Uploader;
import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.dto.request.PostingDto;
import com.chaebbi.community.dto.request.UpdatePostDto;
import com.chaebbi.community.dto.response.AllPostsListDto;
import com.chaebbi.community.dto.response.CheckMyPostsDto;
import com.chaebbi.community.dto.response.PostDetailDto;
import com.chaebbi.community.service.*;
import com.chaebbi.community.validation.PostValidationController;
import com.chaebbi.community.validation.UserValidationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;


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
    @PostMapping(value = "/{userIdx}" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadPost(@PathVariable(value = "userIdx") Long userIdx,
                                           @ApiParam(value = "이미지파일 리스트") @RequestParam(value= "multipartFileList", required = false) List<MultipartFile> multipartFileList,
                                           @ApiParam(value = "게시글 제목") @RequestParam(value="title", required =true) String title,
                                           @ApiParam(value = "게시글 내용") @RequestParam(value="content", required = true) String content
    ) throws IOException {

        log.info("POST 31-1 /posting/{userIdx}");
        userValidationController.validateuser(userIdx);
        postValidationController.validationPost(content, title);

        Posting post = new Posting();
        post = postingService.create(userIdx, content, title);
        postingService.save(post);
        Long postIdx = post.getIdx();

        if(multipartFileList != null) {
            imagesService.uploadImages(postIdx, multipartFileList);
        }

        return ResponseEntity.ok().build();
    }

    /**
     * [Delete] 31-2 게시글 삭제 API
     */
    @ApiOperation(value = "[POST] 31-2 게시글 삭제 ", notes = "게시글 id로 게시글을 삭제합니다")
    @DeleteMapping ("/{userIdx}/{postIdx}")
    public ResponseEntity<Void> deletePost(@PathVariable (value = "userIdx") Long userIdx,
                                           @PathVariable (value = "postIdx") Long postIdx
                                           ){
        log.info("Delete 31-2 /posting/{userIdx}/{postIdx}");

        userValidationController.validateuser(userIdx);
        postValidationController.validateDeletePost(postIdx);

        postingService.deletePost(postIdx);

        return ResponseEntity.ok().build();
    }



    /**
     * [Post] 31-3 게시글 수정 API
     * */
    @ApiOperation(value = "[POST] 31-3 게시글 수정 ", notes = "게시글 id로 게시글의 제목과 내용을 수정합니다.")
    @PostMapping("/update/{userIdx}/{postIdx}")
    public ResponseEntity<Void> updatePost(@PathVariable (value = "userIdx") Long userIdx,
                                           @PathVariable (value = "postIdx") Long postIdx,
                                           @ApiParam(value = "수정할 게시글 제목과 내용 dto") @RequestBody UpdatePostDto updatePostDto) {
        log.info("Post 31-3 /posting/update/{userIdx}/{postIdx}");

        userValidationController.validateuser(userIdx);
        postValidationController.validationPost(updatePostDto.getContent(), updatePostDto.getTitle());
        Posting targetPost = postValidationController.validationPostExist(postIdx);

        postingService.update(targetPost, updatePostDto.getContent(), updatePostDto.getTitle());

        return ResponseEntity.ok().build();
    }

    /**
     * [Get] 31-4 게시글 전체  목록 조회 API
     * */
    @ApiOperation(value = "[GET] 31-4 게시글 전체  목록 조회  ", notes = "전체 게시글 목록을 조회 합니다.")
    @GetMapping("/allposts/{userIdx}")
    public ResponseEntity<AllPostsListDto> allPostsList(@PathVariable (value = "userIdx") Long userIdx){

        log.info("Get 31-4 /allposts/{userIdx}");

        CommunityUser user = userValidationController.validateuser(userIdx);

        AllPostsListDto allPostsListDto = postingService.allPostsList(user);
        return ResponseEntity.ok().body(allPostsListDto);

    }


    /**
     * [Get] 31-5 게시글 상세 1개 조회 API
     * */
    @ApiOperation(value = "[GET] 31-5 게시글 상세 1개 조회  ", notes = "게시글 id로 게시글의 상세내용을 조회 합니다.")
    @GetMapping("/post/{userIdx}/{postIdx}")
    public ResponseEntity<PostDetailDto> detailPost(@PathVariable (value = "userIdx") Long userIdx,
                                                    @PathVariable (value = "postIdx") Long postIdx
                                                    ) {
        log.info("Get 31-5 /post/{userIdx}/{postIdx}");

        CommunityUser user = userValidationController.validateuser(userIdx);
        Posting post = postValidationController.validationPostExist(postIdx);
        List<Images> imageList = imagesService.findByPostIdx(postIdx);

        PostDetailDto postDetailDto = postingService.detailPost(postIdx, post, imageList);


        return ResponseEntity.ok().body(postDetailDto);
    }


    /**
     * [Get] 31-6 내가 쓴 게시글 조회 API
     * */
    @ApiOperation(value = "[GET] 내가 쓴 게시글 조회  ", notes = "user id로 내가 쓴 게시글들을 조회 합니다.")
    @GetMapping("/mypost/{userIdx}")
    public ResponseEntity<CheckMyPostsDto> checkMyPosts(@PathVariable (value = "userIdx") Long userIdx) {
        log.info("Post 31-6 /mypost/{userIdx}");
        userValidationController.validateuser(userIdx);

        CheckMyPostsDto checkMyPostsDto =postingService.checkMyPosts(userIdx);


        return ResponseEntity.ok().body(checkMyPostsDto);
    }




}
