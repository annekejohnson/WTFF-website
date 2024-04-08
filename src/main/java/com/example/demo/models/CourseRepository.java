package com.example.demo.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Integer>{
    Course findByCoursename(String coursename);
    void deleteByCoursename(String coursename);

    Course findById(int id);
}