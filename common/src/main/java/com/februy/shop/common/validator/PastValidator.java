package com.februy.shop.common.validator;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 7:59
 */
// java localdate 是用来描述时间的，past不能用于localedate
// https://stackoverflow.com/questions/30249829/error-no-validator-could-be-found-for-type-java-time-localdate
public class PastValidator implements ConstraintValidator<Past,LocalDate> {

    @Override
    public void initialize(Past annotation) {

    }

    @Override
    public boolean isValid(LocalDate o, ConstraintValidatorContext constraintValidatorContext) {
        if(o==null) return true;
        if(o.isBefore(LocalDate.now())) return true;
        return false;
    }
}
