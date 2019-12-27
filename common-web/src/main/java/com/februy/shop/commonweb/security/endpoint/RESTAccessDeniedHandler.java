package com.februy.shop.commonweb.security.endpoint;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.domain.RestError;
import com.februy.shop.common.utils.JsonUtil;
import com.februy.shop.commonweb.exception.security.AccessDeniedException;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 4:32
 */
@Component
public class RESTAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, org.springframework.security.access.AccessDeniedException e) throws IOException, ServletException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        BaseRestException exception = new AccessDeniedException(authentication.getAuthorities().toString());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(exception.getStatus().value());
        response.getWriter().append(JsonUtil.json(new RestError(exception.getStatus(),exception.getCode(),exception.getErrors(),exception.getMoreInfoURL())));
    }
}
