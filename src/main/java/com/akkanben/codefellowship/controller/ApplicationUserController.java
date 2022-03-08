package com.akkanben.codefellowship.controller;

import com.akkanben.codefellowship.repositories.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ApplicationUserController {

    @Autowired
    ApplicationUserRepository userRepository;

}
