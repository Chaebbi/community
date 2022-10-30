package com.chaebbi.community.service;

import com.chaebbi.community.domain.Comment;
import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.repository.comment.CommentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@WebAppConfiguration
@SpringBootTest
@Transactional
public class CommentServiceTest {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    CommunityUserService userService;

    @Autowired
    PostingService postingService;

    @Autowired
    private CommentService commentService;

    @Test
    void 댓글생성() {
        // given
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);

        Posting post = new Posting();
        String content = "안녕하세요";
        String title = "제목";
        Long userIdx = userT.getIdx();
        Posting create_post = postingService.create(userIdx, content, title);
        Posting save_post = postingService.save(create_post);

        Comment comment = new Comment();
        comment.setUserIdx(userT.getIdx().intValue());
        comment.setPostIdx(save_post.getIdx().intValue());
        comment.setContent("안녕하세요!");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        // when
        commentService.createComment(comment);
    }

    @Test
    void 댓글삭제() {
        // given
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);

        Posting post = new Posting();
        String content = "안녕하세요";
        String title = "제목";
        Long userIdx = userT.getIdx();
        Posting create_post = postingService.create(userIdx, content, title);
        Posting save_post = postingService.save(create_post);

        Comment comment = new Comment();
        comment.setUserIdx(userT.getIdx().intValue());
        comment.setPostIdx(save_post.getIdx().intValue());
        comment.setContent("안녕하세요!");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.createComment(comment);

        // when
        commentService.deleteComment(userT.getIdx().intValue(), comment.getIdx());
    }

    @Test
    void 내가쓴댓글조회() {
        // given
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);

        Posting post = new Posting();
        String content = "안녕하세요";
        String title = "제목";
        Long userIdx = userT.getIdx();
        Posting create_post = postingService.create(userIdx, content, title);
        Posting save_post = postingService.save(create_post);

        Comment comment = new Comment();
        comment.setUserIdx(userT.getIdx().intValue());
        comment.setPostIdx(save_post.getIdx().intValue());
        comment.setContent("안녕하세요!");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.createComment(comment);

        // when
        List<Comment> comments = commentService.getComment(userT.getIdx().intValue());

        // then
        Assertions.assertEquals(comments.size(), 1);
        Assertions.assertEquals(comments.get(0).getContent(), comment.getContent());
        Assertions.assertEquals(comments.get(0).getUserIdx(), comment.getUserIdx());
        Assertions.assertEquals(comments.get(0).getPostIdx(), save_post.getIdx());
    }
}
