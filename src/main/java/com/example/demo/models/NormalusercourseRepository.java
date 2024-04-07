package com.example.demo.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NormalusercourseRepository extends JpaRepository<Normalusercourse, Integer>{
    User findByUsername(String username);
    Course findByCourseID(int courseID);

    Normalusercourse findByUsernameAndCourseID(String username, int courseID);
    //Boolean matchByUsernameAndCourseID

    List<Normalusercourse> findCoursesByUsername(String username);
    void deleteByUsernameAndCourseID(String username, int courseID);
    void deleteByUsername(String username);
    // method names has to reflect the property it handles or else it wont DEBUG AAAA
    // THERE IS ORDER. TO THE NAMING; IF U PUT USERNAME IN NAMING FIRST BUT QUERY IS COURSEID FIRST, IT WILL ERROR.
    // if all else fails. keep doing ctrl+f on terminal for 'error'
}
