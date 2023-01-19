package br.dev.yann.rssreader.auth.user;

import java.io.Serial;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.deser.std.UUIDDeserializer;
import com.fasterxml.jackson.databind.ser.std.UUIDSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * User is the representation of users data to persist in database.
 * 
 * @author Yann Carvalho
 */
@Entity(name = "Users")
@Table(name = "users")
public class User implements UserDetails{
	
  @Serial
  private static final long serialVersionUID = 1L;
  
  /**
   *  Password encoders.
   */	
  @Transient @JsonIgnore 
  private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  /**
   * Unique user identification.
   * 
   * @see UUID
   */	
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonSerialize(using = UUIDSerializer.class)
  @JsonDeserialize(using = UUIDDeserializer.class)
  private UUID id;

  /**
   * User login name.
   */	
  @Column(name = "username", unique = true, nullable = false)
  @NotBlank(message = "username must be informed.")
  @Size(min = 3, max = 40, message = "username must be between {0} and {1} characters") 
  private String username;

  /**
   * Encrypted user password.
   */
  @Column(nullable = false)
  @NotBlank(message = "password must be informed.")
  @Size(min = 3, max = 255, message = "password must be between {0} and {1} characters") 
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  /**
   * User name.
   */
  @Column(nullable = false)
  @NotBlank(message = "name must be informed.")
  @Size(min = 3, max = 255, message = "name must be between {0} and {1} characters") 
  private String name;

  /**
   * 
   * User role. 
   * <br>
   * By default role is {@link  RoleUser#USER User}.
   * @see UserRole
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 255)
  @NotNull(message = "role must be informed.")
  private UserRole role = UserRole.USER;

  /**
   * Empty constructor.
   */
  public User() {}	

  /**
   * Constructs a {@code User} with name, password and username as parameters.
   * @param name value to {@link #name}.
   * @param password value to {@link #password}.
   * @param username value {@link #username}.
   */
  public User(String name, String password, String username) {
    this.name = name;
    this.setPassword(password);
    this.username = username;
  }
  
  /**
   * @return {@link #id}.
   */
  public UUID getId() {
	return id;
  }
  
  /**
   * @param password updates {@link #password}.
   */
  public void setPassword(String password) {
	this.password = passwordEncoder.encode(password);
  }

 /**
   * @param username updates {@link #username}.
   */
  public void setUsername(String username) {
	this.username = username;
  }

  /**
   * @return {@link #name}.
   */
  public String getName() {
	return name;
  }
  
  /**
   * @param name updates {@link #name}.
   */
  public void setName(String name) {
	this.name = name;
  }

  /**
   * @return {@link #role}.
   */
  public UserRole getRole() {
	return role;
  }
  
  /**
   * Authenticate password.
   * @param password the raw password to authenticate.
   * @return {@code true} if authenticated, {@code false} if not.
   * 
   * @see #password
   */
  public boolean authenticatePassword(String password) {
	  return passwordEncoder.matches(this.password, password);
  }
  
  /**
   * @param role updates {@link #role}.
   */
  public void setRole(UserRole role) {
	this.role = role;
  }
  
  /**
   * @return {@link #password}.
   */
  @Override
  public String getPassword() {
	 return password;
  }
    
  /**
   * @return {@link #username}.
   */
  @Override
  public String getUsername() {
  	return username;
  }
   

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Set.of(new SimpleGrantedAuthority("ROLE_"+role.toString()));
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
	return false;
  }
  
  @Override
  public boolean isEnabled() {
	return true;
  }

  @Override
  public int hashCode() {
    if(id == null)
    	return super.hashCode();
	return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	User other = (User) obj;
	return Objects.equals(id, other.id);
  }

  @Override
  public String toString() {
	return "User ["
			+ "id=" + id 
			+ ", username=" + username 
			+ ", password=" + password 
			+ ", name=" + name 
			+ ", role="	+ role 
			+ "]";
  }

}
