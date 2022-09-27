package com.iacsd.demo.repository;

import com.iacsd.demo.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepo extends CrudRepository<Account, Long> {

    @Query("SELECT a FROM AppUser au INNER JOIN au.account a WHERE au.uid = :uid")
    Optional<Account> getAccountByUserUid(String uid);

    @Query("SELECT a.id FROM AppUser au INNER JOIN au.account a WHERE au.uid = :uid")
    Optional<Long> getAccountIdByUserUid(String uid);
}
