package com.februy.shop.commonweb.security.domin;

import com.februy.shop.common.base.exception.BaseRestException;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 3:14
 */
public class TokenCheckResult {
    private boolean isValid;
    private String username;
    private BaseRestException exception;

    public TokenCheckResult(boolean isValid, String username, BaseRestException exception) {
        this.isValid = isValid;
        this.username = username;
        this.exception = exception;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BaseRestException getException() {
        return exception;
    }

    public void setException(BaseRestException exception) {
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "TokenCheckResult{" +
                "isValid=" + isValid +
                ", username='" + username + '\'' +
                ", exception=" + exception +
                '}';
    }

    public static class TokenCheckResultBuilder {
        private boolean isValid;
        private String username;
        private BaseRestException exception;

        public TokenCheckResultBuilder() {
        }

        public TokenCheckResultBuilder valid() {
            this.isValid = true;
            return this;
        }

        public TokenCheckResultBuilder inValid() {
            this.isValid = false;
            return this;
        }

        public TokenCheckResultBuilder username(String username) {
            this.username = username;
            return this;
        }

        public TokenCheckResultBuilder exception(BaseRestException exception) {
            this.exception = exception;
            return this;
        }

        public TokenCheckResult build() {
            return new TokenCheckResult(isValid, username, exception);
        }

    }
}
