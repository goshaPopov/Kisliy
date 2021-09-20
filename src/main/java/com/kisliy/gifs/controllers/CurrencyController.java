package com.kisliy.gifs.controllers;

import com.kisliy.gifs.services.CurrencyService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CurrencyController {

    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/getGif")
    public HttpEntity<Integer> getGifs(@RequestParam("code") String code) {

        

        return new ResponseEntity<>(currencyService.compareCurrency(code), HttpStatus.OK);
    }


}
