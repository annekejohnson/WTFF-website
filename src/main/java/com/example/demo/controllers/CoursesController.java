package com.example.demo.controllers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

// import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.models.Course;
import com.example.demo.models.CourseRepository;
import com.example.demo.models.Normalusercourse;
import com.example.demo.models.NormalusercourseRepository;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.HttpStatus;

@Controller
public class CoursesController {

    @Autowired
    private CourseRepository courseRepo;

    @Autowired
    private NormalusercourseRepository normRepo;
    

    @PostMapping("/courses/add")
    public String addCourse(@RequestParam Map<String, String> newCourse, HttpServletResponse response){
        System.out.println("ADD course");
        String newCourseName = newCourse.get("coursename");
        if (courseRepo.findByCoursename(newCourseName) != null){
            response.setStatus(HttpStatus.CONFLICT.value());
            return "courses/error_same_name";
        }
        String newLocation = newCourse.get("location");
        String newInfo = newCourse.get("courseinfo");
        String newDescription = newCourse.get("description");
        LocalDateTime newStartdate = LocalDateTime.parse(newCourse.get("startDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        LocalDateTime newEnddate =  LocalDateTime.parse(newCourse.get("endDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        courseRepo.save(new Course(newCourseName, newInfo, newStartdate, newEnddate, newLocation, newDescription));
        response.setStatus(201);
        return "courses/success";
    }

    @PostMapping("/courses/delete")
    @Transactional
    public String deleteTimetable(@RequestParam("coursename") String coursename, HttpServletResponse response) {
        System.out.println("DELETE course");

        Course course = courseRepo.findByCoursename(coursename);
        if (course == null) {
            return "courses/error";
        }

        normRepo.deleteByCourseID(courseRepo.findByCoursename(coursename).getId());
        courseRepo.deleteByCoursename(coursename);
        return "courses/success";
    }

    @PostMapping("/courses/update")
    public String updateCourse(@RequestParam String coursename_temp, @RequestParam Map<String, String> updateCourse, HttpServletResponse response){
        System.out.println("UPDATE course");
        Course course = courseRepo.findByCoursename(coursename_temp);

        if (course == null) {
            return "courses/error";
        }
        if (updateCourse.get("coursename") != "") {
            course.setCoursename(updateCourse.get("coursename"));
        }
        if (updateCourse.get("startdate") != "") {
            course.setStartdate(LocalDateTime.parse(updateCourse.get("startDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        }
        if (updateCourse.get("enddate") != "") {
            course.setEnddate(LocalDateTime.parse(updateCourse.get("endDateTime"), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")));
        }
        if (updateCourse.get("description") != "") {
            course.setDescription(updateCourse.get("description"));
        }
        if (updateCourse.get("location") != "") {
            course.setLocation(updateCourse.get("location"));
        }
        if (updateCourse.get("courseinfo") != "") {
            course.setCourseinfo(updateCourse.get("courseinfo"));
        }
        courseRepo.save(course);

        response.setStatus(HttpServletResponse.SC_OK);

        return "courses/success";
    }
}
