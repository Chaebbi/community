package com.chaebbi.community.service;

import com.chaebbi.community.domain.Thumbup;
import com.chaebbi.community.repository.thumbup.ThumbupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class ThumbupService {
    private final ThumbupRepository thumbupRepository;

    public Long checkThumbup(int userIdx, int postIdx) {
        return thumbupRepository.checkThumbup(userIdx, postIdx);
    }

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
