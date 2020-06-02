package com.weison.sbj.repository.custom;

import com.weison.sbj.entity.Account;
import com.weison.sbj.modle.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.IteratorUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.CacheStoreMode;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
@Slf4j
public class AccountCustomRepositoryImpl extends BatchRepository implements AccountCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Resource
    private TransactionDefinition transactionDefinition;

    @Override
    @Transactional
    public List<Account> batchSave(Collection<Account> collection) {
        Iterator<Account> iterator = super.batchSave(collection).iterator();
        return IteratorUtils.toList(iterator);
    }

    @Override
    @Transactional
    public List<Account> batchUpdate(Collection<Account> collection) {
        Iterator<Account> iterator = super.batchUpdate(collection).iterator();
        return IteratorUtils.toList(iterator);
    }

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
            query.setParameter("userId", userId);
            query.setParameter("accountType", accountType);
            query.setFirstResult(0);
            query.setMaxResults(1);
            Optional<Account> first = query.getResultList().stream().findFirst();
            log.debug("==thread:{}== first:{}", Thread.currentThread().getName(), first);

            newAccount = first.map(account -> account.newAddAccount(transaction.getAmount(), transType))
                    .orElse(null);
            entityManager.persist(newAccount);
            entityManager.flush();
            entityManager.clear();
            platformTransactionManager.commit(transactionStatus);
        } catch (TransactionException e) {
            e.printStackTrace();
            //最好是放在catch 里面,防止程序异常而事务一直卡在哪里未提交
            platformTransactionManager.rollback(transactionStatus);
        }
        return Optional.ofNullable(newAccount);
    }
}
