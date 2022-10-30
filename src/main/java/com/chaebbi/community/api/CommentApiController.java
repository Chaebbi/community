package com.chaebbi.community.api;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.dto.request.DeleteCommentReqDto;
import com.chaebbi.community.dto.request.PostCommentReqDto;
import com.chaebbi.community.dto.response.GetCommentResDto;
import com.chaebbi.community.dto.response.PostCommentResDto;
import com.chaebbi.community.dto.response.ResponseDto;
import com.chaebbi.community.dto.response.StringResponseDto;
import com.chaebbi.community.service.CommentService;
import com.chaebbi.community.validation.CommentValidationController;
import com.chaebbi.community.validation.UserValidationController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@Api(tags = "Comment API", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApiController {
    private final CommentService commentService;
    //private final CUserService cuserService;

    private final UserValidationController userValidationController;
    private final CommentValidationController commentValidationController;

    /**
     * [Post] 40-1 댓글 작성 API
     * /comment/:userIdx
     */
    @ApiOperation(value = "[POST] 40-1 댓글 작성 ", notes = "postIdx, 댓글 내용을 넣어 댓글을 등록합니다")
    @PostMapping("/{userIdx}")
    public ResponseEntity<?> createComment(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody PostCommentReqDto request) {
        //validation 로직
        userValidationController.validateuser((long) userIdx);
        commentValidationController.validateComment(request);

        Comment comment = Comment.createComment(userIdx, request.getPostIdx(), request.getContent());
        int commentIdx = commentService.createComment(comment);
        return ResponseEntity.ok().body(new PostCommentResDto(commentIdx));
    }

    /**
     * [Delete] 40-2 댓글 삭제 API
     * /comment/:userIdx
     */
    @ApiOperation(value = "[DELETE] 40-2 댓글 삭제 ", notes = "commentIdx를 넣어 댓글을 삭제합니다")
    @DeleteMapping("/{userIdx}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody DeleteCommentReqDto request) {
        //validation 로직
        userValidationController.validateuser((long) userIdx);
        commentValidationController.validateDeleteComment(request);

        commentService.deleteComment(userIdx, request.getCommentIdx());
        return ResponseEntity.ok().body(new StringResponseDto("삭제되었습니다."));
    }

    /**
     * [Get] 40-3 내가 쓴 댓글 조회 API
     * /comment/:userIdx
     */
    @ApiOperation(value = "[GET] 40-3 내가 쓴 댓글 조회 ", notes = "userIdx를 넣어 내가 쓴 댓글을 조회합니다")

    @GetMapping("/{userIdx}")
    public ResponseEntity<?> getComment(@PathVariable(value = "userIdx", required = false) int userIdx) {
        //validation 로직
        userValidationController.validateuser((long) userIdx);

        List<Comment> getComments = commentService.getComment(userIdx);
        List<GetCommentResDto> getCommentRes = new ArrayList<>();

        for(Comment comment: getComments) {
            getCommentRes.add(new GetCommentResDto(comment.getIdx(), comment.getContent(), new SimpleDateFormat("yyyy.MM.dd HH:mm").format(comment.getCreatedAt()), comment.getPostIdx()));
        }

        return ResponseEntity.ok().body(getCommentRes);
    }
}
