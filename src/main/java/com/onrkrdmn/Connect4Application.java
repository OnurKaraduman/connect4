package com.onrkrdmn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;


/**
 * Created by onur on 23.01.17.
 * Spring-boot application
 */
@SpringBootApplication
public class Connect4Application implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Connect4Application.class, args);
    }


    @Override
    public void run(String... args) {
        System.out.println("********************************************");
        System.out.println("*           CONNNECT4 BACKEND API          *");
        System.out.println("*           developed by ONRKRDMN          *");
        System.out.println("********************************************");
    }
}
