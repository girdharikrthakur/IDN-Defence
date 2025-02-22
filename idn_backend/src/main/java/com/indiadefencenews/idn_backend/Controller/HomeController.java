package com.indiadefencenews.idn_backend.Controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//For Testing
@RestController
public class HomeController {

    @GetMapping("/home")
    public Resource home() {
        return new ClassPathResource("templates/index.html");
    }
}
