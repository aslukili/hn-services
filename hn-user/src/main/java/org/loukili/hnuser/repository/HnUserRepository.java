package org.loukili.hnuser.repository;

import org.loukili.hnuser.entity.HnUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HnUserRepository extends JpaRepository<HnUser, Long> {
    Optional<HnUser> findByUsername(String username);
}
