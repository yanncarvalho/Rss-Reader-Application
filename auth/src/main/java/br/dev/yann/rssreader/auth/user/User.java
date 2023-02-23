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

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SIZE_USER_USERNAME_MAX;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SIZE_FIELD_MAX;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity(name = "Users")
@Table(name = "users")
public class User implements UserDetails{

  @Serial
  private static final long serialVersionUID = 1L;

  /**
   *  Password encoder.
   */
  @Transient
  private static BCryptPasswordEncoder passwordEncoder = 
  				new BCryptPasswordEncoder();

  /**
   * Unique user identification.
   *
   * @see UUID
   */
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  /**
   * User login name.
   */
  @Column(name = "username", unique = true, 
		  nullable = false,  length = SIZE_USER_USERNAME_MAX) 
  private String username;

  /**
   * Encrypted user password.
   */
  @Column(nullable = false, length = SIZE_FIELD_MAX)
  private String password;

  /**
   * User name.
   */
  @Column(nullable = false, length = SIZE_FIELD_MAX)
  private String name;

  /**
   *
   * User role.
   * <br>
   * @see UserRole
   */
  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = SIZE_FIELD_MAX)
  private UserRole role;

  /**
   * Empty constructor.
   */
  public User() {}

  /**
   * Constructs a {@code User} with name, password and username as parameters.
   * <br>
   * i.e.: set {@link #role} as {@link RoleUser#USER}.
   * 
   * @param name value to {@link #name}.
   * @param password value to {@link #password}.
   * @param username value {@link #username}.
   * 
   */
  public User(String name, String password, String username) {
    this.name = name;
    this.setPassword(password);
    this.username = username;
    this.role = UserRole.USER;
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
	  return passwordEncoder.matches(password, this.password);
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
