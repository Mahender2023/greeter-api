package com.example.greeter_api;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController tells Spring that this class will handle web requests.
@RestController
public class GreetingController {

    // This annotation is very important for now. It allows our Angular app
    // (which will run on localhost:4200) to talk to this server (running on localhost:8080).
    // This is a security feature in browsers called CORS.
	@CrossOrigin(origins = "*")

    // @GetMapping maps HTTP GET requests for "/api/greet" to this method.
    @GetMapping("/api/greet")
    public Greeting greet(@RequestParam(defaultValue = "World") String name) {
        // We create a message using the 'name' parameter from the URL.
        String message = String.format("Hello, %s! Greetings from the Spring Boot server!", name);
        // We return a 'Greeting' object, which Spring will automatically
        // convert into JSON format for the response.
        return new Greeting(message);
    }
}

// This is a simple Java "record" to hold our data.
// It's a modern, concise way to create a class that's just a data carrier.
record Greeting(String message) {}