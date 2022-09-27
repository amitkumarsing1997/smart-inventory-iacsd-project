package com.iacsd.demo.repository;

import com.iacsd.demo.model.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Long>{

	Role findByName(String merchantRole);

}
