package com.es.phoneshop.utils.validator;

import java.util.List;
import java.util.Map;

public interface SimpleValidator {
    SimpleValidator isDateByFormat(String format);
    SimpleValidator setCheckedValue(Object checkedValue);
    List getErrorMessages();
    SimpleValidator notEmpty();
    String getFirstErrorIfExist();
    SimpleValidator newErrorList();
    SimpleValidator addToMapErrorsIfExist(Map<String, List<String>> mapErrors, String keyForMapErrors);
    SimpleValidator isPhoneNumber();
    SimpleValidator lengthMoreThen(Integer value);
    SimpleValidator lengthLessThen(Integer value);
}
