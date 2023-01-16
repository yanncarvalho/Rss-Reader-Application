package br.dev.yann.rssreader.auth.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.dev.yann.rssreader.auth.user.record.UserDefaultRes;
import br.dev.yann.rssreader.auth.configuration.TokenService;
import br.dev.yann.rssreader.auth.user.record.FindRes;
import br.dev.yann.rssreader.auth.user.record.FindUsersRes;
import br.dev.yann.rssreader.auth.user.record.JWTRes;
import br.dev.yann.rssreader.auth.user.record.LoginReq;
import br.dev.yann.rssreader.auth.user.record.SaveReq;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
public class UserController{

  @Autowired
  private UserService service;
  
  @Autowired 
  private TokenService tokenService;
  
  @Autowired
  private AuthenticationManager manager;


  @GetMapping("admin/findUsers")
  public ResponseEntity<Object> findAll(
		  @RequestParam(defaultValue = "0") @PositiveOrZero @Valid Integer page,
		  @RequestParam(defaultValue = "10") @PositiveOrZero @Valid Integer size,
		  @RequestParam(defaultValue = "id") @PositiveOrZero @Valid String sort){
    Page<User> usersFound = service.findAllUsers(PageRequest.of(page, size, Sort.by(sort)));
    var userRes = usersFound.get().map(FindUsersRes::new).toList();
    return ResponseEntity
    		.status(HttpStatus.OK)
    		.body(new PageImpl<FindUsersRes>(userRes, usersFound.getPageable(), usersFound.getSize()));
  }

  @GetMapping("admin/findUsers/{id}")
  public ResponseEntity<Object> findUserByIdAsAdmin(@PathVariable("id") UUID id){
    var  user = service.findById(id);
    if(user == null){
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
    }

    return ResponseEntity
    		.status(HttpStatus.OK)
    		.body(new FindUsersRes(user));
  }

  @PutMapping("admin/update")
  public ResponseEntity<Object>  updateUserAsAdmin(@Valid @RequestBody UpdateAsAdminReq update){

    if(update.hasUsername() && !service.hasUsernameWithOriginalId(update.username(), update.id())) {
    	throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
    }

    if(!service.existsById(update.id())) {
    	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The id informed was not found");
    }

    service.updateAsAdmin(update);
    return ResponseEntity
    		.status(HttpStatus.OK)
    		.body(new UserDefaultRes("User updated successfully"));
  }

  @DeleteMapping("admin/delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") UUID id) {
    service.deleteUser(id);
    return ResponseEntity
    		.status(HttpStatus.OK).body(new UserDefaultRes("User deleted successfully!"));
  }

  @PutMapping("update")
  public ResponseEntity<Object> updade(@RequestAttribute(name = "userUUID") UUID id, @RequestBody @Valid UpdateReq update) {
    if(update.hasUsername() && !service.hasUsernameWithOriginalId(update.username(), id)) {
    	throw new ResponseStatusException(
    			HttpStatus.CONFLICT, "Username already exists");
    }
    User user = service.update(update, id);
  
    var tokenJWT = tokenService.getToken(user);
    var exp = tokenService.getClaims(tokenJWT).getExpirationTime().getTime();
    return ResponseEntity
    		.status(HttpStatus.OK)
    		.body(new JWTRes (tokenJWT, "Bearer", exp));
  }

  @DeleteMapping("delete")
  public ResponseEntity<Object> deleteUserAsAdmin(@RequestAttribute(name = "userUUID")  UUID id){
   if(!service.existsById(id)){
	   throw new ResponseStatusException(
 			  HttpStatus.NOT_FOUND, "The user was not found");
    }

	service.deleteUser(id);
    return ResponseEntity	
    		.status(HttpStatus.OK)
    		.body(new UserDefaultRes("User deleted successfully!"));
  }

  @GetMapping("find")
  public ResponseEntity<Object> find(@RequestAttribute(name = "userUUID") UUID id) {
	var user =  service.findById(id);  	
    return ResponseEntity.status(HttpStatus.OK)
    		.body(new FindRes(user.getUsername(), user.getName()));
  }

  @PostMapping("login")
  public ResponseEntity<Object> login(@Valid @RequestBody LoginReq login) {
	  
		UsernamePasswordAuthenticationToken token = new 
				UsernamePasswordAuthenticationToken(
						login.username(), 
						login.password());
				
		Authentication authResult = manager.authenticate(token);
		if(!authResult.isAuthenticated()) {
			throw new  RuntimeException("not valid password");
		}
		
       var tokenJWT = tokenService.getToken((User)authResult.getPrincipal());
       var exp = tokenService.getClaims(tokenJWT).getExpirationTime().getTime();
       return ResponseEntity 
    		  .status(HttpStatus.OK)
    		  .body(new JWTRes (tokenJWT, "Bearer", exp));

  }

  @PostMapping("save")
  public ResponseEntity<Object> save(@RequestBody @Valid SaveReq save){
    if (service.hasUsername(save.username())) {
    	throw new ResponseStatusException(
    			HttpStatus.CONFLICT, "Username already exists");
    }

	var newUser = new User(save.name(), save.password(), save.username());
    service.save(newUser);
    return ResponseEntity
    		   .status(HttpStatus.CREATED)
    		   .body(new UserDefaultRes("User created!"));

  }

}

