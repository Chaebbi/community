package com.chaebbi.community.repository;

import com.chaebbi.community.domain.CommunityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository

public interface CommunityUserRepository extends JpaRepository<CommunityUser, Long> {

    Optional<CommunityUser> findByUserIdx(Long userIdx);








}
