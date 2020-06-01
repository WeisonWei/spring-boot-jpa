package com.weison.sbj.repository.custom;

import com.weison.sbj.entity.Account;
import com.weison.sbj.entity.Address;
import com.weison.sbj.modle.Transaction;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
@Slf4j
public class AccountCustomRepositoryImpl implements AccountCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    TransactionDefinition transactionDefinition;


    public Optional<Account> updateLastAccount(Transaction transaction) {
        TransactionStatus transactionStatus = null;
        Account newAccount = null;
        try {
            transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

            Long userId = transaction.getUid();
            Account.AccountType accountType = Account.AccountType.POINTS;
            Account.TransType transType = transaction.getTransType();

            TypedQuery<Account> query = entityManager.createQuery("select a from Account a " +
                    "where a.uid =:uid and a.accountType =:accountType order by a.utime desc", Account.class);
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("uid", userId);
            query.setParameter("accountType", accountType);
            query.setFirstResult(0);    //设置从第几个结果开始
            query.setMaxResults(1);        //设置显示几个结果
            Optional<Account> first = query.getResultList().stream().findFirst();
            log.debug("==thread:{}== first:{}", Thread.currentThread().getName(), first);

            newAccount = first.map(account -> account.newAddAccount(transaction.getAmount(), transType))
                    .orElse(null);
            entityManager.persist(newAccount);
            entityManager.flush();
            entityManager.clear();
            platformTransactionManager.commit(transactionStatus);//提交
        } catch (TransactionException e) {
            e.printStackTrace();
            platformTransactionManager.rollback(transactionStatus);//最好是放在catch 里面,防止程序异常而事务一直卡在哪里未提交
        }
        return Optional.ofNullable(newAccount);
    }
}
