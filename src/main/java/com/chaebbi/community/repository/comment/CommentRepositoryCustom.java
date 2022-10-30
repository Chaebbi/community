package com.chaebbi.community.repository.comment;

import com.chaebbi.community.domain.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepositoryCustom {
    void deleteComment(int userIdx, int commentIdx);
    List<Comment> getComment(int userIdx);

    Long checkComment(int commentIdx);
}
