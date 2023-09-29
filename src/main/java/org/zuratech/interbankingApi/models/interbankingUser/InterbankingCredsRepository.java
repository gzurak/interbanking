package org.zuratech.interbankingApi.models.interbankingUser;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InterbankingCredsRepository extends JpaRepository<InterbankingCreds, Long> {
}
