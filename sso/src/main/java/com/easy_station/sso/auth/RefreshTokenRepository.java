package com.easy_station.sso.auth;

import com.easy_station.sso.auth.domain.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    RefreshToken findByUserId(String userId);
}
