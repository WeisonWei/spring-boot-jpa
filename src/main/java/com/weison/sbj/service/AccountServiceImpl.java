package com.weison.sbj.service;

import com.weison.sbj.entity.Account;
import com.weison.sbj.model.Transaction;
import com.weison.sbj.repository.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Resource
    private AccountRepository accountRepository;

    @Resource
    private AccountService accountService;

    @Override
    public Account getLastAccount(long uid, Account.AccountType accountType) {
        return accountRepository.findFirst1ByUserIdAndAccountTypeOrderByUpdateTimeDesc(uid, accountType)
                .orElse(null);
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public Account updateLastAccount(Transaction transaction) {
        return accountRepository.updateLastAccount(transaction)
                .orElse(null);
    }

}
