package com.project.flightmanagementsystem.entity;
 
 
import java.util.Set;
import jakarta.persistence.CollectionTable;
 
import jakarta.persistence.Column;
 
import jakarta.persistence.ElementCollection;
 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
 
import jakarta.persistence.GenerationType;
 
import jakarta.persistence.Id;
 
import jakarta.persistence.JoinColumn;
 
import jakarta.persistence.Table;
 
 
@Entity
@Table(name="UserBooking")
public class User {
 
	public User(Long userId, String username, String password, Long userPhone, String email, Set<String> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userPhone = userPhone;
		this.email = email;
		this.roles = roles;
	}
 
	public Long getUserId() {
		return userId;
	}
 
	public void setUserId(Long userId) {
		this.userId = userId;
	}
 
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
 
	public Long getUserPhone() {
		return userPhone;
	}
 
	public void setUserPhone(Long userPhone) {
		this.userPhone = userPhone;
	}
 
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}
 
	public Set<String> getRoles() {
		return roles;
	}
 
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "user_name",unique = true,nullable = false,length = 50)
	private String username;
 
	@Column(name = "user_password",nullable = false,unique = true)
	private String password;
 
	@Column(name = "user_phone",nullable = false)
	private Long userPhone;
 
	@Column(name ="user_email", nullable = false)
	private String email;
 
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="roles_tab",joinColumns = @JoinColumn(name="user_id"))
	@Column(name="role")
	private Set<String> roles;
 
	public User() {
		super();
	}
}
 