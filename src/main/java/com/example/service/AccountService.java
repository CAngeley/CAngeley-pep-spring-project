package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;
@Service
@Transactional
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account registerUser(Account account){
        Account dupe = findUser(account.getUsername());
        if(dupe == null && account.getPassword().length() > 3 && !account.getUsername().isEmpty()) {
            return accountRepository.save(account);
        } else if (dupe != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public Account login(Account account){
        Account loginAccount = findUser(account.getUsername(), account.getPassword());
        if(loginAccount != null){
            return loginAccount;
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public Account findUser(String username){
        Optional<Account> accountOptional = accountRepository.findUserByUsername(username);
        return accountOptional.orElse(null);
    }

    public Account findUser(String username, String password){
        Optional<Account> accountOptional = accountRepository.findUserByUsernameAndPassword(username, password);
        return accountOptional.orElse(null);
    }

    public Account findUser(int id){
        Optional<Account> accountOptional = accountRepository.findUserById(id);
        return accountOptional.orElse(null);
    }
}
