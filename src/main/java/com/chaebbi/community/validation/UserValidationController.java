package com.chaebbi.community.validation;

import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.CommunityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.chaebbi.community.exception.CodeAndMessage.*;

@Controller
@RequiredArgsConstructor
public class UserValidationController {
    private final CommunityUserService communityUserService;
    public void validateuser(Long userIdx) {
        Optional<CommunityUser> user = communityUserService.findByUserIdx(userIdx);
        if (user == Optional.<CommunityUser>empty()) throw new chaebbiException(EMPTY_USER);

    }
}
