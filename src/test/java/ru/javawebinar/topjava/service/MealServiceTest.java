package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;


    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_03_ID, USER_ID);
        assertMatch(meal, USER_MEAL_03);
    }

    @Test
    public void getSomeoneElseFood() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(ADMIN_MEAL_01_ID, USER_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_03_ID, USER_ID);
        assertNull(repository.get(USER_MEAL_03_ID, USER_ID));
    }

    @Test
    public void deleteSomeoneElseFood() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(ADMIN_MEAL_01_ID, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealList = service.getBetweenInclusive(LocalDate.of(2020, 6, 27), LocalDate.of(2020, 6, 27), USER_ID);
        List<Meal> test = new ArrayList<>(USER_MEAL_LIST_OF_27);
        test.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealList, test);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        List<Meal> test = new ArrayList<>(USER_MEAL_LIST);
        test.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(all, test);
    }

    @Test
    public void update() {
        Meal updated = new Meal(USER_MEAL_03_ID, LocalDateTime.parse("2020-06-27T20:00:00"), "ужин невкусный и грустный", 200);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_03_ID, USER_ID), updated);
    }

    @Test
    public void updateSomeoneElseFood() throws Exception {
        Meal updated = new Meal(USER_MEAL_03_ID, LocalDateTime.parse("2020-06-27T20:00:00"), "ужин невкусный и грустный", 200);
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2020, 7, 1, 12, 10), "еда", 600);
        Meal created = service.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateMealDate() throws Exception {
        assertThrows(DuplicateKeyException.class, () ->
                service.create(new Meal(LocalDateTime.parse("2020-06-27T13:00:00"), "дубль", 1000), USER_ID));
    }
}