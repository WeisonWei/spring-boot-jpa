package com.weison.sbj.service;

import com.weison.sbj.entity.Account;
import com.weison.sbj.modle.Transaction;

public interface AccountService {

    Account getAccount(long uid, Account.AccountType accountType);

    Account getLastAccount(long uid, Account.AccountType accountType);

    Account saveAccount(Account account);

    Account updateLastAccount(Transaction transaction);

}
