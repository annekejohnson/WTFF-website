package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalusercourseRepository extends JpaRepository<Normalusercourse, Integer>{
    User findByUsername(String username);
    Course findByCourseId(int courseID);

    List<Normalusercourse> findCoursesByUser(String username);
    void deleteByUserCourse(String username, int courseID);
}
