package com.chaebbi.community.repository.thumbup;

import com.chaebbi.community.domain.Thumbup;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public interface ThumbupRepository extends JpaRepository<Thumbup, Long>, ThumbupRepositoryCustom {
}
