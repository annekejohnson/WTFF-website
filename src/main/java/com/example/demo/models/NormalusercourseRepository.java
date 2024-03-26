package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalusercourseRepository extends JpaRepository<Normalusercourse, Integer>{
    User findByUsername(String username);
    Course findByCourseID(int courseID);

    List<Normalusercourse> findCoursesByUsername(String username);
    void deleteByUsernameAndCourseID(String username, int courseID);
    // method names has to reflect the property it handles or else it wont DEBUG AAAA
}
