package com.chaebbi.community.validation;

import com.chaebbi.community.domain.Images;
import com.chaebbi.community.exception.chaebbiException;
import com.chaebbi.community.service.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

import static com.chaebbi.community.exception.CodeAndMessage.*;

@Controller
@RequiredArgsConstructor
public class ImageValidationController {
    private final ImagesService imagesService;

    public void validateImageIdx(Long imageIdx) {
        Optional<Images> image = imagesService.findByIdx(imageIdx);
        if(image == Optional.<Images>empty()) throw new chaebbiException(NO_MATCHING_IMAGES);
    }
}
