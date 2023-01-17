package br.dev.yann.rssreader.auth.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.auth.user.record.SaveReq;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;

/**
 * User Service
 */
@Service
public class UserService implements UserDetailsService{

  /**
   * {@link UserRepository} Instance
   */
  @Autowired
  private UserRepository userRepostitory;
   
  /**
   * Check if user with a specific id is the only user with role {@link UserRole#ADMIN Admin}.
   * @param id the {@link User#id id} to search for the user.
   * @throws SingleUserAdminException if it is the only user with role {@link UserRole#ADMIN Admin}. 
   */
  private void checkUserIsTheOneAdmin(UUID id) {
	var isUserTheOnlyAdmin = userRepostitory.existsOneUserAdminById(id);
	if(isUserTheOnlyAdmin) 
		throw new SingleUserAdminException();
  }
  
  /**
   * Returns a Slice of {@link User users}
   * @param pageable pages information
   * @return users wrapped in an {@link Slice}.  
   */
  public Slice<User> findAllUsers(Pageable pageable) {
    return userRepostitory.findAll(pageable);
  }
  
  /**
   * Delete a {@link User user} with the given id.
   * @param id user id pages information
   * @throws SingleUserAdminException see {@link #checkUserIsTheOneAdmin}  
   */
  public void deleteUser(UUID id) {
	checkUserIsTheOneAdmin(id);
	userRepostitory.deleteById(id);
  }

  /**
   * Create a new {@link User user}
   * @param save {@link SaveReq data} with new user information
   */
  public void save(SaveReq save) {
	  userRepostitory.save(new User(save.name(), save.password(), save.username()));
  }
  
  /**
   * Update user {@link User user}
   * @param update {@link UpdateReq data} with user information to be updated
   * @param id user id 
   * @return user updated
   */
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
  
  /**
   * Update user {@link User user}
   * @param update {@link UpdateAsAdminReq data} with user information to be updated
   * @return user updated
   */
  public User updateAsAdmin(UpdateAsAdminReq update) {
	 var user =  userRepostitory.getReferenceById(update.id());

     if (update.hasName())
	   	 user.setName(update.name());
	 if (update.hasUsername()) 
		 user.setUsername(update.username());
	 
	 if (update.hasRole()) {
		if(!update.role().equals(UserRole.ADMIN))
			checkUserIsTheOneAdmin(user.getId());
	   user.setRole(update.role());
	}
	 
	userRepostitory.save(user);
	return user;
  }

  /**
   * Check if a username has already been used
   * @param username the {@link User#username username} to check if it has already been used
   * @return {@code true} if has already been used, {@code false} if not.
   */
  public boolean hasUsername(String username){
	return userRepostitory.existsByUsername(username);
  }

  /**
   * Check if a username has already been used by another user
   * @param username the {@link User#username username} to check if it has already been used
   * @param id user id 
   * @return {@code true} if has already been used, {@code false} if not.
   */
  public boolean isUsernameUsedByAnotherUser(String username, UUID id) {
    var user = userRepostitory.findByUsername(username);
    return user.isPresent() && !user.get().getId().equals(id);
  }

  /**
   * Check if user exists in the system 
   * @param id user id 
   * @return {@code true} if user exists, {@code false} if not.
   */
  public boolean existsById(UUID id) {
    return userRepostitory.existsById(id);
  }
  
  /**
   * Find user by Id.
   * @param id user id 
   * @return user found
   * @throws UsernameNotFoundException if the user could not be found
   */
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
