package br.dev.yann.rssreader.auth.role;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.dev.yann.rssreader.auth.role.record.RoleDefaultRes;
import br.dev.yann.rssreader.auth.role.record.SaveReq;
import br.dev.yann.rssreader.auth.role.record.UpdateReq;
import jakarta.validation.Valid;
import jakarta.validation.constraints.PositiveOrZero;

@RestController
@RequestMapping("role")
public class RoleController{

  @Autowired
  private RoleService service;
  
  @PutMapping("update")
  public ResponseEntity<Object> updade(@RequestBody @Valid UpdateReq update) {
    service.update(update);
    return ResponseEntity
 		   .status(HttpStatus.CREATED)
 		   .body(new RoleDefaultRes("Role with id %s updated!".formatted(update.id())));
  }

  @GetMapping("findRoles")
  public ResponseEntity<Object> findAll( 
		  @RequestParam(defaultValue = "0") @PositiveOrZero @Valid Integer page,
		  @RequestParam(defaultValue = "10") @PositiveOrZero @Valid Integer size,
		  @RequestParam(defaultValue = "id") @PositiveOrZero @Valid String sort){
	  
    var roles = service.findAll(PageRequest.of(page, size, Sort.by(sort)));

    return ResponseEntity.status(HttpStatus.OK)
    		.body(roles);
  }

  @GetMapping("findRoles/{id}")
  public ResponseEntity<Object> find(@PathVariable("id") UUID id) {
	var role =  service.findById(id);  	
    return ResponseEntity.status(HttpStatus.OK)
    		.body(role);
  }


  @PostMapping("save")
  public ResponseEntity<Object> save(@RequestBody @Valid SaveReq save){

    service.save(save.name());
    return ResponseEntity
    		   .status(HttpStatus.CREATED)
    		   .body(new RoleDefaultRes("New role created!"));

  }

  @DeleteMapping("delete/{id}")
  public ResponseEntity<Object> delete(@PathVariable("id") UUID id){

    service.delete(id);
    return ResponseEntity
    		   .status(HttpStatus.OK)
    		   .body(new RoleDefaultRes("Role deleted!"));

  }
}

