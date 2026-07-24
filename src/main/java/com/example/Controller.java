package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller - API Endpoints
 * 
 * Exposes REST endpoints for the application
 */
@RestController
@RequestMapping("/api")
public class AppController {
    
    @Autowired
    private AppService appService;
    
    /**
     * Health check endpoint
     * @return Health status
     */
    @GetMapping("/health")
    public Map<String, String> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("application", "Maven Java Demo App");
        response.put("version", "1.0.0");
        return response;
    }
    
    /**
     * Greeting endpoint
     * @param name - Optional name parameter
     * @return Personalized greeting
     */
    @GetMapping("/greet")
    public Map<String, String> greet(@RequestParam(value = "name", defaultValue = "User") String name) {
        Map<String, String> response = new HashMap<>();
        response.put("message", appService.getGreeting(name));
        return response;
    }
    
    /**
     * Root endpoint
     * @return Welcome message
     */
    @GetMapping("/")
    public Map<String, String> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to Maven Java Demo Application");
        response.put("endpoints", "/api/health, /api/greet, /api/status");
        return response;
    }
    
    /**
     * Status endpoint
     * @return Application status
     */
    @GetMapping("/status")
    public Map<String, String> status() {
        Map<String, String> response = new HashMap<>();
        response.put("status", appService.getStatus());
        return response;
    }
    
    /**
     * Calculation endpoint - Addition
     * @param a - First number
     * @param b - Second number
     * @return Sum result
     */
    @GetMapping("/calc/add")
    public Map<String, Object> add(@RequestParam int a, @RequestParam int b) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "addition");
        response.put("a", a);
        response.put("b", b);
        response.put("result", appService.add(a, b));
        return response;
    }
    
    /**
     * Calculation endpoint - Multiplication
     * @param a - First number
     * @param b - Second number
     * @return Product result
     */
    @GetMapping("/calc/multiply")
    public Map<String, Object> multiply(@RequestParam int a, @RequestParam int b) {
        Map<String, Object> response = new HashMap<>();
        response.put("operation", "multiplication");
        response.put("a", a);
        response.put("b", b);
        response.put("result", appService.multiply(a, b));
        return response;
    }
    
    /**
     * Echo endpoint - For testing
     * @param message - Message to echo
     * @return Echo response
     */
    @PostMapping("/echo")
    public Map<String, String> echo(@RequestBody String message) {
        Map<String, String> response = new HashMap<>();
        response.put("message", message);
        response.put("echoed", message);
        return response;
    }
}
