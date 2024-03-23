package com.example.repository;

import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    public Optional<Account> findUserByUsername(String username);

    public Optional<Account> findUserByUsernameAndPassword(String username, String password);
    
    @Query("SELECT a FROM Account a WHERE a.account_id = ?1")
    public Optional<Account> findUserById(int id);

}
