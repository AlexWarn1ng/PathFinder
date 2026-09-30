package com.example.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjectApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(ProjectApplication.class, args);

        DistrictRepository repository = context.getBean(DistrictRepository.class);
        var districts = repository.findAll();
        System.out.println(districts.size());
    }
}
