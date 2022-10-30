package com.chaebbi.community.validation;

import com.chaebbi.community.dto.request.PostingDto;
import com.chaebbi.community.exception.chaebbiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import static com.chaebbi.community.exception.CodeAndMessage.*;


@Controller
@RequiredArgsConstructor
public class PostValidationController {
    public void validationPost(PostingDto post){
        if(post.getContent() == null || post.getContent().equals("")) throw new chaebbiException(EMPTY_CONTENT);  //post.getContent().isEmpty()  불가능이므로 코드 무효화
        if(post.getTitle() == null || post.getTitle().equals("")) throw new chaebbiException(EMPTY_TITLE);
    }
}
