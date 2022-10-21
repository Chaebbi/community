package com.chaebbi.community.service;

import com.chaebbi.community.domain.Thumbup;
import com.chaebbi.community.repository.ThumbupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ThumbupService {
    private final ThumbupRepository thumbupRepository;

    @Transactional
    public int createThumbup(Thumbup thumbup) {
        thumbupRepository.save(thumbup);
        return thumbup.getIdx();
    }

    @Transactional
    public void deleteThumbup(int userIdx, int postIdx) {
        thumbupRepository.deleteThumbup(userIdx, postIdx);
    }
}
