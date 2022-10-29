package com.chaebbi.community.service;

import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;

    public Posting save(Posting post) {  return postingRepository.save(post); }

    public Posting create(Long userIdx, String content) {
        Posting post = new Posting();
        post.setUserIdx(userIdx);
        post.setContent(content);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return post;
    }

}
