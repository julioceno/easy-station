package com.easy_station.sso.users;

import com.easy_station.sso.users.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByLogin(String login);
}
