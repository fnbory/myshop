package com.februy.shop.commonweb.security.endpoint;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.domain.RestError;
import com.februy.shop.common.utils.JsonUtil;
import com.februy.shop.commonweb.properties.AuthenticationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 1:22
 */
@Slf4j
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final String UNAUTHORIZED = JsonUtil.json(new RestError(HttpStatus.UNAUTHORIZED, 1, null, ""));

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        log.info("到达JWTAuthenticationEntryPoint");
        if(request.getAttribute(AuthenticationProperties.EXCEPTION_ATTR_NAME)!=null){
            BaseRestException exception=(BaseRestException) request.getAttribute(AuthenticationProperties.EXCEPTION_ATTR_NAME);
            response.setStatus(exception.getStatus().value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().append(JsonUtil.json(new RestError(exception.getStatus(),exception.getCode(),exception.getErrors(),exception.getMoreInfoURL())));
        }
        else{
            response.setStatus(401);
            response.getWriter().append(UNAUTHORIZED);
        }
    }
}
