package com.weison.sbj.repository.custom;


import com.weison.sbj.entity.Account;
import com.weison.sbj.modle.Transaction;

import java.util.Optional;


public interface AccountCustomRepository {

    public Optional<Account> updateLastAccountByTask(Transaction transaction);

}
