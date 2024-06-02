package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name="users")
public class User {
    @Id
    private String username;
    private String email;
    private String password;
    private String usertype;

    // Will overtake as default constructos
    public User(String username, String password, String usertype, String Email) {
        this.username = username;
        this.password = password;
        this.usertype = usertype;
        this.email = Email;
    }

    public User(String username, String password, String usertype) {
        this.username = username;
        this.password = password;
        this.usertype = usertype;
    }

    public User() { 
    }

    // Username
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    // Password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Usertype
    public String getUsertype() {
        return usertype;
    }
    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    // Email
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) { this.email = email;}
   
}
