package com.SpringReactCollective.The.Online.Outfitters.controller;

import com.SpringReactCollective.The.Online.Outfitters.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping()
    public ApiResponse HomeControllerHandler() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Welcome to 'The Online Outfitters'");
        return apiResponse;
    }
}
