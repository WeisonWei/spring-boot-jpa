package com.weison.sbj.service;

import com.weison.sbj.entity.Account;
import com.weison.sbj.model.Transaction;

public interface AccountService {

    Account getLastAccount(long uid, Account.AccountType accountType);

    Account saveAccount(Account account);

    Account updateLastAccount(Transaction transaction);

}
