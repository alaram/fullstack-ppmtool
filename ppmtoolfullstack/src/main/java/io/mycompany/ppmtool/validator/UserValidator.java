package io.mycompany.ppmtool.validator;

import io.mycompany.ppmtool.domain.User;

import org.springframework.stereotype.Component;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    /**
     * This method says which class is going to
     * be supported for validation
     *
     * @param clazz
     * @return
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    /**
     *
     * @param target
     * @param errors
     */
    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;

        if (user.getPassword().length() < 6) {
            errors.rejectValue("password",
                               "Length",
                               "Password must be at least 6 characters");
        }

        if (!user.getPassword().equals(user.getConfirmPassword())) {
            errors.rejectValue("confirmPassword",
                               "Match",
                               "Passwords must match");
        }
    }
}