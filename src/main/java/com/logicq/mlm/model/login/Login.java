package com.logicq.mlm.model.login;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.logicq.mlm.model.profile.UserProfile;

@Entity
@Table(name = "LOGIN")
public class Login implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1528639937000355912L;

	@Id
	@Column(name = "USERNAME", length = 200, unique = true)
	@NotNull
	@Size(max = 200)
	private String username;

	@Column(name = "PASSWORD", length = 200)
	@NotNull
	@Size(min = 4, max = 200)
	private String password;

	@Column(name = "MOBILE_NUMBER", length = 11, unique = true)
	@NotNull
	@Size(min = 10)
	private String mobilenumber;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ENABLED")
	@NotNull
	private Boolean enabled;

	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "PROFILE_ID")
	private UserProfile userprofile;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "USER_AUTHORITY", joinColumns = { @JoinColumn(name = "USERNAME") }, inverseJoinColumns = {
			@JoinColumn(name = "AUTHORITY_ID", referencedColumnName = "ID") })
	private List<Authority> authorities;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public UserProfile getUserprofile() {
		return userprofile;
	}

	public void setUserprofile(UserProfile userprofile) {
		this.userprofile = userprofile;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String toString() {
		return "Login [username=" + username + ", password=" + password + ", mobilenumber=" + mobilenumber + ", email="
				+ email + ", enabled=" + enabled + ", authorities=" + authorities + "]";
	}

}
