package com.iacsd.demo.repository;

import com.iacsd.demo.model.AppUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long> {

	Optional<AppUser> findByUsername(String username);

	AppUser findByUid(String uid);

	@Query("SELECT COUNT(ap.id)>0 from AppUser ap where ap.username=:userName")
	Boolean isUserNameExists(@Param("userName") String userName);

	Page<AppUser> findAll(Pageable pageable);
	@Query("SELECT COUNT (ap.id)>0 from AppUser ap  where ap.email=:email")
	boolean existsByEmail(String email);
}
		

