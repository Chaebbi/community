package com.chaebbi.community.service;

import com.chaebbi.community.domain.CommunityUser;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.domain.Posting;
import com.chaebbi.community.dto.request.UpdatePostDto;
import com.chaebbi.community.dto.response.CommentsListDto;
import com.chaebbi.community.dto.response.ImagesListDto;
import com.chaebbi.community.dto.response.PostDetailDto;
import com.chaebbi.community.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingRepository postingRepository;
    private final ImagesService imagesService;
    private final ThumbupService thumbupService;
    private final CommentService commentService;
    private final CommunityUserService userService;
    public Posting save(Posting post) {  return postingRepository.save(post); }

    public Posting create(Long userIdx, String content, String title) {
        Posting post = new Posting();
        post.setUserIdx(userIdx);
        post.setContent(content);
        post.setTitle(title);
        post.setCreatedAt(new Timestamp(System.currentTimeMillis()));

        return post;
    }
    public Optional<Posting> findById(Long postIdx) {return  postingRepository.findById(postIdx); }


    public void deletePost(Long postIdx) {
        imagesService.deleteByPostIdx(postIdx);
        postingRepository.deleteByIdx(postIdx);
    }

    public Posting update(Posting post, String content, String title) {
        Long postIdx = post.getIdx();
        post.setContent(content);
        post.setTitle(title);
        //post.setCreatedAt(new Timestamp(System.currentTimeMillis())); 수정일자를 보일지 작성일자를 보일지 고민

        postingRepository.save(post);
        return post;

    }

    public PostDetailDto detailPost(Long postIdx, Posting post, List<Images> imageList){

        PostDetailDto postDetailDto = new PostDetailDto();
        postDetailDto.setPostIdx(post.getIdx());
        postDetailDto.setTitle(post.getTitle());
        postDetailDto.setContent(post.getContent());

        Long writerIdx = findById(postIdx).get().getUserIdx();
        CommunityUser user = userService.findByUserIdx(writerIdx).get();

        String nickname = user.getNickname();
        postDetailDto.setNickname(nickname);
        postDetailDto.setCreatedAt(new SimpleDateFormat("yyyy.MM.dd HH:mm").format(post.getCreatedAt()));
        postDetailDto.setImagesCount(imageList.size());
        if(imageList.size() != 0) {
            List<ImagesListDto> dtoList = imageList.stream()
                    .map(m -> new ImagesListDto(m.getImgUrl(), m.getImgRank()))
                    .collect(Collectors.toList());
            postDetailDto.setImagesLists(dtoList);

        } else postDetailDto.setImagesLists(null);

        Long thumbupCnt = thumbupService.getThumbupCount(postIdx.intValue());
        postDetailDto.setThumbupCount(thumbupCnt);
        Long commentCount = commentService.getCommentCnt(postIdx);
        postDetailDto.setCommentCount(commentCount);

        if(commentCount >0) {
            List<CommentsListDto> comments = commentService.getCommentList(postIdx);
            postDetailDto.setCommentsLists(comments);

        } else postDetailDto.setCommentsLists(null);

        return postDetailDto;

    }

}
