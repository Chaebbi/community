package com.chaebbi.community.repository.thumbup;

import com.chaebbi.community.domain.Thumbup;

import java.util.Optional;

public interface ThumbupRepositoryCustom {
    void deleteThumbup(int userIdx, int postIdx);
    Long checkThumbup(int userIdx, int postIdx);
}
