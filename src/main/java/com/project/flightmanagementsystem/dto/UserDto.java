package com.project.flightmanagementsystem.dto;
 
import java.util.Set;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.*;
 
public class UserDto {
 
    @Positive(message = "{validation.user.id.positive}")
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long userId;
    @NotBlank(message = "{validation.user.name.required}")
    @Size(min = 3, max = 15, message = "{validation.user.name.size}")
    @Pattern(regexp = "^[A-Za-z0-9]{3,15}$", message = "{validation.user.name.alphanumeric}")
    private String username;
 

    @NotBlank(message = "{validation.user.password.required}")
    @Size(min = 6, max = 10, message = "{validation.user.password.size}")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$", 
        message = "{validation.user.password.complexity}"
    )
    private String password;
 
 
    @NotNull(message = "{validation.user.phone.required}")
    @Digits(integer = 10, fraction = 0, message = "{validation.user.phone.format}")
    private Long userPhone;
 
 
    @NotBlank(message = "{validation.user.email.required}")
    @Email(message = "{validation.user.email.format}")
    private String email;
 
   
    @NotEmpty(message = "{validation.user.roles.required}")
    @Size(min = 1, max = 2, message = "{validation.user.roles.size}")
    private Set<@Pattern(
        regexp = "^(?i)(ADMIN|PASSENGER)$", 
        message = "{validation.user.roles.invalid}") 
    String> roles;
 
 
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
 
 
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 
    public Long getUserPhone() { return userPhone; }
    public void setUserPhone(Long userPhone) { this.userPhone = userPhone; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
	public UserDto(@Positive(message = "{validation.user.id.positive}") Long userId,
			@NotBlank(message = "{validation.user.name.required}") @Size(min = 3, max = 15, message = "{validation.user.name.size}") @Pattern(regexp = "^[A-Za-z0-9]{3,15}$", message = "{validation.user.name.alphanumeric}") String username,
			@NotBlank(message = "{validation.user.password.required}") @Size(min = 6, max = 10, message = "{validation.user.password.size}") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,10}$", message = "{validation.user.password.complexity}") String password,
			@NotNull(message = "{validation.user.phone.required}") @Digits(integer = 10, fraction = 0, message = "{validation.user.phone.format}") Long userPhone,
			@NotBlank(message = "{validation.user.email.required}") @Email(message = "{validation.user.email.format}") String email,
			@NotEmpty(message = "{validation.user.roles.required}") @Size(min = 1, max = 2, message = "{validation.user.roles.size}") Set<@Pattern(regexp = "^(?i)(ADMIN|PASSENGER)$", message = "{validation.user.roles.invalid}") String> roles) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.userPhone = userPhone;
		this.email = email;
		this.roles = roles;
	}
 
   
}
 
 