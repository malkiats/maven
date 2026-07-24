package com.example;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

/**
 * Unit Tests for Maven Java Demo Application
 */
public class AppTest {
    
    private AppService appService;
    
    @Before
    public void setUp() {
        appService = new AppService();
    }
    
    /**
     * Test addition operation
     */
    @Test
    public void testAdd() {
        int result = appService.add(5, 3);
        assertEquals("5 + 3 should equal 8", 8, result);
    }
    
    /**
     * Test addition with negative numbers
     */
    @Test
    public void testAddNegative() {
        int result = appService.add(-5, 3);
        assertEquals("-5 + 3 should equal -2", -2, result);
    }
    
    /**
     * Test addition with zero
     */
    @Test
    public void testAddZero() {
        int result = appService.add(0, 0);
        assertEquals("0 + 0 should equal 0", 0, result);
    }
    
    /**
     * Test multiplication operation
     */
    @Test
    public void testMultiply() {
        int result = appService.multiply(5, 3);
        assertEquals("5 * 3 should equal 15", 15, result);
    }
    
    /**
     * Test multiplication with zero
     */
    @Test
    public void testMultiplyZero() {
        int result = appService.multiply(5, 0);
        assertEquals("5 * 0 should equal 0", 0, result);
    }
    
    /**
     * Test multiplication with negative numbers
     */
    @Test
    public void testMultiplyNegative() {
        int result = appService.multiply(-5, 3);
        assertEquals("-5 * 3 should equal -15", -15, result);
    }
    
    /**
     * Test greeting message
     */
    @Test
    public void testGreeting() {
        String result = appService.getGreeting("Alice");
        assertTrue("Greeting should contain name", result.contains("Alice"));
    }
    
    /**
     * Test greeting with null name
     */
    @Test
    public void testGreetingNullName() {
        String result = appService.getGreeting(null);
        assertTrue("Greeting should contain default name", result.contains("User"));
    }
    
    /**
     * Test status
     */
    @Test
    public void testStatus() {
        String result = appService.getStatus();
        assertEquals("Status should match expected value", 
                   "Application is running successfully", result);
    }
}
