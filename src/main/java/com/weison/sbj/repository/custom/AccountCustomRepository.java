package com.weison.sbj.repository.custom;


import com.weison.sbj.entity.Account;
import com.weison.sbj.modle.Transaction;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface AccountCustomRepository {

    Optional<Account> updateLastAccount(Transaction transaction);

    List<Account> batchSave(Collection<Account> taskLogs);

    List<Account> batchUpdate(Collection<Account> taskLogs);

}
