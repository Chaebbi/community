package com.chaebbi.community.validation;

import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.dto.request.PostingDto;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.PostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.chaebbi.community.exception.CodeAndMessage.*;


@Controller
@RequiredArgsConstructor
public class PostValidationController {
    private final PostingService postingService;

    public void validationPost(PostingDto post){
        if(post.getContent() == null || post.getContent().equals("")) throw new chaebbiException(EMPTY_CONTENT);  //post.getContent().isEmpty()  불가능이므로 코드 무효화
        if(post.getTitle() == null || post.getTitle().equals("")) throw new chaebbiException(EMPTY_TITLE);
    }

    public void validateDeletePost(Long postIdx) {
        Optional<Posting> post = postingService.findById(postIdx);
        if(post == Optional.<Posting>empty()) throw new chaebbiException(INVALID_POST_ID);
    }
}
