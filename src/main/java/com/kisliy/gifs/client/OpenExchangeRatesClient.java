package com.kisliy.gifs.client;

import com.kisliy.gifs.dto.OpenExchangeDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface OpenExchangeRatesClient {

    @GetMapping("/historical/{date}.json")
    OpenExchangeDto getHistory(
            @RequestParam("base") String currencyBase,
            @RequestParam("symbols") List<String> symbols,
            @RequestParam("app_id") String key,
            @PathVariable("date") LocalDate date
    );

    @GetMapping("/latest.json")
    OpenExchangeDto getLatest(
            @RequestParam("base") String currencyBase,
            @RequestParam("symbols") List<String> symbols,
            @RequestParam("app_id") String key
    );


}
