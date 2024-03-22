package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="course")

public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String coursename;
    private String date;
    private String location;
    private String courseinfo;
    private String description;
    private String imagelink;

    public Course() {}

    public Course(String coursename, String date, String location, String courseinfo, String description,
            String imagelink) {
        this.coursename = coursename;
        this.date = date;
        this.location = location;
        this.courseinfo = courseinfo;
        this.description = description;
        this.imagelink = imagelink;
    }

    public String getCoursename() {
        return coursename;
    }

    public void setCoursename(String coursename) {
        this.coursename = coursename;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCourseinfo() {
        return courseinfo;
    }

    public void setCourseinfo(String courseinfo) {
        this.courseinfo = courseinfo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
