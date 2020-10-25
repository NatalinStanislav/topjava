package ru.javawebinar.topjava.web;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    public LocalTime convert(String source) {
        try {
            return LocalTime.parse(source);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}
