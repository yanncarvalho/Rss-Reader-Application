package br.dev.yann.rssreader.auth.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.auth.role.RoleRepository;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository userRepostitory;
    
  @Autowired
  private RoleRepository roleRepostitory;
  
  private void verifyUserIsTheOnlyAdmin(UUID id) {
	  var roleAdmin = roleRepostitory.findByNameAndFlagActiveTrue("ADMIN").orElseThrow( () -> new RuntimeException("Role was not found"));
	  var adminIds = roleAdmin.getUsers().stream().map(User::getId).toList();
	  if(adminIds.size() == 1 && adminIds.contains(id)) {
		   throw new RuntimeException
		   			("It is not posible to delete or change the role of the only admin");
	  }
  }
  
  public Page<User> findAllUsers(Pageable pageable) {
    return userRepostitory.findAll(pageable);
  }

  public void deleteUser(UUID id) {
	  verifyUserIsTheOnlyAdmin(id);
	  userRepostitory.deleteById(id);
  }


  public void save(User user) {
	  if(user.getRole() == null) {
		  String defaultRole = "USER";
		  var role = roleRepostitory.findByNameAndFlagActiveTrue(defaultRole).orElseThrow(
				  () -> new RuntimeException("Role was not found"));
		  user.setRole(role);
	  }
	  userRepostitory.save(user);
  }
  public User update(UpdateReq update, UUID id) {
	 var user =  userRepostitory.getReferenceById(id);

     if (update.hasName()) {
	   	user.setName(update.name());
	 }

	 if (update.hasUsername()) {
	   	user.setUsername(update.username());
	 }

	 if (update.hasPassword()) {
	    user.setPassword(update.password());
	 }
	 verifyUserIsTheOnlyAdmin(id);
	 userRepostitory.save(user);
	 return user;

  }

  public User updateAsAdmin(UpdateAsAdminReq update) {
	 var user =  userRepostitory.getReferenceById(update.id());

     if (update.hasName()) {
	   	user.setName(update.name());
	 }

	 if (update.hasUsername()) {
	   	user.setUsername(update.username());
	 }

	 if (update.hasRole()) {
	    var role = roleRepostitory.findByNameAndFlagActiveTrue(update.role()).orElseThrow(
				  () -> new RuntimeException("Role was not found"));
	    user.setRole(role);
	 }
	 
	 verifyUserIsTheOnlyAdmin(update.id());

	 userRepostitory.save(user);
	 return user;

  }

  public boolean hasUsername(String username){
	return userRepostitory.existsUserByUsername(username);
  }


  public boolean hasUsernameWithOriginalId(String username, UUID id) {
    var user = userRepostitory.findByUsername(username);
    return user.isPresent() && user.get().getId().equals(id);
  }

  public boolean existsById(UUID id) {
    return userRepostitory.existsById(id);
  }

  public User findById(UUID id) {
    return userRepostitory.findById(id).orElse(null);
  }


  @Override 
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	  
	  return userRepostitory.findByUsername(username).orElseThrow(
			  		() -> new UsernameNotFoundException("User with username - " + username + " not found"));
	  

}



}
