package com.easy_station.sso.repositories;

import com.easy_station.sso.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
}
