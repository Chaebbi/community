package com.chaebbi.community.api;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.dto.request.DeleteCommentReqDto;
import com.chaebbi.community.dto.request.PostCommentReqDto;
import com.chaebbi.community.dto.response.GetCommentResDto;
import com.chaebbi.community.dto.response.PostCommentResDto;
import com.chaebbi.community.dto.response.ResponseDto;
import com.chaebbi.community.dto.response.StringResponseDto;
import com.chaebbi.community.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequiredArgsConstructor
public class CommentApiController {
    private final CommentService commentService;
    //private final CUserService cuserService;

    /*
    댓글 작성 API
    [POST] /api/comment/:userIdx
     */
    @PostMapping("/api/comment/{userIdx}")
    public ResponseEntity<?> createComment(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody PostCommentReqDto request) {
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

        if(request.getContent().isEmpty() || request.getContent().equals("")) {
            return new BaseResponse<>(POST_COMMENT_NO_CONTENT);
        }

        if(request.getContent().length() > 200) {
            return new BaseResponse<>(POST_COMMENT_LONG_CONTENT);
        } */

        Comment comment = Comment.createComment(userIdx, request.getPostIdx(), request.getContent());
        int commentIdx = commentService.createComment(comment);
        return ResponseEntity.ok().body(new PostCommentResDto(commentIdx));
    }

    /*
    댓글 삭제 API
    [DELETE] /api/comment/:userIdx
     */
    @DeleteMapping("/api/comment/{userIdx}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "userIdx", required = false) int userIdx, @RequestBody DeleteCommentReqDto request) {
        /*
        if(String.valueOf(userIdx).isEmpty() || String.valueOf(userIdx).equals("")) {
            return new BaseResponse<>(EMPTY_USERIDX);
        }
        CUser user = cuserService.findOne(userIdx); 유저 관련 생기면 다시 하기
        if(user == null) {
            return new BaseResponse<>(INVALID_USER);
        }

        if(String.valueOf(request.getCommentIdx()).isEmpty() || String.valueOf(request.getCommentIdx()).equals("")) {
            return new BaseResponse<>(EMPTY_COMMENTIDX);
        }

        if(commentService.checkComment(userIdx, commentIdx) == 0) {
            return new BaseResponse<>(INVALID_COMMENT); 유효하지 않은 댓글
        }
         */
        commentService.deleteComment(userIdx, request.getCommentIdx());
        return ResponseEntity.ok().body(new StringResponseDto("삭제되었습니다."));
    }

    /*
    내가 쓴 댓글 조회 API
    [GET]  /api/comment/:userIdx
     */
    @GetMapping("/api/comment/{userIdx}")
    public ResponseEntity<?> getComment(@PathVariable(value = "userIdx", required = false) int userIdx) {
        /*
        if(String.valueOf(userIdx).isEmpty() || String.valueOf(userIdx).equals("")) {
            return new BaseResponse<>(EMPTY_USERIDX);
        }
        CUser user = cuserService.findOne(userIdx); 유저 관련 생기면 다시 하기
        if(user == null) {
            return new BaseResponse<>(INVALID_USER);
        }
         */
        List<Comment> getComments = commentService.getComment(userIdx);
        List<GetCommentResDto> getCommentRes = new ArrayList<>();

        for(Comment comment: getComments) {
            getCommentRes.add(new GetCommentResDto(comment.getIdx(), comment.getContent(), new SimpleDateFormat("yyyy.MM.dd HH:mm").format(comment.getCreatedAt()), comment.getPostIdx()));
        }

        return ResponseEntity.ok().body(new ResponseDto<>(getCommentRes));
    }
}
