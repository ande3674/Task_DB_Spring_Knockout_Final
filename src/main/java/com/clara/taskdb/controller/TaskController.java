package com.clara.taskdb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TaskController {
    // Web page routes. Only need one in this app,
    // there's only one page that's updated w JavaScript
    // calls from the client JavaScript app.
    // returns the index.html template
    @GetMapping("/")
    public String homePage() {
        return "index.html";
    }
}
