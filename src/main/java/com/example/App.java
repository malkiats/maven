package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Entry Point for Maven Java Demo App
 * 
 * This is a Spring Boot application demonstrating a simple
 * microservice with REST endpoints for the CI/CD lab.
 */
@SpringBootApplication
public class App {
    
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        System.out.println("========================================");
        System.out.println("Maven Java Demo Application Started");
        System.out.println("========================================");
    }
}
