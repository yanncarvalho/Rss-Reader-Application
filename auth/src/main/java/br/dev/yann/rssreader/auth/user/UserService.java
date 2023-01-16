package br.dev.yann.rssreader.auth.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.auth.user.record.SaveReq;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository userRepostitory;
    
  private void verifyUserIsTheOneAdmin(UUID id) {
	var isUserTheOnlyAdmin = userRepostitory.existsOneUserAdminById(id);
	if(isUserTheOnlyAdmin) 
		throw new SingleUserAdminException();
	  
  }
  
  public Page<User> findAllUsers(Pageable pageable) {
    return userRepostitory.findAll(pageable);
  }

  public void deleteUser(UUID id) {
	verifyUserIsTheOneAdmin(id);
	userRepostitory.deleteById(id);
  }


  public void save(SaveReq save) {

	  userRepostitory.save(new User(save.name(), save.password(), save.username()));
  }
  
  public User update(UpdateReq update, UUID id) {
	 var user =  userRepostitory.getReferenceById(id);

     if (update.hasName()) 
	   	 user.setName(update.name());
	 

	 if (update.hasUsername()) 
	   	 user.setUsername(update.username());
	 

	 if (update.hasPassword())
	     user.setPassword(update.password());
	
	 userRepostitory.save(user);
	 return user;

  }

  public User updateAsAdmin(UpdateAsAdminReq update) {
	 var user =  userRepostitory.getReferenceById(update.id());

     if (update.hasName())
	   	 user.setName(update.name());
	 
	 if (update.hasUsername()) 
		 user.setUsername(update.username());
	 
	 if (update.hasRole()) {
		if(!update.role().equals(UserRole.ADMIN))
			verifyUserIsTheOneAdmin(user.getId());
	  
	   user.setRole(update.role());
	}
	userRepostitory.save(user);
	return user;
  }

  public boolean hasUsername(String username){
	return userRepostitory.existsByUsername(username);
  }


  public boolean isUsernameUsedByAnotherUser(String username, UUID id) {
    var user = userRepostitory.findByUsername(username);
    return user.isPresent() && !user.get().getId().equals(id);
  }

  public boolean existsById(UUID id) {
    return userRepostitory.existsById(id);
  }

  public User findById(UUID id) {
    return userRepostitory.findById(id).orElseThrow(
    		() -> new UsernameNotFoundException(
    				"User with id %s not found".formatted(id)));
  }


  @Override 
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	return userRepostitory.findByUsername(username).orElseThrow(
			 () -> new UsernameNotFoundException(
					 "User with username %s not found".formatted(username)));
  }



}
