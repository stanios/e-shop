package com.finalproject.cf.repo;

import com.finalproject.cf.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findByUserName(String userName);
}
