package com.chaebbi.community.service;

import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.repository.CommunityUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityUserService {

    private final CommunityUserRepository userRepository;

    public CommunityUser save(CommunityUser user) {
        return userRepository.save(user);
    }




}
