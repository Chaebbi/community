package com.chaebbi.community.repository;

import com.chaebbi.community.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    @Transactional
    void deleteByPostIdx(Long postIdx);
}
