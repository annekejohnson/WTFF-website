package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.User;
import com.example.demo.models.UserRepository;
import com.example.demo.models.NormalusercourseRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;

@Controller
public class UsersController {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private NormalusercourseRepository normalusercourseRepository;

    public UsersController(UserRepository mockUserRepo) {
        //TODO Auto-generated constructor stub
    }
    
    @ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute("isLoggedIn")
    public boolean addIsLoggedInAttribute(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute("session_user") != null;
    }
}

    @GetMapping("/users/page")
    public String userPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/users/login"; // Redirect to login if there is no user in the session
        }

        User user = (User) session.getAttribute("session_user");
        model.addAttribute("user", user); // Make sure the user is added to the model
        if ("admin".equals(user.getUsertype().toLowerCase())) {
            return "/users/adminPage"; // Redirect to admin dashboard
        } else {
            return "/users/userPage";
        } // Redirect to user dashboard
    }


    @GetMapping("/users/view")
    public String getAllUsers(Model model){
        System.out.println("Getting all users");
        //TODO: get all users from database
        List<User> users = userRepo.findAll();
        //end of database call
        model.addAttribute("us", users);
        return "/users/showAll";
    }

    @GetMapping("/")
    public String process(){
        return ("/users/login");
    }

    // @GetMapping("/")
    // public String process(){
    //     return ("users/login");
    // }
    
    
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
        userRepo.save(new User(newName, newPwd, "user"));
        response.setStatus(201);
        return "/users/feedback/addedUser";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if (user == null){
            return "/users/login";
        }
        else{
            model.addAttribute("user", user);
        //     if ("admin".equals(user.getUsertype().toLowerCase())) {
        //         return "/users/adminPage"; // Redirect to admin dashboard
        //     } else {
        //         return "/users/feedback/loginSuccess"; // Redirect to user dashboard
        //     }
        return "/users/feedback/loginSuccess";
        }
    }

    // @GetMapping("/UserPage")
    // public String getUserPage(Model model, HttpServletRequest request, HttpSession session){
    //     User user = (User) session.getAttribute("session_user");
    //     if (user == null){
    //         return "login";
    //     }
    //     else{
    //         model.addAttribute("user", user);
    //         if ("admin".equals(user.getUsertype().toLowerCase())) {
    //             return "users/adminPage"; // Redirect to admin dashboard
    //         } else {
    //             return "users/feedback/loginSuccess"; // Redirect to user dashboard
    //         }
    //     }
    // }

    @PostMapping("/users/login")
    public String login(@RequestParam Map<String, String> formData, Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes){
        // processing logins
        if (formData.get("username") == null || formData.get("username").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your username.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/users/login";
        }
        if (formData.get("password") == null || formData.get("password").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your password.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/users/login";
        }
        String username = formData.get("username");
        String pwd = formData.get("password");
        List<User> userList = userRepo.findByUsernameAndPassword(username, pwd);
        if (userList.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Incorrect username or password.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/users/login";
        }
        else{
            //sucess
            User user = userList.get(0);
            request.getSession().setAttribute("session_user", user);
            session.setAttribute("currentUser", user);

            model.addAttribute("user", user);
            // if ("admin".equals(user.getUsertype().toLowerCase())) {
            //     return "users/adminPage"; // Redirect to admin dashboard
            // } else {
                return "/users/feedback/loginSuccess"; // Redirect to user dashboard
        // }
    }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        // return "login";
        return "/users/feedback/logoutSuccess";
    }

    @GetMapping("/users/deleted")
    public String getDeletePage(){
        return "/users/feedback/deleted";
    }

    // Delete clicked student (ID is retrieved when clicked)
    @Transactional
    @PostMapping("/users/delete/{username}")
    public String deleteStudent(@PathVariable("username") String username, HttpServletRequest request) {
        // if user is deleted delete all of it's occurences in the table
        normalusercourseRepository.deleteByUsername(username); 
        userRepo.deleteByUsername(username);
        request.getSession().invalidate();
        return "/users/deleted";
    }

    // Add a Get mapping to show the edit form
    @GetMapping("/users/edit/{username}")
    public String showEditForm(@PathVariable("username") String username, Model model) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            return "redirect:/users/view";
        }
        model.addAttribute("user", user);
        return "/users/edit";
    }

    // Add a Post mapping to update the password
    @PostMapping("/users/updatePassword")
    public String updatePassword(@RequestParam("password") String password,
                                 HttpServletRequest request, 
                                 RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("session_user");
    
        if (sessionUser == null) {
            redirectAttributes.addFlashAttribute("error", "No user logged in.");
            return "redirect:/users/login";
        }
    
        if (password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password cannot be empty.");
            return "redirect:/users/edit/" + sessionUser.getUsername();
        }
    
        sessionUser.setPassword(password);
        userRepo.save(sessionUser);
        redirectAttributes.addFlashAttribute("success", "Password updated successfully.");
        return "/users/feedback/edited";
    }
    
    // @GetMapping("/Home")
    // public String getMethodName(@RequestParam String param, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    //     HttpSession session = request.getSession();
    //     User sessionUser = (User) session.getAttribute("session_user");
    
    //     // if (sessionUser == null) {
    //     //     redirectAttributes.addFlashAttribute("error", "No user logged in.");
    //     //     return "redirect:/login";
    //     // }
    //     return "pages/Home";
    // }
    
    
    // @GetMapping("/Home")
    // public String goHome(@RequestParam String param, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    //         HttpSession session = request.getSession();
    //         User sessionUser = (User) session.getAttribute("session_user");
    //         if (sessionUser == null) {

    //         }
    //     return "pages/Home";
    // }
    @GetMapping("/Home")
    public String goHome() {
        return "/pages/Home";
    }
    @GetMapping("/AboutUs")
    public String goAboutUs() {
        return "/pages/AboutUs";
    }
    @GetMapping("/Donate")
    public String goDonate() {
        return "/pages/donate";
    }
    @GetMapping("/QnA")
    public String goQnA() {
        return "/pages/QnA";
    }
    @GetMapping("/Resources")
    public String goResources() {
        return "/pages/resources";
    }
    @GetMapping("/Volunteer")
    public String goVolunteer() {
        return "/pages/Volunteer";
    }
    @GetMapping("/courses")
    public String goCourses() {
        return "/pages/COURSES/courses";
    }


}