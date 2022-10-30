package com.chaebbi.community.repository;

import com.chaebbi.community.domain.Posting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {


    @Transactional
    void deleteByIdx(Long postIdx);
}
