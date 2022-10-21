package com.chaebbi.community.repository;

import com.chaebbi.community.domain.Thumbup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ThumbupRepository {
    private final EntityManager em;

    public void save(Thumbup thumbup) {
        em.persist(thumbup);
    }

    public void deleteThumbup(int userIdx, int postIdx) {
        em.createQuery("delete from Thumbup th where th.userIdx = :userIdx and th.postIdx = :postIdx")
                .setParameter("userIdx", userIdx)
                .setParameter("postIdx", postIdx)
                .executeUpdate();
    }
}
