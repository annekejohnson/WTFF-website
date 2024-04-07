package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;;

@Entity
@Table(name="test")

public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String summary;
    private LocalDateTime startdate;
    private LocalDateTime enddate;
    private String location;
    private String description;

    public Test() {}

    public Test(String summary, LocalDateTime startdate, LocalDateTime enddate, String location,
            String description) {
        this.summary = summary;
        this.startdate = startdate;
        this.enddate = enddate;
        this.location = location;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
