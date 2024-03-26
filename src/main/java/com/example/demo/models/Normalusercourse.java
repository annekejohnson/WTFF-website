package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="normalusers_courses")

public class Normalusercourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uniqueid;
    private String username;
    private int courseID; 

    public Normalusercourse() {}

    public Normalusercourse(int uniqueid, String username, int courseID) {
        this.uniqueid = uniqueid;
        this.username = username;
        this.courseID = courseID;
    }

    public Normalusercourse(String username, int courseID) {
        this.username = username;
        this.courseID = courseID;
    }

    public Normalusercourse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public int getUniqueid() {
        return uniqueid;
    }

    public void setUniqueid(int uniqueid) {
        this.uniqueid = uniqueid;
    }
}
