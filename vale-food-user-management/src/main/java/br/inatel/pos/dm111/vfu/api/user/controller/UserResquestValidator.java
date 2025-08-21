package br.inatel.pos.dm111.vfu.api.user.controller;


import br.inatel.pos.dm111.vfu.api.user.UserRequest;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResquestValidator implements Validator {

    private  static final List<String> VALID_USER_TYPE = List.of("REGULAR", "RESTAURANT");
    @Override
    public boolean supports(Class<?> clazz) {
        return UserRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.empty", "Name is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Pass is required!");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.empty", "Email is required!");

        var req = (UserRequest) target;
        if (!VALID_USER_TYPE.contains(req.type())){
            errors.rejectValue("type", "User type invalid", "Invalid user type! Possible values: REGULAR, RESTAURANT");
        }
    }
}
