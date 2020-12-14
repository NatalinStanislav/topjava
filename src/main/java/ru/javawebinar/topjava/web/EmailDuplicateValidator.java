package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.UserTo;

@Component
public class EmailDuplicateValidator implements Validator {

    @Autowired
    UserRepository userRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("РАБОТАЕТ ШТОЛЕЕЕЕ?????????");
        User user = userRepository.getByEmail(((UserTo) target).getEmail());
        if (SecurityUtil.safeGet() != null && user.getId() == SecurityUtil.safeGet().getId()) {
            return;
        }
        if (user != null) {
            errors.rejectValue("email", "user.emailDuplicateError");
        }
    }
}