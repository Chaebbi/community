package com.chaebbi.community.repository.thumbup;

import com.chaebbi.community.domain.Thumbup;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.Optional;

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

    @Override
    public Long checkThumbup(int userIdx, int postIdx) {
        return (Long) em.createQuery("select count(th.idx) from Thumbup th where th.userIdx = :userIdx and th.postIdx = :postIdx")
                .setParameter("userIdx", userIdx)
                .setParameter("postIdx", postIdx)
                .getSingleResult();
    }

    @Override
    public Long getThumbupCount(int postIdx){
        return (Long) em.createQuery("select count(th.idx) from Thumbup th where th.postIdx = :postIdx")
                .setParameter("postIdx", postIdx)
                .getSingleResult();
    }



}
