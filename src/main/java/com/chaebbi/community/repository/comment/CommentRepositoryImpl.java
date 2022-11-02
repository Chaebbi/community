package com.chaebbi.community.repository.comment;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.dto.response.CommentsListDto;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Long getCommentCnt(int postIdx) {
        return (Long) em.createQuery("select count(c.idx) from Comment  c where c.postIdx = :postIdx")
                .setParameter("postIdx", postIdx)
                .getSingleResult();
    }

    @Override
    public List<CommentsListDto> getCommentList(Long postIdx) {
        String sql = "SELECT c.idx, u.nickname, c.created_at, c.content" +
                " FROM comment c JOIN community_user u" +
                " WHERE c.user_idx = u.user_idx AND c.post_idx = :postIdx" +
                " ORDER BY c.created_at";

        Query nativeQuery = em.createNativeQuery(sql)
                .setParameter("postIdx", postIdx);
        List<Object[]> resultList = nativeQuery.getResultList();
        List<CommentsListDto> commentsListDtos = new ArrayList<>();
        for(Object[] row : resultList) {
            Integer commentIdx = (Integer)row[0];
            String nickname = (String)row[1];
            Timestamp date = (Timestamp)row[2];
            String content = (String)row[3];
            commentsListDtos.add(new CommentsListDto(commentIdx, nickname, new SimpleDateFormat("yyyy.MM.dd HH:mm").format(date), content));
        }
        return commentsListDtos;
    }
}
