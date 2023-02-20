package br.dev.yann.rssreader.auth.user;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.USER_NOT_FOUND;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.auth.user.exception.UserSingleAdminException;
import br.dev.yann.rssreader.auth.user.exception.UsernameNotUniqueException;
import br.dev.yann.rssreader.auth.user.record.SaveReq;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;

@Service
public class UserService implements UserDetailsService{

  @Autowired
  private UserRepository repository;

  /**
   * Check if a username has already been used.
   * @param username the {@link User#username username} to check if it has already been used.
   * @return {@code true} if has already been used, {@code false} if not.
   */
  private boolean existsUsername(String username){	  
	  return repository.existsByUsername(username);
  }

  /**
   * Check if a username has already been used by another user.
   * @param username the {@link User#username username} to check if it has already been used.
   * @param id user to compare with the user found by the given username.
   * @return {@code true} if has already been used, {@code false} if not.
   */
  private boolean existsUsername(String username, UUID id){
	  var user = repository.findByUsername(username);
	  return user.isPresent() && !user.get().getId().equals(id);
  }

  /**
   * Returns a Slice of {@link User users}.
   * @param pageable pages information.
   * @return users wrapped in an {@link Slice}.
   */
  public Slice<User> findAllUsers(Pageable pageable) {
    return repository.findAll(pageable);
  }

  /**
   * Delete a {@link User user} with the given id.
   * @param id user id pages information.
   * @throws UserSingleAdminException if it is the only user with role {@link UserRole#ADMIN Admin}. 
   */
  public void deleteUser(UUID id) {
	if(repository.existsSoleUserAdminById(id)) 
		throw new UserSingleAdminException();
	repository.deleteById(id);
  }

  /**
   * Create a new {@link User user}.
   * @param save {@link SaveReq data} with new user information.
   * @throws UsernameNotUniqueException if the update username is already used.
   */
  public void save(SaveReq save) {
	  if(existsUsername(save.username())) 
	    	throw new UsernameNotUniqueException();		
	  repository.save(new User(save.name(), save.password(), save.username()));
  }

  /**
   * Update user {@link User user}.
   * @param update {@link UpdateReq data} with user information to be updated.
   * @param id user id.
   * @return user updated.
   * @throws UsernameNotUniqueException if the update username is already used.
   */
  public User update(UpdateReq update, UUID id) {
	  
	 if(update.hasUsername() && existsUsername(update.username(), id)) 
		 throw new UsernameNotUniqueException();
	 
	 var user =  repository.getReferenceById(id);
	 
     if (update.hasName())
	   	 user.setName(update.name());

	 if (update.hasUsername())
	   	 user.setUsername(update.username());

	 if (update.hasPassword())
	     user.setPassword(update.password());

	 repository.save(user);
	 return user;
  }

  /**
   * Update user {@link User user}.
   * @param update {@link UpdateAsAdminReq data} with user information to be updated.
   * @throws UsernameNotUniqueException if the update username is already used.
   * @throws UserSingleAdminException if it is the only user with role {@link UserRole#ADMIN Admin}. 
   */
  public void updateAsAdmin(UpdateAsAdminReq update) {
	  
	  
	if(update.hasUsername() && existsUsername(update.username(), update.id())) {
		 throw new UsernameNotUniqueException();
	 }
		 
	 var user = repository.getReferenceById(update.id());

     if (update.hasName()) {
	   	 user.setName(update.name());
     }
     
	 if (update.hasUsername()) {
		 user.setUsername(update.username());
	 }

	 if (update.hasRole()) {
		if(repository.existsSoleUserAdminById(user.getId())) 
			throw new UserSingleAdminException();
			
	   user.setRole(update.role());
	}

	repository.save(user);
  }

  /**
   * Find user by id.
   * @param id user id.
   * @return user found.
   * @throws UsernameNotFoundException if the user could not be found.
   */
  public User findById(UUID id) {
    return repository.findById(id).orElseThrow(
    		() -> new UsernameNotFoundException(USER_NOT_FOUND));
  }
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	return repository.findByUsername(username).orElseThrow(
    		() -> new UsernameNotFoundException(USER_NOT_FOUND));
  }

}
