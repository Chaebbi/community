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
        //if(userIdx == null) throw new chaebbiException(EMPTY_PATHVARIABLE_USERID);  // pathvariable로 있으면 null 이 불가능으므로 코드 무효화시키겠음
        Optional<CommunityUser> user = communityUserService.findByUserIdx(userIdx);
        if (user == Optional.<CommunityUser>empty()) throw new chaebbiException(EMPTY_USER);

    }
}
