package com.akkanben.codefellowship.controller;

import com.akkanben.codefellowship.model.ApplicationUser;
import com.akkanben.codefellowship.model.Post;
import com.akkanben.codefellowship.repositories.ApplicationUserRepository;
import com.akkanben.codefellowship.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.Date;

@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ApplicationUserRepository applicationUserRepository;

    @PostMapping("/add-post")
    public RedirectView addNewPost(Principal p, Model m, String body, String subject) {
        if (p != null) {
            String username = p.getName();
            ApplicationUser applicationUser = (ApplicationUser) applicationUserRepository.findByUsername(username);
            m.addAttribute("applicationUser", applicationUser);
            Post post = new Post(body, subject);
            post.setCreatedAt(new Date());
            post.setApplicationUser(applicationUser);
            postRepository.save(post);
        }
        return new RedirectView("/my-profile");
    }
}