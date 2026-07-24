package com.example;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * Service Layer - Business Logic
 * 
 * Handles core business operations for the application
 */
@Service
public class AppService {
    
    /**
     * Returns a greeting message
     * @param name - Name to greet
     * @return Greeting message with timestamp
     */
    public String getGreeting(String name) {
        return String.format("Hello, %s! Current time: %s", 
                           name != null ? name : "User", 
                           LocalDateTime.now());
    }
    
    /**
     * Performs a simple calculation
     * @param a - First number
     * @param b - Second number
     * @return Sum of two numbers
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Performs multiplication
     * @param a - First number
     * @param b - Second number
     * @return Product of two numbers
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Returns application status
     * @return Status message
     */
    public String getStatus() {
        return "Application is running successfully";
    }
}
