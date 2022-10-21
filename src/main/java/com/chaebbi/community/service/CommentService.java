package com.chaebbi.community.service;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.dto.response.GetCommentResDto;
import com.chaebbi.community.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public int createComment(Comment comment) {
        commentRepository.save(comment);
        return comment.getIdx();
    }

    @Transactional
    public void deleteComment(int userIdx, int commentIdx) {
        commentRepository.deleteComment(userIdx, commentIdx);
    }

    public List<Comment> getComment(int userIdx) {
        return commentRepository.getComment(userIdx);
    }
}
