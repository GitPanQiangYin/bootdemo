package com.boot.bootdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  /*  @Value("${name}")
    private String name;
    @Value("${date}")
    private String date;*/
  @Autowired
  private author author;

        @RequestMapping("/hello")
        public String hello() {

            return author.getName() +"Hello Spring Boot!" +author.getDate();
        }

}
