package com.februy.shop.commonweb.security.filter;

import com.februy.shop.commonweb.properties.AuthenticationProperties;
import com.februy.shop.commonweb.security.domin.TokenCheckResult;
import com.februy.shop.commonweb.security.token.TokenManager;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 上午 11:51
 */
@Component
@Slf4j
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenManager tokenManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("经过JWTAuthenticationTokenFilter");
        String token=request.getHeader(AuthenticationProperties.AUTH_HEADER);
        TokenCheckResult result=tokenManager.checkToken(token);
        if(!result.isValid()){
            log.info("token 无效");
            request.setAttribute(AuthenticationProperties.EXCEPTION_ATTR_NAME,result.getException());
        }
        else{
            log.info("checking authentication {}", result);
            UserDetails userDetails=userDetailsService.loadUserByUsername(result.getUsername());
            if(SecurityContextHolder.getContext().getAuthentication()==null){
                UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(
                        userDetails,null,userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user {} ,setting security context", result);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request,response);
    }
}
