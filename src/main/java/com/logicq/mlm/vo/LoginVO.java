package com.logicq.mlm.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicq.mlm.model.profile.UserProfile;


public class LoginVO implements UserDetails {
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1773038650761872642L;
	private final String username;
    private  String firstname;
    private  String lastname;
    private final String password;
    private final String email;
    private final String mobilenumber;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean enabled;
    private final UserProfile userprofile;


    public LoginVO(
          String username,
          String fname,
          String lname,
          String email,
          String mobilenumber,
          String password, Collection<? extends GrantedAuthority> authorities,
          boolean enabled,
          UserProfile userprofile
    ) {
        this.username = username;
        this.firstname=fname;
        this.lastname=lname;
        this.email = email;
        this.mobilenumber=mobilenumber;
        this.password = password;
        this.authorities = authorities;
        this.enabled = enabled;
        this.userprofile=userprofile;
   
    }


	public UserProfile getUserprofile() {
		return userprofile;
	}


	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}
	public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

  
    public boolean isEnabled() {
        return enabled;
    }




	@Override
    @JsonIgnore
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
    @JsonIgnore
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}



	@Override
    @JsonIgnore
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
}
