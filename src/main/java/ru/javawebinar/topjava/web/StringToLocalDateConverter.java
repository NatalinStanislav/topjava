package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    public LocalDate convert(String source) {
        try {
            return LocalDate.parse(source);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
