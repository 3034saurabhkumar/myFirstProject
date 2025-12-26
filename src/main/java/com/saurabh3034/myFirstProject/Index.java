package com.saurabh3034.myFirstProject;

import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.ui.Model;
// import org.springframework.stereotype.Controller; // Use this!
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
public class Index {

    @GetMapping("/")
    public String index() {
        return "I have successfully built a Spring Boot application using Maven! " +
                "This is ready for Argo CD and Kubernetes.";
    }

//    @GetMapping("/")
//    public String index(Model model) {
//        model.addAttribute("title", "Spring Boot + Maven + Bootstrap");
//        model.addAttribute("msg", "Deployed via Argo CD to Kubernetes");
//        return "index";
//    }
}
