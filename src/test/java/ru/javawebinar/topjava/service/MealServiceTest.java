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
        Meal meal = service.get(100004, 100000);
        assertMatch(meal, userMeal03);
    }

    @Test
    public void getSomeoneElseFood() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(100009, 100000));
    }

    @Test
    public void delete() {
        service.delete(100004, 100000);
        assertNull(repository.get(100004, 100000));
    }

    @Test
    public void deleteSomeoneElseFood() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(100009, 100000));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> mealList = service.getBetweenInclusive(LocalDate.of(2020, 6, 27), LocalDate.of(2020, 6, 27), 100000);
        List<Meal> test = new ArrayList<>(userMealListOf27);
        test.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealList, test);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(100000);
        List<Meal> test = new ArrayList<>(userMealList);
        test.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(all, test);
    }

    @Test
    public void update() {
        Meal updated = new Meal(100004, LocalDateTime.parse("2020-06-27T20:00:00"), "ужин невкусный и грустный", 200);
        service.update(updated, 100000);
        assertMatch(service.get(100004, 100000), updated);
    }

    @Test
    public void updateSomeoneElseFood() throws Exception {
        Meal updated = new Meal(100004, LocalDateTime.parse("2020-06-27T20:00:00"), "ужин невкусный и грустный", 200);
        assertThrows(NotFoundException.class, () -> service.update(updated, 100001));
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2020, 7, 1, 12, 10), "еда", 600);
        Meal created = service.create(newMeal, 100000);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, 100000), newMeal);
    }

    @Test
    public void duplicateMealDate() throws Exception {
        assertThrows(DuplicateKeyException.class, () ->
                service.create(new Meal(LocalDateTime.parse("2020-06-27T13:00:00"), "дубль", 1000), 100000));
    }
}