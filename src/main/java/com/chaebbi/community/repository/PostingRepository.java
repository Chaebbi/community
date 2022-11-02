package com.chaebbi.community.repository;

import com.chaebbi.community.domain.Posting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long>, CrudRepository<Posting, Long> {


    @Transactional
    void deleteByIdx(Long postIdx);

    Long countByUserIdx(Long userIdx);

    List<Posting> findAllByUserIdx(Long userIdx);
}
