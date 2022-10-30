package com.chaebbi.community.repository.thumbup;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
public class ThumbupRepositoryImpl implements ThumbupRepositoryCustom {
    private final EntityManager em;

    @Override
    public void deleteThumbup(int userIdx, int postIdx) {
        em.createQuery("delete from Thumbup th where th.userIdx = :userIdx and th.postIdx = :postIdx")
                .setParameter("userIdx", userIdx)
                .setParameter("postIdx", postIdx)
                .executeUpdate();
    }
}
