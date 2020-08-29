package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.ActiveProfileResolver;

@ActiveProfiles(resolver = ActiveProfileResolver.class)
public class MealServiceDataJPATest extends MealServiceTest {
}
