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

    @ModelAttribute("username")
    public String addUsernameAttribute(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("session_user") != null) {
        // Assuming session_user is an object that has a getUsername() method
        User user = (User) session.getAttribute("session_user");
        return user.getUsername();
    }
    return null; // or some default value
    }

    @ModelAttribute("usertype")
    public boolean addUsertypeAttribute(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session != null && session.getAttribute("session_user") != null) {
        // Assuming session_user is an object that has a getUsername() method
        User user = (User) session.getAttribute("session_user");
        if("pluto".equals(user.getUsername())){
        return false;
        }
        else{
            return true;
        }
    }
    return false; // or some default value
    }

    
}

    // @GetMapping("/users/page")
    // public String userPage(Model model, HttpServletRequest request) {
    //     HttpSession session = request.getSession(false);
    //     if (session == null || session.getAttribute("session_user") == null) {
    //         return "redirect:/login"; // Redirect to login if there is no user in the session
    //     }

    //     User user = (User) session.getAttribute("session_user");
    //     model.addAttribute("user", user); // Make sure the user is added to the model
    //     if ("admin".equals(user.getUsertype().toLowerCase())) {
    //         return "users/pages/adminPage"; // Redirect to admin dashboard
    //     } else {
    //         return "users/pages/userPage";
    //     } // Redirect to user dashboard
    // }
    @GetMapping("/users/page")
    public String userPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/login"; // Redirect to login if there is no user in the session
        }

        User user = (User) session.getAttribute("session_user");
        model.addAttribute("user", user); // Make sure the user is added to the model
        if ("admin".equals(user.getUsertype().toLowerCase())) {
            return "redirect:/Home"; 
        } 
            return "users/pages/userPage";
         // Redirect to user dashboard
    }
    @GetMapping("/admin/page")
    public String adminPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/login"; // Redirect to login if there is no user in the session
        }

        User user = (User) session.getAttribute("session_user");
        model.addAttribute("user", user); // Make sure the user is added to the model
        if ("admin".equals(user.getUsertype().toLowerCase())) {
            return "users/pages/adminPage"; // Redirect to admin dashboard
        } else {
            return "redirect:/Home"; 
        } // Redirect to user dashboard
    }
    @GetMapping("/admin/add")
    public String adminAdd(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/login"; // Redirect to login if there is no user in the session
        }

        User user = (User) session.getAttribute("session_user");
        model.addAttribute("user", user); // Make sure the user is added to the model
        if ("admin".equals(user.getUsertype().toLowerCase())) {
            return "users/admin/adminAdd"; // Redirect to admin dashboard
        } else {
            return "redirect:/Home"; 
        } // Redirect to user dashboard
    }
    @GetMapping("/admin/delete")
    public String adminDelete(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/login"; // Redirect to login if there is no user in the session
        }

        User user = (User) session.getAttribute("session_user");
        model.addAttribute("user", user); // Make sure the user is added to the model
        if ("admin".equals(user.getUsertype().toLowerCase())) {
            return "users/admin/adminDelete"; // Redirect to admin dashboard
        } else {
            return "redirect:/Home"; 
        } // Redirect to user dashboard
    }
    @GetMapping("/admin/edit")
    public String adminEdit(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("session_user") == null) {
            return "redirect:/login"; // Redirect to login if there is no user in the session
        }

        User user = (User) session.getAttribute("session_user");
        model.addAttribute("user", user); // Make sure the user is added to the model
        if ("admin".equals(user.getUsertype().toLowerCase())) {
            return "users/admin/adminUpdate"; // Redirect to admin dashboard
        } else {
            return "redirect:/Home"; 
        } // Redirect to user dashboard
    }


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
    public String process(){
        return ("users/login");
    }

    // @GetMapping("/")
    // public String process(){
    //     return ("users/login");
    // }
    
    
    @GetMapping("/users/signUp")
    public String signUp(){
        return "users/signUp";
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
        return "users/feedback/addedUser";
    }

    @GetMapping("/login")
    public String getLogin(Model model, HttpServletRequest request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if (user == null){
            return "users/login";
        }
        else{
            model.addAttribute("user", user);
        //     if ("admin".equals(user.getUsertype().toLowerCase())) {
        //         return "/users/adminPage"; // Redirect to admin dashboard
        //     } else {
        //         return "/users/feedback/loginSuccess"; // Redirect to user dashboard
        //     }
        return "users/feedback/loginSuccess";
        //
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
            // session.setAttribute("currentUser", user);

            model.addAttribute("user", user);
            // if ("admin".equals(user.getUsertype().toLowerCase())) {
            //     return "users/adminPage"; // Redirect to admin dashboard
            // } else {
                return "users/feedback/loginSuccess"; // Redirect to user dashboard
        // }
    }
    }

    @GetMapping("/logout")
    public String destroySession(HttpServletRequest request){
        request.getSession().invalidate();
        // return "login";
        return "users/feedback/logoutSuccess";
    }

    @GetMapping("/users/deleted")
    public String getDeletePage(){
        return "users/feedback/deleted";
    }

    // Delete clicked student (ID is retrieved when clicked)
    @Transactional
    @PostMapping("/users/delete/{username}")
    public String deleteStudent(@PathVariable("username") String username, HttpServletRequest request) {
        // if user is deleted delete all of it's occurences in the table
        normalusercourseRepository.deleteByUsername(username); 
        userRepo.deleteByUsername(username);
        request.getSession().invalidate();

        // return "users/deleted";
        return "users/feedback/deleted";
    }

    // Add a Get mapping to show the edit form
    @GetMapping("/users/edit/{username}")
    public String showEditForm(@PathVariable("username") String username, Model model) {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            return "redirect:/users/view";
        }
        model.addAttribute("user", user);
        return "users/edit";
        //brooklyn's code has /users/edit instead, idk if that affects
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
            //this was prev redirect/login
        }
    
        if (password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password cannot be empty.");
            return "redirect:/users/edit/" + sessionUser.getUsername();
        }
    
        sessionUser.setPassword(password);
        userRepo.save(sessionUser);
        redirectAttributes.addFlashAttribute("success", "Password updated successfully.");
        return "users/feedback/edited";
        // prev return "users/edited";
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
        return "pages/Home";
    }
    @GetMapping("/AboutUs")
    public String goAboutUs() {
        return "pages/AboutUs";
    }
    @GetMapping("/Donate")
    public String goDonate() {
        return "pages/donate";
    }
    @GetMapping("/QnA")
    public String goQnA() {
        return "pages/QnA";
    }
    @GetMapping("/Resources")
    public String goResources() {
        return "pages/resources";
    }
    @GetMapping("/Volunteer")
    public String goVolunteer() {
        return "pages/Volunteer";
    }
    @GetMapping("/courses")
    public String goCourses() {
        return "redirect:/courseDisplay";
    }

    @GetMapping("/Exit")
    public String goExit() {
        return "redirect:https://www.theweathernetwork.com/ca";
    }
    

    //--------------------------------------------------------------------------------------------------------------
    // basically everything under here is for when user clicks to enroll in a course BUT THEY ARE NOT IN SESSION -- but after they login/signup will ENROLL THEM in the course automatically <3
    // vv for when user wants to enroll but out of session
    
    @GetMapping("/loginSpecial")
    public String getLoginSpecial(@RequestParam("courseId") int courseId, Model model, HttpServletRequest request, HttpSession session){
        User user = (User) session.getAttribute("session_user");
        if (user == null){
            model.addAttribute("courseId", courseId);
            return "courses/loginSpecial";
        }
        else{
            model.addAttribute("user", user);
            if ("admin".equals(user.getUsertype().toLowerCase())) {
                return "users/pages/adminPage"; // Redirect to admin dashboard
            } else {
                return "redirect:/redirection?courseId=" + courseId; // Redirect to user's course dashboard ENROLLED.
            }
        }
    }

    @PostMapping("/courses/specialLogin")
    public String specialLogin(@RequestParam Map<String, String> formData, @RequestParam("courseId") int courseId, Model model, HttpServletRequest request, HttpSession session, RedirectAttributes redirectAttributes){
        
        // processing logins
        if (formData.get("username") == null || formData.get("username").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your username.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/loginSpecial?courseId=" + courseId;
        }
        if (formData.get("password") == null || formData.get("password").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Please enter your password.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/loginSpecial?courseId=" + courseId;
        }
        String username = formData.get("username");
        String pwd = formData.get("password");
        List<User> userList = userRepo.findByUsernameAndPassword(username, pwd);
        if (userList.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Incorrect username or password.");
            redirectAttributes.addFlashAttribute("user", formData);
            return "redirect:/loginSpecial?courseId=" + courseId;
        }
        else{
            //sucess
            User user = userList.get(0);
            request.getSession().setAttribute("session_user", user);
            session.setAttribute("currentUser", user);

            model.addAttribute("user", user);
            if ("admin".equals(user.getUsertype().toLowerCase())) {
                return "admin/page"; // Redirect to admin dashboard
            } 
            else {
                return "redirect:/redirection?courseId=" + courseId; // Redirect to user's course dashboard ENROLLED.
        }
    }
    }
    //----------------------------------------------------------------------------------------------------------------
    // vv for when NEW USER makes new account while want to enroll

    @GetMapping("/signUpSpecial")
    public String signUpSpecial(@RequestParam("courseId")int courseId, Model model){
        model.addAttribute("courseId", courseId);
        return "courses/signUpSpecial";
    }

    @PostMapping("/courses/specialSignUp")
    public String specialSignUp(@RequestParam Map<String, String> newuser, @RequestParam("courseId") int courseId, Model model, HttpServletResponse response, RedirectAttributes redirectAttributes){
        System.out.println("New User");
        model.addAttribute("courseId", courseId);

        if (newuser.get("username") == null || newuser.get("username").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Username cannot be empty.");
            redirectAttributes.addFlashAttribute("user", newuser);
            return "redirect:/signUpSpecial?courseId=" + courseId;
        }
        if (userRepo.findByUsername(newuser.get("username")) != null) {
            redirectAttributes.addFlashAttribute("error", "Username is taken.");
            redirectAttributes.addFlashAttribute("error", newuser);
            return "redirect:/signUpSpecial?courseId=" + courseId;
        }
        if (newuser.get("password") == null || newuser.get("password").isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Password cannot be empty.");
            redirectAttributes.addFlashAttribute("user", newuser);
            return "redirect:/signUpSpecial?courseId=" + courseId;
        }
        String newName = newuser.get("username");
        String newPwd = newuser.get("password");
        userRepo.save(new User(newName, newPwd, "user"));
        response.setStatus(201);
        return "redirect:/redirection?courseId=" + courseId; // Redirect to user's course dashboard ENROLLED.
    }
}
