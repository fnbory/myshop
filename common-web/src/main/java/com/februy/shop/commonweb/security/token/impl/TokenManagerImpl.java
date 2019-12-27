package com.februy.shop.commonweb.security.token.impl;

import com.februy.shop.common.cache.RedisCacheManager;
import com.februy.shop.commonweb.exception.security.TokenStateInvalidException;
import com.februy.shop.commonweb.properties.AuthenticationProperties;
import com.februy.shop.commonweb.security.domin.TokenCheckResult;
import com.februy.shop.commonweb.security.token.TokenManager;
import com.februy.shop.commonweb.security.token.TokenState;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 3:02
 */
@Component
@Slf4j
public class TokenManagerImpl implements TokenManager {

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Autowired
    private RedisCacheManager redisCacheManager;

    @Override
    public String createToken(String username) {

        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(authenticationProperties.getSecretKey());
        Key signingKey=new SecretKeySpec(apiKeySecretBytes,signatureAlgorithm.getJcaName());
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder= Jwts.builder().setHeaderParam("typ","JWT")
                .setId(username)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm, signingKey);

        long expireTime = System.currentTimeMillis() + authenticationProperties.getTokenExpireTime() * 1000;
        Date expireDateTime = new Date(expireTime);
        builder.setExpiration(expireDateTime);

        String token=builder.compact();
        redisCacheManager.putWithExpireTime(String.valueOf(username.hashCode()),token,authenticationProperties.getTokenExpireTime());
        return token;
    }

    @Override
    public TokenCheckResult checkToken(String token) {

        if(token==null) return new TokenCheckResult.TokenCheckResultBuilder().inValid().exception(new TokenStateInvalidException(TokenState.NOT_FOUND.toString())).build();
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(authenticationProperties.getSecretKey()))
                    .parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            log.info("Token过期 {}",token);
            return new TokenCheckResult.TokenCheckResultBuilder().inValid().exception(new TokenStateInvalidException(TokenState.EXPIRED.toString())).build();
        } catch (Exception e) {
            return new TokenCheckResult.TokenCheckResultBuilder().inValid().exception(new TokenStateInvalidException(TokenState.INVALID.toString())).build();
        }
        String username=claims.getId();
        String cachedToken=redisCacheManager.get(String.valueOf(username.hashCode()),String.class);
        if (cachedToken == null || !cachedToken.equals(token)) {
            return new TokenCheckResult.TokenCheckResultBuilder().inValid().exception(new TokenStateInvalidException(TokenState.INVALID.toString())).build();
        }
        return new TokenCheckResult.TokenCheckResultBuilder().valid().username(username).build();
    }

    @Override
    public void deleteToken(String username) {
        redisCacheManager.delete(String.valueOf(username.hashCode()));
    }
}
