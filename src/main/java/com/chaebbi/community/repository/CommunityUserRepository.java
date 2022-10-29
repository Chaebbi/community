package com.chaebbi.community.repository;

import com.chaebbi.community.domain.CommunityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository

public interface CommunityUserRepository extends JpaRepository<CommunityUser, Long> {

    //private final EntityManager em;







}
