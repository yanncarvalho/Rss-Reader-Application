package br.dev.yann.rssreader.auth.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.dev.yann.rssreader.auth.configuration.JWTToken;
import br.dev.yann.rssreader.auth.user.record.FindRes;
import br.dev.yann.rssreader.auth.user.record.FindUsersRes;
import br.dev.yann.rssreader.auth.user.record.JWTRes;
import br.dev.yann.rssreader.auth.user.record.LoginReq;
import br.dev.yann.rssreader.auth.user.record.SaveReq;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;
import br.dev.yann.rssreader.auth.user.record.UserDefaultRes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
public class UserController{

  @Autowired
  private UserService service;
  
  @Autowired 
  private JWTToken tokenService;
  
  @Autowired
  private AuthenticationManager manager;

  @GetMapping("admin/findUsers")
  @ResponseStatus(HttpStatus.OK)
  public Page<FindUsersRes> findAll(
		  @RequestParam(defaultValue = "0") @PositiveOrZero @Valid Integer page,
		  @RequestParam(defaultValue = "10") @PositiveOrZero @Valid Integer size,
		  @RequestParam(defaultValue = "id") @PositiveOrZero @Valid String sort){
    Page<User> usersFound = service.findAllUsers(PageRequest.of(page, size, Sort.by(sort)));
    var userRes = usersFound.get().map(FindUsersRes::new).toList();
    return new PageImpl<>(userRes, usersFound.getPageable(), usersFound.getSize());
  }

  @GetMapping("admin/findUsers/{id}")
  @ResponseStatus(HttpStatus.OK)
  public FindUsersRes findUserByIdAsAdmin(@PathVariable("id") UUID id){
    var  user = service.findById(id);
    if(user == null){
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }

    return new FindUsersRes(user);
  }

  @PutMapping("admin/update")
  @ResponseStatus(HttpStatus.OK)
  public UserDefaultRes updateUserAsAdmin(@Valid @RequestBody UpdateAsAdminReq update){

    if(update.hasUsername() && service.isUsernameUsedByAnotherUser(update.username(), update.id())) {
    	throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
    }

    if(!service.existsById(update.id())) {
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The id informed was not found");
    }

    service.updateAsAdmin(update);
    return new UserDefaultRes("User updated successfully");
  }

  @DeleteMapping("admin/delete/{id}")
  @ResponseStatus(HttpStatus.OK)
  public UserDefaultRes delete(@PathVariable("id") UUID id) {
    service.deleteUser(id);
    return new UserDefaultRes("User deleted successfully!");
  }

  @PutMapping("update")
  @ResponseStatus(HttpStatus.OK)
  public JWTRes updade(@RequestAttribute(name = "userUUID") UUID id, @RequestBody @Valid UpdateReq update) {
    if(update.hasUsername() && service.isUsernameUsedByAnotherUser(update.username(), id)) {
    	throw new ResponseStatusException(
    			HttpStatus.CONFLICT, "Username already exists");
    }
    User user = service.update(update, id);
    return jwtResponse(user);
  }

  @DeleteMapping("delete")
  @ResponseStatus(HttpStatus.OK)
  public UserDefaultRes deleteUserAsAdmin(@RequestAttribute(name = "userUUID")  UUID id){
    if(!service.existsById(id)){
	   throw new ResponseStatusException(
 			  HttpStatus.NOT_FOUND, "The user was not found");
    }

	service.deleteUser(id);
    return new UserDefaultRes("User deleted successfully!");
  }

  @GetMapping("find")
  @ResponseStatus(HttpStatus.OK)
  public FindRes findById(@RequestAttribute(name = "userUUID") UUID id) {
	var user =  service.findById(id);  	
    return new FindRes(user.getUsername(), user.getName());
  }

  @PostMapping("login")
  @ResponseStatus(HttpStatus.OK)
  public JWTRes login(@Valid @RequestBody LoginReq login) {
	  
	UsernamePasswordAuthenticationToken token = new 
			UsernamePasswordAuthenticationToken(
					login.username(), 
					login.password());
				
	Authentication authResult = manager.authenticate(token);
	if(!authResult.isAuthenticated()) {
		throw new BadCredentialsException("Incorrect credentials");
	}

	User user = (User) authResult.getPrincipal();
	return jwtResponse(user);

  }


  @PostMapping("save")
  @ResponseStatus(HttpStatus.CREATED)
  public UserDefaultRes save(@RequestBody @Valid SaveReq save){
    if (service.hasUsername(save.username())) {
    	throw new ResponseStatusException(
    			HttpStatus.CONFLICT, "Username already exists");
    }

    service.save(save);
    return new UserDefaultRes("User created!");

  }
  
  private JWTRes jwtResponse(User user) {
	 var tokenJWT = tokenService.getToken(user);
      var exp = tokenService.getClaims(tokenJWT).getExpirationTime();

      return new JWTRes (tokenJWT, "Bearer", exp);
  }

}

