package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.User;
import com.example.demo.models.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/users/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        //TODO: get all users from database
        List<User> users = userRepo.findAll();
        //end of database call
        model.addAttribute("us", users);
        return "users/showAll";
    }

    @GetMapping("/")
    public RedirectView process(){
        return new RedirectView("login");
    }

    @GetMapping("/users/signUp")
    public String signUp(){
        return "/users/signUp";
    }

    @PostMapping("/users/signUp")
    public String signUp(@RequestParam Map<String, String> newuser, HttpServletResponse response, RedirectAttributes redirectAttributes){
        System.out.println("New User");
        if (newuser.get("username") == null || newuser.get("username").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Username cannot be empty.");
            redirectAttributes.addFlashAttribute("user", newuser);
            return "redirect:/users/signUp";
        }
        if (userRepo.findByUsername(newuser.get("username")) != null) {
            redirectAttributes.addFlashAttribute("error", "Username is taken.");
            redirectAttributes.addFlashAttribute("error", newuser);
            return "redirect:/users/signUp";
        }
        if (newuser.get("password") == null || newuser.get("password").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password cannot be empty.");
            redirectAttributes.addFlashAttribute("user", newuser);
            return "redirect:/users/signUp";
        }
        String newName = newuser.get("username");
        String newPwd = newuser.get("password");
        userRepo.save(new User(newName, newPwd, "admin"));
        response.setStatus(201);
        return "users/addedUser";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if (user == null){
            return "/users/login";
        }
        else{
            model.addAttribute("user", user);
            if ("admin".equals(user.getUsertype().toLowerCase())) {
                return "users/adminPage"; // Redirect to admin dashboard
            } else {
                return "users/userPage"; // Redirect to user dashboard
            }
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes){
        // processing logins
        if (formData.get("username") == null || formData.get("username").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your username.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/login";
        }
        if (formData.get("password") == null || formData.get("password").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your password.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/login";
        }
        String username = formData.get("username");
        String pwd = formData.get("password");
        List<User> userList = userRepo.findByUsernameAndPassword(username, pwd);
        if (userList.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Incorrect username or password.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/login";
        }
        else{
            //sucess
            User user = userList.get(0);
            request.getSession().setAttribute("session_user", user);
            model.addAttribute("user", user);
            if ("admin".equals(user.getUsertype().toLowerCase())) {
                return "users/adminPage"; // Redirect to admin dashboard
            } else {
                return "users/userPage"; // Redirect to user dashboard
        }
    }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        return "/users/login";
    }
}