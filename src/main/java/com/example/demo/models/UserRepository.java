package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String>{
    User findByUsername(String username);
    List<User> findByUsernameAndPassword(String username, String password);

}