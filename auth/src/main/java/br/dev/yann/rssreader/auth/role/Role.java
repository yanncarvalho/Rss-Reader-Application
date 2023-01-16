package br.dev.yann.rssreader.auth.role;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.dev.yann.rssreader.auth.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity(name = "Roles")
@Table(name = "roles")
public class Role implements Serializable {
		
	private static final long serialVersionUID = 1L;

	public Role() {}
	
	public Role(String name) {
		this.name = name;
		this.flagActive = true;
		this.users = new ArrayList<>();
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 255)
	@Size(min = 3, max = 255) 
	private String name;
	
	@OneToMany(mappedBy = "role", fetch = FetchType.LAZY,
	            cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<User> users = new ArrayList<>();
	
	@Column(nullable = false, name = "flag_active")
	@NotNull
	private Boolean flagActive  = true; 
	
	public UUID getId() {
		return id;
	}
		
	public Boolean getFlagActive() {
		return flagActive;
	}

	public void setFlagActive(Boolean flagActive) {
		this.flagActive = flagActive;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		 users.add(user);
	}
	public void removeUser(User user) {
		 users.remove(user);
	}
	
	public void clearUser() {
		 users.clear();
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
		Role other = (Role) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "role ["
				+ "id=" + id  
				+ ", name=" + name
			    + "]";
	}
	
}
