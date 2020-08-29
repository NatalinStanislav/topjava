package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"postgres", "jpa-datajpa", "jpa"})
public class UserServiceJPATest extends UserServiceTest {
}
