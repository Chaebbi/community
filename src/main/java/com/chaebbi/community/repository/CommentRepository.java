package com.chaebbi.community.repository;

import com.chaebbi.community.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CommentRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void deleteComment(int userIdx, int commentIdx) {
        em.createQuery("delete from Comment c where c.userIdx = :userIdx and c.idx = :commentIdx")
                .setParameter("userIdx", userIdx)
                .setParameter("commentIdx", commentIdx)
                .executeUpdate();
    }

    public List<Comment> getComment(int userIdx) {
        return em.createQuery("select c from Comment c where c.userIdx = :userIdx order by c.createdAt DESC")
                .setParameter("userIdx", userIdx)
                .getResultList();
    }
}
