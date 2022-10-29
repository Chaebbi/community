package com.chaebbi.community.service;

import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.repository.CommunityUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.persistence.EntityManager;

import java.util.Optional;

@WebAppConfiguration
@SpringBootTest
@Transactional
class CommunityUserServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    CommunityUserService userService;
    @Autowired
    CommunityUserRepository userRepository;

    @Test
    void 회원정보생성() {
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);
    }

    @Test
    // 커뮤니티 회원 생성 후 조회 테스트 (create, findOne)
    void 회원id조회() {
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        Long testIdx = 13L;
        user.setIdx(testIdx);
        user.setNickname(nickname);
        CommunityUser userC = userRepository.save(user);
        Optional<CommunityUser> userT = userRepository.findById(Long.valueOf(testIdx));

        assertEquals(userC.getNickname(), userT.get().getNickname());
        assertEquals(userC.getIdx(), userT.get().getIdx());
    }




}