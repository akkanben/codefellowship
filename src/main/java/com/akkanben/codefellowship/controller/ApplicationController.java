package com.akkanben.codefellowship.controller;

import com.akkanben.codefellowship.model.ApplicationUser;
import com.akkanben.codefellowship.model.Post;
import com.akkanben.codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController {

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    @GetMapping("/")
    public String getHomePage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("applicationUser", applicationUser);
        }
        return "index.html";
    }

    @GetMapping("/all-users")
    public String getAllUsersPage(Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("applicationUser", applicationUser);
        }
        List<ApplicationUser> allUsers = applicationUserRepository.findAll();
        m.addAttribute("allUsers", allUsers);
        return "all-users.html";
    }

    @GetMapping("/my-feed")
    public String getMyFeed(Principal p, Model m) {
        if (p != null) {
            ApplicationUser applicationUser = applicationUserRepository.findByUsername(p.getName());
            m.addAttribute("applicationUser", applicationUser);
            List<Post> followingPosts = new ArrayList<>();
            for (ApplicationUser user : applicationUser.getFollowingSet()) {
                followingPosts.addAll(user.getPostList());
            }
            m.addAttribute("allUserPosts", followingPosts);
        }
        return "my-feed.html";
    }

}
