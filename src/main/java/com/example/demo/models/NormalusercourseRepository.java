package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalusercourseRepository extends JpaRepository<Normalusercourse, String>{
    User findByUsername(String username);
    Course findByCourseId(int courseID);

    List<Course> findCoursesByUser(String username);
}
