package com.datastax.da.astra.investment.backend.primary.repository;

import java.util.List;

import com.datastax.da.astra.investment.backend.model.Account;
import com.datastax.da.astra.investment.backend.repository.AccountRepository;


public interface PrimaryAccountRepository extends AccountRepository {

    List<Account> findByKeyUserName(String userName);
    
}
