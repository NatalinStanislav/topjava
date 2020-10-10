package ru.javawebinar.topjava.service.jdbc;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.Date;
import java.util.Set;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void validation() throws Exception {
        Assert.assertNull(service.create(new User(null, "  ", "mail@yandex.ru", "password", Role.USER)));
        Assert.assertNull(service.create(new User(null, "User", "  ", "password", Role.USER)));
        Assert.assertNull(service.create(new User(null, "User", "mail@yandex.ru", "  ", Role.USER)));
        Assert.assertNull(service.create(new User(null, "User", "mail@yandex.ru", "password", 9, true, new Date(), Set.of())));
        Assert.assertNull(service.create(new User(null, "User", "mail@yandex.ru", "password", 10001, true, new Date(), Set.of())));
    }
}