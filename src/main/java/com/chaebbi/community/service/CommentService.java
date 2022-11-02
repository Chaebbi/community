package com.chaebbi.community.service;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.dto.response.CommentsListDto;
import com.chaebbi.community.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public Long checkComment(int commentIdx) {
        return commentRepository.checkComment(commentIdx);
    }

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

    public Long getCommentCnt(Long postIdx) { return commentRepository.getCommentCnt(postIdx.intValue()); }

    public List<CommentsListDto> getCommentList(Long postIdx) {
        return commentRepository.getCommentList(postIdx);
    }
}
