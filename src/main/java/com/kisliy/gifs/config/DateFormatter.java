package com.kisliy.gifs.config;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Locale;

@Component
public class DateFormatter implements Formatter<LocalDate> {

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public LocalDate parse(String text, Locale locale) throws ParseException {
        return formatter.parse(text).toInstant().atZone(ZoneOffset.systemDefault()).toLocalDate();
    }

    @Override
    public String print(LocalDate date, Locale locale) {
        return formatter.format(date);
    }
}
