package com.chaebbi.community.service;

import com.chaebbi.community.aws.S3Uploader;
import com.chaebbi.community.domain.Images;
import com.chaebbi.community.repository.ImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ImagesService {

    private final S3Uploader s3Uploader;
    private final ImagesRepository imagesRepository;

    public Images save(Images image) {return  imagesRepository.save(image); }

    public Images create(Long postIdx, String imgUrl, int imgRank) {
        Images images = new Images();
        images.setPostIdx(postIdx);
        images.setImgUrl(imgUrl);
        images.setImgRank(imgRank);
        return images;
    }


    public void deleteByPostIdx(Long postIdx) {
        imagesRepository.deleteByPostIdx(postIdx);
    }

    public Optional<Images> findByIdx(Long imageIdx) { return imagesRepository.findById(imageIdx);
    }

    public void deleteImage(Long imageIdx) { imagesRepository.deleteById(imageIdx);    }
}
