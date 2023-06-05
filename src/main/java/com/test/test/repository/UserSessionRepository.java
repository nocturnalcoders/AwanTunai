package com.test.test.repository;

import com.test.test.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    UserSession findByToken(String token);
}
