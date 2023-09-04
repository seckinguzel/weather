package com.meteorologicalapps.weather.controller.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class CityNameValidator implements ConstraintValidator<CityNameConstraint, String> {
    //CityNameConstraint will return String for the message
    @Override
    public void initialize(CityNameConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        //Below regex removes special characters from request for instance Ankara-Istanbul-Izmır makes AnkaraIstanbulIzmır
        //isNumeric controls numbers
        //isAllBlank controls blanks for instance below regex "- - -" converts "     " and that command controls this.
        s = s.replaceAll("[^a-zA-Z0-9]+", ""); //Zero number next to big letter Z

        return !StringUtils.isNumeric(s) && !StringUtils.isAllBlank(s);
    }
}
