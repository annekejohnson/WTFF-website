package com.example.demo.models;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Integer>{
    Course findBySummary(String summary);
    void deleteBySummary(String summary);

    Course findById(int id);
}