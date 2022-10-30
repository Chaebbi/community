package com.chaebbi.community.validation;

import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.domain.Thumbup;
import com.chaebbi.community.dto.request.DeleteThumbupReqDto;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.PostingService;
import com.chaebbi.community.service.ThumbupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.chaebbi.community.exception.CodeAndMessage.*;

@Controller
@RequiredArgsConstructor
public class ThumbupValidationController {
    private final PostingService postingService;
    private final ThumbupService thumbupService;
    public void validatePost(int postIdx) {
        Optional<Posting> post = postingService.findById((long)postIdx);
        if(post == Optional.<Posting>empty()) throw new chaebbiException(INVALID_POST_ID);
    }

    public void validateDeleteThumbup(int userIdx, DeleteThumbupReqDto request) {
        if(thumbupService.checkThumbup(userIdx, request.getPostIdx()) <= 0) {
            throw new chaebbiException(INVALID_THUMBUP);
        }
    }
}
