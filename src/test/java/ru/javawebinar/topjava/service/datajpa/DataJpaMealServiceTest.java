package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles(profiles = Profiles.REPOSITORY_IMPLEMENTATION)
public class DataJpaMealServiceTest extends MealServiceTest {
    static {
        results = new StringBuilder();
    }
}
