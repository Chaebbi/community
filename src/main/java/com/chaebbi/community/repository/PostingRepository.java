package com.chaebbi.community.repository;

import com.chaebbi.community.domain.Posting;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {





    


}
