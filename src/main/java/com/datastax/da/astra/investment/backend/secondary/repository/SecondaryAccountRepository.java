package com.datastax.da.astra.investment.backend.secondary.repository;

import java.util.List;

import com.datastax.da.astra.investment.backend.model.Account;
import com.datastax.da.astra.investment.backend.repository.AccountRepository;


public interface SecondaryAccountRepository extends AccountRepository {

    List<Account> findByKeyUserName(String userName);
    
}
