package com.februy.shop.common.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 8:06
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Constraint(validatedBy = PastValidator.class)
public @interface Past {

    String message() default "必须是一个过去的时间";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
