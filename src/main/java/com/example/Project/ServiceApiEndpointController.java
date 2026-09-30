package com.example.Project;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ServiceApiEndpointController {

    @GetMapping("/ping")
    public String ping() {
        return "ping";
    }

}
