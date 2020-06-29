package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


public class MealTestData {
    public static int USER_ID = 100000;
    public static int ADMIN_ID = 100001;
    public static int USER_MEAL_03_ID = 100004;
    public static int ADMIN_MEAL_01_ID = 100009;

    public static final Meal USER_MEAL_01 = new Meal(100002, LocalDateTime.parse("2020-06-27T10:00:00"), "завтрак", 500);
    public static final Meal USER_MEAL_02 = new Meal(100003, LocalDateTime.parse("2020-06-27T13:00:00"), "обед", 1000);
    public static final Meal USER_MEAL_03 = new Meal(100004, LocalDateTime.parse("2020-06-27T20:00:00"), "ужин", 500);
    public static final Meal USER_MEAL_04 = new Meal(100005, LocalDateTime.parse("2020-06-28T00:00:00"), "граница", 100);
    public static final Meal USER_MEAL_05 = new Meal(100006, LocalDateTime.parse("2020-06-28T10:00:00"), "завтрак", 500);
    public static final Meal USER_MEAL_06 = new Meal(100007, LocalDateTime.parse("2020-06-28T13:00:00"), "обед", 1000);
    public static final Meal USER_MEAL_07 = new Meal(100008, LocalDateTime.parse("2020-06-28T20:00:00"), "ужин", 500);
    public static final Meal ADMIN_MEAL_01 = new Meal(100009, LocalDateTime.parse("2020-06-27T12:00:00"), "завтрак админа ролтон с курицей", 1000);
    public static final Meal ADMIN_MEAL_02 = new Meal(100010, LocalDateTime.parse("2020-06-27T23:00:00"), "ужин админа ролтон с говядиной", 1000);

    public static final List<Meal> USER_MEAL_LIST = Arrays.asList(USER_MEAL_01, USER_MEAL_02, USER_MEAL_03, USER_MEAL_04, USER_MEAL_05, USER_MEAL_06, USER_MEAL_07);
    public static final List<Meal> ADMIN_MEAL_LIST = Arrays.asList(ADMIN_MEAL_01, ADMIN_MEAL_02);
    public static final List<Meal> USER_MEAL_LIST_OF_27 = Arrays.asList(USER_MEAL_01, USER_MEAL_02, USER_MEAL_03);

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual.toString()).isEqualTo(expected.toString());
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual.toString()).isEqualTo(expected.toString());
    }


}
