package com.shumisoft.employee_management_system.config;

import java.util.Collections;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;

import lombok.Data;

@Data
public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String principal;
    private String token;

    // unauthenticated
    public JwtAuthenticationToken(String token) {
        super(Collections.emptyList());
        this.setToken(token);
    }

    // authenticated
    public JwtAuthenticationToken(String principal, String role) {
        super(AuthorityUtils.createAuthorityList(role));
        this.principal = principal;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
