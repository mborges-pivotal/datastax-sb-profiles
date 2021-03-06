package com.datastax.da.astra.investment.backend.repository;

import java.util.List;

import com.datastax.da.astra.investment.backend.model.Account;
import com.datastax.da.astra.investment.backend.model.AccountKey;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, AccountKey> {

    List<Account> findByKeyUserName(String userName);
    
}
