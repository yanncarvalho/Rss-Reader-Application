package br.dev.yann.rssreader.auth.user;

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

import br.dev.yann.rssreader.auth.role.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * User database entity
 */
@Entity(name = "Users")
@Table(name = "users")
public class User implements UserDetails{
	
  public void setId(UUID id) {
		this.id = id;
	}

/**
   * Serial Version UID
   */	
  private static final long serialVersionUID = 1L;
   
  @Transient @JsonIgnore 
  private static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

 /**
   * User id
   */	
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @JsonSerialize(using = UUIDSerializer.class)
  @JsonDeserialize(using = UUIDDeserializer.class)
  private UUID id;

  /**
   * User username
   */	
  @Column(name = "username", unique = true, nullable = false)
  @NotBlank(message = "username must be informed.")
  @Size(min = 3, max = 255, message = "username must be between {1} and {2} characters") 
  private String username;

  /**
   * Encrypted user password
   */
  @Column(nullable = false)
  @NotBlank(message = "password must be informed.")
  @Size(min = 3, max = 255, message = "password must be between {1} and {2} characters") 
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;

  /**
   * User name
   */
  @Column(nullable = false)
  @NotBlank(message = "name must be informed.")
  @Size(min = 3, max = 255, message = "name must be between {1} and {2} characters") 
  private String name;

  /**
   * User role
   */

  @JsonIgnore 
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
  private Role role;

  /**
   * Empty constructor
   */
  public User() {}	

  /**
   * Constructor
   * @param name value to {@link #name}
   * @param password value to {@link #password}
   * @param username value {@link #username}
   * @param role value to {@link #role}
   */
  public User(String name, String password, String username, Role role) {
    this.name = name;
    this.setPassword(password);
    this.username = username;
    this.role = role;
  }

  /**
   * Constructor
   * @param name value to {@link #name}
   * @param password value to {@link #password}
   * @param username value {@link #username}
   */
  public User(String name, String password, String username) {
    this.name = name;
    this.setPassword(password);
    this.username = username;
  }
  /**
   * @return {@link #id}
   */
  public UUID getId() {
	return id;
  }


  
  public void setPassword(String password) {
	this.password = passwordEncoder.encode(password);
  }

 /**
   * @param username update {@link #username}
   */
  public void setUsername(String username) {
	this.username = username;
  }

  /**
   * @return {@link #name}
   */
  public String getName() {
	return name;
  }
  
  /**
   * @param name update {@link #name}
   */
  public void setName(String name) {
	this.name = name;
  }

  /**
   * @return {@link #role}
   */
  public Role getRole() {
	return role;
  }
  
  public boolean authenticationPassword(String password) {
	  return passwordEncoder.matches(this.password, password);
  }
  
  /**
   * @param role update {@link #role}
   */
  public void setRole(Role role) {
	this.role = role;
  }
  
  /**
   * @return {@link #password}
   */
  @Override
  public String getPassword() {
	 return password;
  }
    
  /**
   * @return {@link #username}
   */
  @Override
  public String getUsername() {
  	return username;
  }
  
  

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Set.of(new SimpleGrantedAuthority("ROLE_"+role.getName()));
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
