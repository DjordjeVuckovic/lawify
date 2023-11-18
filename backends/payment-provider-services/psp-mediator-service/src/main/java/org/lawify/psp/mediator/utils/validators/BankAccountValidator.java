package org.lawify.psp.mediator.utils.validators;

import org.springframework.stereotype.Component;

import java.util.function.Predicate;
@Component
public class BankAccountValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return s.matches("\\d{8,16}");
    }
}
