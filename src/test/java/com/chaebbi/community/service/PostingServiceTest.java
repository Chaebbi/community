package com.chaebbi.community.service;

import com.chaebbi.community.domain.*;
import com.chaebbi.community.dto.response.AllPostsListDto;
import com.chaebbi.community.dto.response.CheckMyPostsDto;
import com.chaebbi.community.dto.response.PostDetailDto;
import com.chaebbi.community.repository.PostingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.parameters.P;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@WebAppConfiguration
@SpringBootTest
@Transactional
class PostingServiceTest {
    @Autowired
    CommunityUserService userService;

    @Autowired
    PostingService postingService;

    @Autowired
    PostingRepository postingRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private ThumbupService thumbupService;
    @Autowired
    private ImagesService imagesService;

    @Test
    void 포스트저장() {
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);

        Posting post = new Posting();
        String content = "안녕하세요 오늘은 테스트입니다";
        String title = "안녕하세요 테스트 제목임";
        Long userIdx = userT.getIdx();
        Posting create_post = postingService.create(userIdx, content, title);
        Posting save_post = postingService.save(create_post);

    }

    @Test
    void 포스트삭제() {
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);

        Posting create_post = postingService.create(userT.getIdx(), "new post", "new post title");
        Posting save_post =postingService.save(create_post);

        Long postIdx = save_post.getIdx();
        postingService.deletePost(postIdx);
        Optional<Posting> delPost = postingService.findById(postIdx);
        assertEquals(delPost, Optional.<Posting>empty());

    }

    @Test
    void 포스트수정() {
        CommunityUser user = new CommunityUser();
        String nickname = "dr.김";
        user.setNickname(nickname);
        user.setIdx(13L);
        CommunityUser userT = userService.save(user);

        Posting createPost = postingService.create(userT.getIdx(), "new post", "new post title");
        Posting savedPost =postingService.save(createPost);    // 포스트 저장

        //수정 내역 발생
        String updateContent = "update Content";
        String updateTitle = "update Title";
        Posting newPost = postingService.update(savedPost, updateContent, updateTitle);

        assertEquals(newPost.getTitle(), updateTitle);
        assertEquals(newPost.getContent(), updateContent);


    }

    @Test
    void 게시글_한개_조회() {
        // user Kim 과 Choi 생성
        CommunityUser user1 = new CommunityUser();
        user1.setNickname("dr.김");
        user1.setIdx(333L);
        user1.setUserIdx(13L);
        CommunityUser userK = userService.save(user1);

        CommunityUser user2 = new CommunityUser();
        user2.setNickname("dr.최");
        user2.setIdx(444L);
        user2.setUserIdx(14L);
        CommunityUser userC = userService.save(user2);

        //Kim이 게시글 생성
        Posting createPost = postingService.create(userK.getUserIdx(), "new post", "new post title by Kim");
        Posting savedPost =postingService.save(createPost);    // 포스트 저장

        //Choi가 댓글 달고 따봉함
        Comment comment = new Comment();
        comment.setUserIdx(userC.getUserIdx().intValue());
        comment.setPostIdx(savedPost.getIdx().intValue());
        comment.setContent("안녕하세요! 김선생님 최입니다.");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.createComment(comment);

        Thumbup thumbup = Thumbup.createThumbup(userC.getIdx().intValue(), savedPost.getIdx().intValue());
        thumbupService.createThumbup(thumbup);

        List<Images> imageList = imagesService.findByPostIdx(savedPost.getIdx());
        PostDetailDto postDetailDto = postingService.detailPost(savedPost.getIdx(), savedPost, imageList);

        // 게시글의 작성자가 Kim이 맞는가
        assertEquals(userK.getNickname(), postDetailDto.getNickname());
        // 게시글 댓글의 닉네임이 Choi가 맞는가
        assertEquals(userC.getNickname(), postDetailDto.getCommentsLists().get(0).getNickname());
        // 따봉의 수가 1개가 맞는가
        assertEquals(1, postDetailDto.getThumbupCount());
        // 댓글의 수가 1개가 맞는가
        assertEquals(1, postDetailDto.getCommentCount());


    }
    @Test
    void 내_게시글_조회() {
        // user Kim 생성
        CommunityUser user1 = new CommunityUser();
        user1.setNickname("dr.김");
        user1.setIdx(333L);
        user1.setUserIdx(13L);
        CommunityUser userK = userService.save(user1);


        //Kim이 게시글 생성
        Posting createPost = postingService.create(userK.getUserIdx(), "new post", "new post title by Kim");
        Posting savedPost1 =postingService.save(createPost);    // 포스트 저장

        Posting createPost2 = postingService.create(userK.getUserIdx(), "new post2", "2nd new post title by Kim");
        Posting savedPost2 =postingService.save(createPost2);

        CheckMyPostsDto checkMyPostsDto = postingService.checkMyPosts(userK.getUserIdx());

        // 게시글의 수가 2개인가
        assertEquals(2, checkMyPostsDto.getPostCount());
        // 게시글의 제목과 내용이 맞는가
        assertEquals(savedPost1.getTitle(), checkMyPostsDto.getPostsLists().get(0).getTitle());
        assertEquals(savedPost1.getContent(), checkMyPostsDto.getPostsLists().get(0).getContent());

        assertEquals(savedPost2.getTitle(), checkMyPostsDto.getPostsLists().get(1).getTitle());
        assertEquals(savedPost2.getContent(), checkMyPostsDto.getPostsLists().get(1).getContent());


    }

    @Test
    void 전체_게시글_조회() {
        // user Kim, Choi 생성
        CommunityUser user1 = new CommunityUser();
        user1.setNickname("dr.김");
        user1.setIdx(333L);
        user1.setUserIdx(13L);
        CommunityUser userK = userService.save(user1);
        CommunityUser user2 = new CommunityUser();
        user2.setNickname("dr.최");
        user2.setIdx(444L);
        user2.setUserIdx(14L);
        CommunityUser userC = userService.save(user2);


        // 게시글 3개 생성
        Posting createPost1 = postingService.create(userK.getUserIdx(), "new post", "new post title by Kim");
        Posting savedPost1 =postingService.save(createPost1);    // 포스트 저장
        Posting createPost2 = postingService.create(userK.getUserIdx(), "new post2", "2nd new post title by Kim");
        Posting savedPost2 =postingService.save(createPost2);
        Posting createPost3 = postingService.create(userK.getUserIdx(), "new post3", "2nd new post title by Kim");
        Posting savedPost3 =postingService.save(createPost3);

        // 따봉과 댓글
        Comment comment = new Comment();
        comment.setUserIdx(userC.getUserIdx().intValue());
        comment.setPostIdx(savedPost1.getIdx().intValue());
        comment.setContent("안녕하세요! 김선생님 최입니다.");
        comment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.createComment(comment);

        Comment recomment = new Comment();
        recomment.setUserIdx(userK.getUserIdx().intValue());
        recomment.setPostIdx(savedPost1.getIdx().intValue());
        recomment.setContent("안녕하세요! 최선생님 반가워요.");
        recomment.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        commentService.createComment(recomment);

        thumbupService.createThumbup(Thumbup.createThumbup(userC.getIdx().intValue(), savedPost1.getIdx().intValue()));
        thumbupService.createThumbup(Thumbup.createThumbup(userC.getIdx().intValue(), savedPost2.getIdx().intValue()));
        thumbupService.createThumbup(Thumbup.createThumbup(userC.getIdx().intValue(), savedPost3.getIdx().intValue()));

        AllPostsListDto allPostsListDto = postingService.allPostsList(userK);
        // 접속한 사용자 닉네임이 Kim이 맞는가
        assertEquals(userK.getNickname(), allPostsListDto.getNickname());

        // 게시글 따봉수,댓글수가 맞는가
        assertEquals(2, allPostsListDto.getAllPostsLists().get(0).getCommentCount());
        assertEquals(1, allPostsListDto.getAllPostsLists().get(0).getThumbupCount());

        assertEquals(0, allPostsListDto.getAllPostsLists().get(1).getCommentCount());
        assertEquals(1, allPostsListDto.getAllPostsLists().get(1).getThumbupCount());

        assertEquals(0, allPostsListDto.getAllPostsLists().get(2).getCommentCount());
        assertEquals(1, allPostsListDto.getAllPostsLists().get(2).getThumbupCount());

    }
}