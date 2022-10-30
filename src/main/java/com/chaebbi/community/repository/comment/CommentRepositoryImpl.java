package com.chaebbi.community.repository.comment;

import com.chaebbi.community.domain.Comment;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final EntityManager em;
    @Override
    public void deleteComment(int userIdx, int commentIdx) {
        em.createQuery("delete from Comment c where c.userIdx = :userIdx and c.idx = :commentIdx")
                .setParameter("userIdx", userIdx)
                .setParameter("commentIdx", commentIdx)
                .executeUpdate();
    }

    @Override
    public List<Comment> getComment(int userIdx) {
        return em.createQuery("select c from Comment c where c.userIdx = :userIdx order by c.createdAt DESC")
                .setParameter("userIdx", userIdx)
                .getResultList();
    }

    @Override
    public Long checkComment(int commentIdx) {
        return (Long) em.createQuery("select count(c.idx) from Comment c where c.idx = :idx")
                .setParameter("idx", commentIdx)
                .getSingleResult();
    }


}
