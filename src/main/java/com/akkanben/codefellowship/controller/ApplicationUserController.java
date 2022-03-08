package com.akkanben.codefellowship.controller;

import com.akkanben.codefellowship.model.ApplicationUser;
import com.akkanben.codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private HttpServletRequest request;

    @GetMapping("/sign-up")
    public String getSignUpPage() {
        return "sign-up.html";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login.html";
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = (ApplicationUser) applicationUserRepository.findByUsername(username);
            m.addAttribute("username", applicationUser.getUsername());
        }
        return "index.html";
    }


    @PostMapping("/create-account")
    public RedirectView addNewAccount(String username, String password, String firstname, String lastname, Date date, String bio) {
        String encryptedPassword = passwordEncoder.encode(password);
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(username);
        applicationUser.setPassword(encryptedPassword);
        applicationUser.setBio(bio);
        applicationUser.setDateOfBirth(date);
        applicationUser.setFirstName(firstname);
        applicationUser.setLastName(lastname);
        applicationUserRepository.save(applicationUser);
        authWithHttpServletRequest(username, password);
        return new RedirectView("/");
    }

    public void authWithHttpServletRequest(String username, String password) {
        try {
            request.login(username, password);
        } catch(ServletException se) {
            System.out.println("Error logging in");
            se.printStackTrace();
        }
    }

    @PostMapping("/login-account")
    public RedirectView loginToApp(String username, String password) {
        return new RedirectView("/");
    }

}
