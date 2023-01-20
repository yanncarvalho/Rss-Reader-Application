package br.dev.yann.rssreader.auth.user;

import static br.dev.yann.rssreader.auth.configuration.DefaultValue.ATTRIBUTE_UUID;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.JWT_BEARER;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_DELETE_USER;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_DELETE_USER_AS_ADMIN;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_FIND_USER;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_FIND_USERS;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_FIND_USER_AS_ADMIN;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_LOGIN;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_SAVE;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_UPDATE_USER;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.SWAGGER_UPDATE_USER_AS_ADMIN;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.USER_CREATED;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.USER_DELETED;
import static br.dev.yann.rssreader.auth.configuration.DefaultValue.USER_UPDATED;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.dev.yann.rssreader.auth.configuration.JwtService;
import br.dev.yann.rssreader.auth.user.record.ControllerDefaultRes;
import br.dev.yann.rssreader.auth.user.record.FindByIdReq;
import br.dev.yann.rssreader.auth.user.record.FindByIdRes;
import br.dev.yann.rssreader.auth.user.record.JwtRes;
import br.dev.yann.rssreader.auth.user.record.LoginReq;
import br.dev.yann.rssreader.auth.user.record.PageRes;
import br.dev.yann.rssreader.auth.user.record.SaveReq;
import br.dev.yann.rssreader.auth.user.record.UpdateAsAdminReq;
import br.dev.yann.rssreader.auth.user.record.UpdateReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("v1")
public class UserController{
 
  @Autowired
  private UserService service;

  @Autowired
  private AuthenticationManager manager;
  

  @Autowired
  private JwtService tokenService;


  @Tag(name = "Admin")
  @Operation(description =  SWAGGER_FIND_USERS)
  @GetMapping(value = "admin/findUsers", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(value = HttpStatus.OK)
  public PageRes<FindByIdRes> findAll(
		  @RequestParam(defaultValue = "0") @PositiveOrZero @Valid Integer page,
		  @RequestParam(defaultValue = "10") @PositiveOrZero @Valid Integer size,
		  @RequestParam(defaultValue = "id") @PositiveOrZero @Valid String sort){
    var usersFound = service.findAllUsers(PageRequest.of(page, size, Sort.by(sort)));
    var userRes = usersFound.get().map(FindByIdRes::new).toList();
    var pageImpl = new PageImpl<FindByIdRes>(userRes, usersFound.getPageable(), usersFound.getSize());
	return new PageRes<>(pageImpl);
  }

  @Tag(name = "Admin")
  @Operation(description = SWAGGER_FIND_USER_AS_ADMIN)
  @GetMapping(value = "admin/findUsers/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public FindByIdRes findByIdAsAdmin(@PathVariable("id") UUID id){
    var  user = service.findById(id);
    return new FindByIdRes(user);
  }

  @Tag(name = "Admin")
  @Operation(description =  SWAGGER_UPDATE_USER_AS_ADMIN)
  @PutMapping(value = "admin/update", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ControllerDefaultRes updateAsAdmin(@Valid @RequestBody UpdateAsAdminReq update){
    service.updateAsAdmin(update);
    return new ControllerDefaultRes(USER_UPDATED);
  }

  @Tag(name = "Admin")
  @Operation(description = SWAGGER_DELETE_USER_AS_ADMIN)
  @DeleteMapping(value = "admin/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ControllerDefaultRes deleteAsAdmin(@PathVariable("id") UUID id) {
    service.deleteUser(id);
    return new ControllerDefaultRes(USER_DELETED);
  }

  @Tag(name = "User")
  @Operation(description =  SWAGGER_UPDATE_USER)
  @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public JwtRes updade(@RequestAttribute(name = ATTRIBUTE_UUID) UUID id, @RequestBody @Valid UpdateReq update) {
    User user = service.update(update, id);
    return createJwtResponse(user);
  }
  @Tag(name = "User")
  @Operation(description = SWAGGER_DELETE_USER)
  @DeleteMapping(value = "delete", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ControllerDefaultRes delete(@RequestAttribute(name = ATTRIBUTE_UUID) UUID id){

	service.deleteUser(id);
    return new ControllerDefaultRes(USER_DELETED);
  }

  @Tag(name = "User")
  @Operation(description =  SWAGGER_FIND_USER)
  @GetMapping(value = "find", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public FindByIdReq findById(@RequestAttribute(name = ATTRIBUTE_UUID) UUID id) {
	var user =  service.findById(id);
    return new FindByIdReq(user.getUsername(), user.getName());
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
	return createJwtResponse(user);
  }


  @Tag(name = "New User")
  @Operation(description = SWAGGER_SAVE)
  @PostMapping(value = "save", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ControllerDefaultRes save(@RequestBody @Valid SaveReq save){
    service.save(save);
    return new ControllerDefaultRes(USER_CREATED);
  }

  /**
   * Create a JWT Response
   * @param user {@link User user} to be used in the JWT encoding
   * @return {@link JwtRes Jwt response}
   */
  private JwtRes createJwtResponse(User user) {
	 var tokenJWT = tokenService.encode(user);
     var exp = tokenService.decode(tokenJWT).getExpirationTime();

      return new JwtRes (tokenJWT, JWT_BEARER, exp);
  }

}

