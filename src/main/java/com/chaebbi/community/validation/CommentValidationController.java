package com.chaebbi.community.validation;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.dto.request.DeleteCommentReqDto;
import com.chaebbi.community.dto.request.PostCommentReqDto;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.CommentService;
import com.chaebbi.community.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.chaebbi.community.exception.CodeAndMessage.*;

@Controller
@RequiredArgsConstructor
public class CommentValidationController {
    private final PostingService postingService;
    private final CommentService commentService;

    public void validateComment(PostCommentReqDto request) {
        Optional<Posting> post = postingService.findById((long)request.getPostIdx());
        if(post == Optional.<Posting>empty()) throw new chaebbiException(INVALID_POST_ID);

        if(request.getContent().isEmpty() || request.getContent().equals("")) {
            throw new chaebbiException(COMMENT_NO_CONTENT);
        }

        if(request.getContent().length() > 200) {
            throw new chaebbiException(COMMENT_LONG_CONTENT);
        }
    }

    public void validateDeleteComment(DeleteCommentReqDto request) {
        if(commentService.checkComment(request.getCommentIdx()) <= 0) {
            throw new chaebbiException(INVALID_COMMENT);
        }
    }
}
