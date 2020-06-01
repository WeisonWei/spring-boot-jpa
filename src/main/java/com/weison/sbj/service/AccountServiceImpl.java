package com.weison.sbj.service;

import com.weison.sbj.entity.Account;
import com.weison.sbj.modle.Transaction;
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
    public Account getAccount(long uid, Account.AccountType accountType) {
        return accountRepository.findFirst1ByUidAndAccountTypeOrderByUtimeDesc(uid, accountType)
                .orElseThrow(() -> new RuntimeException("账户不存在"));
    }

    @Override
    public Account getLastAccount(long uid, Account.AccountType accountType) {
        return accountRepository.findFirst1ByUidAndAccountTypeOrderByUtimeDesc(uid, accountType)
                .orElse(null);
    }

    @Override
    public Account saveAccount(Account account) {
        return accountRepository.save(account);
    }

    @Transactional(rollbackFor = Exception.class)
    public synchronized Account updateLastAccount(Transaction transaction) {
        return accountRepository.updateLastAccount(transaction)
                .orElse(null);
    }

}
