package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {
    public static final Meal userMeal01 = new Meal(100002, LocalDateTime.parse("2020-06-27T10:00:00"), "завтрак", 500);
    public static final Meal userMeal02 = new Meal(100003, LocalDateTime.parse("2020-06-27T13:00:00"), "обед", 1000);
    public static final Meal userMeal03 = new Meal(100004, LocalDateTime.parse("2020-06-27T20:00:00"), "ужин", 500);
    public static final Meal userMeal04 = new Meal(100005, LocalDateTime.parse("2020-06-28T00:00:00"), "граница", 100);
    public static final Meal userMeal05 = new Meal(100006, LocalDateTime.parse("2020-06-28T10:00:00"), "завтрак", 500);
    public static final Meal userMeal06 = new Meal(100007, LocalDateTime.parse("2020-06-28T13:00:00"), "обед", 1000);
    public static final Meal userMeal07 = new Meal(100008, LocalDateTime.parse("2020-06-28T20:00:00"), "ужин", 500);
    public static final Meal adminMeal01 = new Meal(100009, LocalDateTime.parse("2020-06-27T12:00:00"), "завтрак админа ролтон с курицей", 1000);
    public static final Meal adminMeal02 = new Meal(100010, LocalDateTime.parse("2020-06-27T23:00:00"), "ужин админа ролтон с говядиной", 1000);

    public static final List<Meal> userMealList = Arrays.asList(userMeal01, userMeal02, userMeal03, userMeal04, userMeal05, userMeal06, userMeal07);
    public static final List<Meal> adminMealList = Arrays.asList(adminMeal01, adminMeal02);
    public static final List<Meal> userMealListOf27 = Arrays.asList(userMeal01, userMeal02, userMeal03);

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }


}
