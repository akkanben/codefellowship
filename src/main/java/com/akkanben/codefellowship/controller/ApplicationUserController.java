package com.akkanben.codefellowship.controller;

import com.akkanben.codefellowship.model.ApplicationUser;
import com.akkanben.codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;

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
    public String getLoginPage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("username", p.getName());
        }
        return "log-in.html";
    }

    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("username", p.getName());
        }
        return "index.html";
    }

    @GetMapping("/profile/{userID}")
    public String getUserProfilePage(@PathVariable long userID, Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            if (applicationUser != null)
                m.addAttribute("applicationUser", applicationUser);
        }
        ApplicationUser publicUser = applicationUserRepository.getById(userID);
        try {
            publicUser.getFirstName();
        } catch (EntityNotFoundException enfe) {
            m.addAttribute("errorMessage", "Could not find a user for that id!");
            return "index.html";
        }
            m.addAttribute("publicUser", publicUser);
            return "profile.html";
    }

    @GetMapping("/my-profile")
    public String getMyProfile(Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = (ApplicationUser) applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("applicationUser", applicationUser);
        }
        return "my-profile.html";
    }

    @PostMapping("/logout")
    public RedirectView logoutUser(Principal p) {
        if (p != null) {
            try {
                request.logout();
            } catch (ServletException e) {
                System.out.println("Error logging out.");
                e.printStackTrace();
            }
        }
        return new RedirectView("/");
    }

    @PostMapping("/create-account")
    public RedirectView addNewAccount(String username, String password, String firstname, String lastname, @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date, String bio) {
        if (applicationUserRepository.findByUsername(username) != null)
            return new RedirectView("/");
        String encryptedPassword = passwordEncoder.encode(password);
        ApplicationUser applicationUser = new ApplicationUser(username, encryptedPassword, firstname, lastname, date, bio);
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

    @PostMapping("/login")
    public RedirectView loginToApp(String username, String password) {
        return new RedirectView("/");
    }

    @PutMapping("/follow/{profileId}")
    RedirectView followUser(@PathVariable long profileId, Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            ApplicationUser publicUser = applicationUserRepository.getById(profileId);
            if (applicationUser != null && publicUser != null) {
                m.addAttribute("applicationUser", applicationUser);
                applicationUser.getFollowingSet().add(publicUser);
                applicationUserRepository.save(applicationUser);
            }
        }
        return new RedirectView("/profile/" + profileId);
    }

}
