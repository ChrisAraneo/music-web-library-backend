package com.chrisaraneo.mwl.payload;

import java.util.Set;

import javax.validation.Valid;

import com.chrisaraneo.mwl.model.Role;
import com.chrisaraneo.mwl.model.User;
import com.chrisaraneo.mwl.model.extended.UserUndetailed;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Set<Role> userRoles;
    private User user;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
    
    public JwtAuthenticationResponse(String accessToken, Set<Role> roles, User user) {
        this.accessToken = accessToken;
        this.userRoles = roles;
        this.user = user;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
    
    public Set<Role> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<Role> roles) {
        this.userRoles = roles;
    }
    
    public User getUser() {
    	User uu = new UserUndetailed(user);
    	return uu;
    }
    
    public void setUser(User user) {
    	this.user = user;
    }
}