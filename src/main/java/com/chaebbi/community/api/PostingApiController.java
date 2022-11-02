package com.chaebbi.community.api;

import com.chaebbi.community.aws.S3Uploader;
import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.dto.request.PostingDto;
import com.chaebbi.community.dto.request.UpdatePostDto;
import com.chaebbi.community.dto.response.CommentsListDto;
import com.chaebbi.community.dto.response.ImagesListDto;
import com.chaebbi.community.dto.response.PostDetailDto;
import com.chaebbi.community.exception.ExceptionController;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.*;
import com.chaebbi.community.validation.ImageValidationController;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    private final ThumbupService thumbupService;
    private final CommentService commentService;
    private final CommunityUserService userService;
    /**
     * [Post] 31-1 게시글 작성 API
     */
    @ApiOperation(value = "[POST] 31-1 게시글 작성 ", notes = "제목, 글내용, 이미지들을 넣어 게시글을 등록합니다")
    @PostMapping("/{userIdx}")
    public ResponseEntity<Void> uploadPost(@PathVariable(value = "userIdx") Long userIdx,
                                           @ApiParam(value = "이미지파일 리스트") @RequestPart (value= "multipartFileList", required = false) List<MultipartFile> multipartFileList,
                                           @ApiParam(value = "게시글 제목과 내용 dto") @RequestPart(value="posting") PostingDto postingDto ) throws IOException {

        log.info("POST 31-1 /posting/{userIdx}");

        userValidationController.validateuser(userIdx);
        postValidationController.validationPost(postingDto.getContent(), postingDto.getTitle());

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

    /**
     * [Get] 31-5 게시글 상세 1개 조회 API
     * */
    @ApiOperation(value = "[GET] 31-5 게시글 상세 1개 조회  ", notes = "게시글 id로 게시글의 상세내용을 조회 합니다.")
    @GetMapping("/post/{userIdx}/{postIdx}")
    public ResponseEntity<PostDetailDto> detailPost(@PathVariable (value = "userIdx") Long userIdx,
                                                    @PathVariable (value = "postIdx") Long postIdx
                                                    ) {
        log.info("Post 31-3 /posting/update/{userIdx}/{postIdx}");

        CommunityUser user = userValidationController.validateuser(userIdx);
        Posting post = postValidationController.validationPostExist(postIdx);
        List<Images> imageList = imagesService.findByPostIdx(postIdx);

        PostDetailDto postDetailDto = postingService.detailPost(postIdx, post, imageList);


        return ResponseEntity.ok().body(postDetailDto);
    }


    /**
     * [Get] 31-6 내가 쓴 게시글 조회 API
     * */




}
