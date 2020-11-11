package ru.javawebinar.topjava.web.meal;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/profile/meals")
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void create(@RequestParam Integer id,
                       @RequestParam String dateTime,
                       @RequestParam String description,
                       @RequestParam String calories) {

        Meal meal = new Meal(id, LocalDateTime.parse(dateTime), description, Integer.parseInt(calories));
        if (meal.isNew()) {
            super.create(meal);
        }
    }

    @GetMapping("/filter")
    public List<MealTo> getBetween(
            @RequestParam String startDate,
            @RequestParam String startTime,
            @RequestParam String endDate,
            @RequestParam String endTime) {
        LocalDate start_Date;
        LocalDate end_Date;
        LocalTime start_Time;
        LocalTime end_Time;
        try {
            start_Date = LocalDate.parse(startDate);
        } catch (DateTimeParseException e) {
            start_Date = null;
        }
        try {
            end_Date = LocalDate.parse(endDate);
        } catch (DateTimeParseException e) {
            end_Date = null;
        }
        try {
            start_Time = LocalTime.parse(startTime);
        } catch (DateTimeParseException e) {
            start_Time = null;
        }
        try {
            end_Time = LocalTime.parse(endTime);
        } catch (DateTimeParseException e) {
            end_Time = null;
        }
        return super.getBetween(start_Date, start_Time, end_Date, end_Time);
    }
}
