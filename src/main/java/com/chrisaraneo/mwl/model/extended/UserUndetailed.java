package com.chrisaraneo.mwl.model.extended;

import java.util.Set;

import com.chrisaraneo.mwl.model.Role;
import com.chrisaraneo.mwl.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserUndetailed extends User {

	public UserUndetailed(User user) {
		super();
		this.setID(user.getID());
		this.setEmail(user.getEmail());
		this.setName(user.getName());
		this.setPassword(user.getPassword());
		this.setRoles(user.getRoles());
		this.setUsername(user.getUsername());
	}
	
	@Override
	@JsonIgnore
	public String getUsername() {
		return super.getUsername();
	}
	
	@Override
	@JsonIgnore
	public String getEmail() {
		return super.getEmail();
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	@JsonIgnore
	public Set<Role> getRoles() {
		return super.getRoles();
	}
	
}
