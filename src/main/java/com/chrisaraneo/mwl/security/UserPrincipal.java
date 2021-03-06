package com.chrisaraneo.mwl.security;

import com.chrisaraneo.mwl.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 6805040122152875785L;

	private Long id;

    private String name;
    
    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())
        ).collect(Collectors.toList());

        return new UserPrincipal(
                user.getID(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    public Long getID() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
	public String getUsername() {
		return username;
	}

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    } 

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
    
	public boolean hasRole(String roleName) {
    	SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
        	 return false;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
        	 return false;
        }

        for (GrantedAuthority auth : this.getAuthorities()) {
            if (roleName.equals(auth.getAuthority())) {
            	return true;
            }
        }
        
        return false;
    }
}