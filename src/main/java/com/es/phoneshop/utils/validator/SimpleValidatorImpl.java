package com.es.phoneshop.utils.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimpleValidatorImpl implements SimpleValidator {
    private Object checkedValue;
    private List<String> errorsList;
    private static SimpleValidator simpleValidator;

    private boolean hasErrors() {
        return errorsList.size() != 0;
    }

    public static SimpleValidator getInstance() {
        if (simpleValidator == null) {
            simpleValidator = new SimpleValidatorImpl();
        }
        return simpleValidator;
    }

    @Override
    public SimpleValidator isDateByFormat(String format) {
        try {
            LocalDate.parse(checkedValue.toString(), DateTimeFormatter.ofPattern(format));
        } catch (DateTimeParseException e) {
            this.errorsList.add("Input date in format " + format);
        }
        return this;
    }

    @Override
    public SimpleValidator setCheckedValue(Object checkedValue) {
        this.checkedValue = checkedValue;
        return this;
    }

    @Override
    public List<String> getErrorMessages() {
        return hasErrors() ? errorsList : null;
    }

    @Override
    public SimpleValidator notEmpty() {
        if (this.checkedValue == null || this.checkedValue.equals("")) {
            this.errorsList.add("This field must be filled out");
        }
        return this;
    }

    @Override
    public String getFirstErrorIfExist() {
        return getErrorMessages().get(0);
    }


    @Override
    public SimpleValidator newErrorList() {
        this.errorsList = new ArrayList<>();
        return this;
    }

    @Override
    public SimpleValidator addToMapErrorsIfExist(Map<String, List<String>> mapErrors, String keyForMapErrors) {
        if (hasErrors()) {
            mapErrors.put(keyForMapErrors, errorsList);
        }
        return this;
    }

    @Override
    public SimpleValidator isPhoneNumber() {
        if (!checkedValue.toString().matches("(\\+*)375\\d{9}")) {
            this.errorsList.add("Phone number must be in form +375XXXXXXXXX");
        }
        return this;
    }

    @Override
    public SimpleValidator lengthMoreThen(Integer value) {
        if (checkedValue.toString().length() < value) {
            this.errorsList.add("Input more then " + value + " symbols!");
        }
        return this;
    }

    @Override
    public SimpleValidator lengthLessThen(Integer value) {
        if (checkedValue.toString().length() > value) {
            this.errorsList.add("Input less then " + value + " symbols!");
        }
        return this;
    }
}
