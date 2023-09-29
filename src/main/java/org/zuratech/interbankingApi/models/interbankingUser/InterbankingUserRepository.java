package org.zuratech.interbankingApi.models.interbankingUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InterbankingUserRepository extends JpaRepository<InterbankingUser, Long> {
    InterbankingUser findInterbankingUserByToken(String token);
}
