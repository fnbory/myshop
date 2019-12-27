package com.februy.shop.common.base.exception;

import com.februy.shop.common.base.exception.annotation.RestExceptionAnnotationUtil;
import com.februy.shop.common.base.exception.domain.RestFieldError;
import com.februy.shop.common.utils.InternationalizeUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 4:44
 */
@NoArgsConstructor
@Setter
@Getter
public class BaseRestException extends  RuntimeException {

    private HttpStatus status;
    private int code;
    private List<RestFieldError> errors;
    private String moreInfoURL = "";

    public BaseRestException(Object rejectedValue) {
        RestExceptionAnnotationUtil.setAttr(this);
        this.errors = Arrays.asList(new RestFieldError(RestExceptionAnnotationUtil.getFieldName(this), rejectedValue, InternationalizeUtil
                .getMessage("i18n." + RestExceptionAnnotationUtil.getMsgKey(this), LocaleContextHolder.getLocale())));
    }


    public BaseRestException(List<FieldError> errors){

        RestExceptionAnnotationUtil.setAttr(this);
        this.errors=toRestFieldErrorList(errors);
    }

    private List<RestFieldError> toRestFieldErrorList(List<FieldError> errors) {
        List<RestFieldError> errorsList=new ArrayList<>();
        errors.stream().forEach(
                error->{
                    errorsList.add(new RestFieldError(error));
                }
        );
        return errorsList;
    }
}
