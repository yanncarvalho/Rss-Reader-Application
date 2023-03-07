package com.github.yanncarvalho.rssreader.auth.user;

import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.ATTRIBUTE_UUID;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.JWT_BEARER;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_ADMIN_FIND_ALL_USERS;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_ADMIN_FIND_USER;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_ADMIN_UPDATE;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_CREATE_USER;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_LOGIN;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_USER_FIND;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.LOGGER_USER_UPDATE;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_DELETE_USER;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_DELETE_USER_AS_ADMIN;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_FIND_USER;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_FIND_USERS;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_FIND_USER_AS_ADMIN;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_LOGIN;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_SAVE;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_UPDATE_USER;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.SWAGGER_UPDATE_USER_AS_ADMIN;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USER_CREATED;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USER_DELETED;
import static com.github.yanncarvalho.rssreader.auth.configuration.DefaultValue.USER_UPDATED;

import org.hibernate.validator.constraints.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.github.yanncarvalho.rssreader.auth.configuration.JwtService;
import com.github.yanncarvalho.rssreader.auth.user.record.ControllerDefaultRes;
import com.github.yanncarvalho.rssreader.auth.user.record.FindByIdRes;
import com.github.yanncarvalho.rssreader.auth.user.record.FindByIdAsAdminRes;
import com.github.yanncarvalho.rssreader.auth.user.record.JwtRes;
import com.github.yanncarvalho.rssreader.auth.user.record.LoginReq;
import com.github.yanncarvalho.rssreader.auth.user.record.PageRes;
import com.github.yanncarvalho.rssreader.auth.user.record.SaveReq;
import com.github.yanncarvalho.rssreader.auth.user.record.UpdateAsAdminReq;
import com.github.yanncarvalho.rssreader.auth.user.record.UpdateReq;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping("v1")
public class UserController{
	
  private static Logger logger = LoggerFactory.getLogger(UserController.class);
 
  @Autowired
  private UserService service;

  @Autowired
  private AuthenticationManager manager;
  
  @Autowired
  private JwtService tokenService;


  @Tag(name = "Admin")
  @Operation(description =  SWAGGER_FIND_USERS)
  @GetMapping(value = "admin/findUsers/", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public PageRes<FindByIdAsAdminRes> findAll(
		  @RequestParam(defaultValue = "0") @PositiveOrZero Integer page,
		  @RequestParam(defaultValue = "10") @PositiveOrZero Integer size,
		  @RequestParam(defaultValue = "id") @NotBlank String sort){
    var usersFound = service.findAllUsers(PageRequest.of(page, size, Sort.by(sort)));
    var userRes = usersFound.get().map(FindByIdAsAdminRes::new).toList();
    var pageImpl = new PageImpl<FindByIdAsAdminRes>(userRes, usersFound.getPageable(), usersFound.getNumberOfElements());
	logger.info(LOGGER_ADMIN_FIND_ALL_USERS);
    return new PageRes<>(pageImpl);
  }	

  @Tag(name = "Admin")
  @Operation(description = SWAGGER_FIND_USER_AS_ADMIN)
  @GetMapping(value = "admin/findUsers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public FindByIdAsAdminRes findByIdAsAdmin(@PathVariable("id")  @UUID String id){
    var  user = service.findById(java.util.UUID.fromString(id)); 
	logger.info(LOGGER_ADMIN_FIND_USER(id));
    return new FindByIdAsAdminRes(user);
  }

  @Tag(name = "Admin")
  @Operation(description =  SWAGGER_UPDATE_USER_AS_ADMIN)
  @PutMapping(value = "admin/update", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ControllerDefaultRes updateAsAdmin(@RequestBody UpdateAsAdminReq update){
    service.updateAsAdmin(update);
	logger.info(LOGGER_ADMIN_UPDATE(update.id()));
    return new ControllerDefaultRes(USER_UPDATED);
  }

  @Tag(name = "Admin")
  @Operation(description = SWAGGER_DELETE_USER_AS_ADMIN)
  @DeleteMapping(value = "admin/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ControllerDefaultRes deleteAsAdmin(@PathVariable("id") @UUID String id) {
    service.deleteUser(java.util.UUID.fromString(id));
	logger.info(LOGGER_ADMIN_UPDATE(id));
    return new ControllerDefaultRes(USER_DELETED);
  }

  @Tag(name = "User")
  @Operation(description =  SWAGGER_UPDATE_USER)
  @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public JwtRes updade(@RequestAttribute(name = ATTRIBUTE_UUID) java.util.UUID id, @RequestBody @Valid UpdateReq update) {
    User user = service.update(update, id);
	logger.info(LOGGER_USER_UPDATE(id));
    return createJwtResponse(user);
  }
  @Tag(name = "User")
  @Operation(description = SWAGGER_DELETE_USER)
  @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ControllerDefaultRes delete(@RequestAttribute(name = ATTRIBUTE_UUID) java.util.UUID id){
	service.deleteUser(id);
	logger.info(LOGGER_USER_UPDATE(id));
    return new ControllerDefaultRes(USER_DELETED);
  }

  @Tag(name = "User")
  @Operation(description =  SWAGGER_FIND_USER)
  @GetMapping(value = "find", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public FindByIdRes findById(@RequestAttribute(name = ATTRIBUTE_UUID) java.util.UUID id) {
	var user = service.findById(id);
	logger.info(LOGGER_USER_FIND(user.getId()));
    return new FindByIdRes(user.getId(), user.getUsername(), user.getName());
  }

  @Tag(name = "Login")
  @Operation(description =  SWAGGER_LOGIN)
  @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public JwtRes login(@Valid @RequestBody LoginReq login) {
	UsernamePasswordAuthenticationToken token = 
			new	UsernamePasswordAuthenticationToken(
						login.username(),
						login.password());

	Authentication authResult = manager.authenticate(token);
	var user = (User) authResult.getPrincipal();
    logger.info(LOGGER_LOGIN(user.getId()));
	return createJwtResponse(user);
  }


  @Tag(name = "New User")
  @Operation(description = SWAGGER_SAVE)
  @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ControllerDefaultRes save(@RequestBody @Valid SaveReq save){
    var user = service.save(save);
    logger.info(LOGGER_CREATE_USER(user.getId()));
    return new ControllerDefaultRes(USER_CREATED);
  }

  /**
   * Create a JWT Response
   * @param user {@link User user} to be used in the JWT encoding
   * @return {@link JwtRes Jwt response}
   */
  private JwtRes createJwtResponse(User user) {
	 var tokenJWT = tokenService.encode(user);
     var exp = tokenService.decode(tokenJWT).getExpiresAt();
      return new JwtRes (tokenJWT, JWT_BEARER, exp);
  }

}

