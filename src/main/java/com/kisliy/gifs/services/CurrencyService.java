package com.kisliy.gifs.services;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import com.kisliy.gifs.client.OpenExchangeRatesClient;
import com.kisliy.gifs.dto.OpenExchangeDto;
import feign.Feign;
import feign.Logger;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

@Service
public class CurrencyService {

    @Value("${currency.base}")
    private String baseCur;

    @Value("${openexchangeclient.url}")
    private String url;

    @Value("${openexchangeclient.key}")
    private String key;

    private OpenExchangeRatesClient openExchangeRatesClient;

    @PostConstruct
    public void init() {
        this.openExchangeRatesClient = Feign.builder()
                .client(new OkHttpClient())
                .decoder(new GsonDecoder())
                .encoder(new GsonEncoder())
                .logger(new Slf4jLogger(OpenExchangeRatesClient.class))
                .logLevel(Logger.Level.FULL)
                .contract(new SpringMvcContract())
                .target(OpenExchangeRatesClient.class, url);
    }

    public int compareCurrency(String currencySymbol) {

        OpenExchangeDto yesterday = openExchangeRatesClient.getHistory(
                baseCur,
                Collections.singletonList(currencySymbol),
                key,
                LocalDate.now().minusDays(1)
        );

        OpenExchangeDto today = openExchangeRatesClient.getLatest(
                baseCur,
                Collections.singletonList(currencySymbol),
                key
        );

        BigDecimal yesterdayVal = yesterday.getRates().get(currencySymbol);
        BigDecimal todayVal = today.getRates().get(currencySymbol);


        return yesterdayVal.compareTo(todayVal);
    }

}
