package com.februy.shop.common.base.exception.annotation;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.meta.RestFieldAnnotationNotFoundException;
import com.februy.shop.common.base.exception.meta.RestResponseStatusAnnotationNotFoundException;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 5:12
 */
public class RestExceptionAnnotationUtil {

    public static void setAttr(BaseRestException e){
        RestResponseStatus status=e.getClass().getAnnotation(RestResponseStatus.class);
        if(status==null) throw new RestResponseStatusAnnotationNotFoundException();
        e.setStatus(status.value());
        e.setCode(status.code());
        e.setMoreInfoURL(status.url());
    }

    public static String getMsgKey(BaseRestException e) {
        RestResponseStatus status = e.getClass().getAnnotation(RestResponseStatus.class);
        if (status == null) {
            throw new RestResponseStatusAnnotationNotFoundException();
        }
        if (status.msgKey().equals("")) {
            String simpleName = e.getClass().getSimpleName();
            return simpleName.substring(0, simpleName.lastIndexOf("Exception"));
        } else {
            return status.msgKey();
        }
    }

    public static String getFieldName(BaseRestException e){
        RestField field=e.getClass().getAnnotation(RestField.class);
        if(field==null) throw new RestFieldAnnotationNotFoundException();
        return field.value();
    }


}
