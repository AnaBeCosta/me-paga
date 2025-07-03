package com.anabeatriz.mepaga.repository;

import com.anabeatriz.mepaga.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;


public interface UserRepository extends JpaRepository<User, String> {

    UserDetails findByLogin(String login);
}
