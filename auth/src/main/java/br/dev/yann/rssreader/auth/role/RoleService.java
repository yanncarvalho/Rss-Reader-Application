package br.dev.yann.rssreader.auth.role;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.dev.yann.rssreader.auth.role.record.UpdateReq;

@Service
public class RoleService{

  @Autowired
  private RoleRepository roleRepostitory;

  public Page<Role> findAll(Pageable pageable) {
    return roleRepostitory.findAll(pageable);
  }

  public void delete (UUID id) {
	  var role = roleRepostitory.findById(id).orElseThrow(() -> new RuntimeException("role not found"));
	  if(!role.getUsers().isEmpty()) {
		  role.setFlagActive(false);
		  roleRepostitory.save(role);
		  return;
	  }
	  roleRepostitory.deleteById(id);
  }

  public void save(String name) {
	 roleRepostitory.findByNameAndFlagActiveTrue(name)
		.ifPresent( 
		r ->   new RuntimeException("Name: "+r.getName()+" already used to an active role")); 
	  roleRepostitory.save(new Role(name));
  }
  
  public void update(UpdateReq update) {
	 roleRepostitory.findByNameAndFlagActiveTrue(update.name())
	 				.ifPresent( 
	 				r ->   new RuntimeException("Name: "+r.getName()+" already used to an active role")); 
	  
	 var role = roleRepostitory.getReferenceById(update.id());
	 if(update.hasName()) {
		 role.setName(update.name());
	 }

	 if(update.hasFlagActive()) {
		 role.setFlagActive(update.flagActive());
	 }

	 roleRepostitory.save(role);
  }

  public Role findById(UUID id) {
    return roleRepostitory.findById(id).orElse(null);
  }

}
