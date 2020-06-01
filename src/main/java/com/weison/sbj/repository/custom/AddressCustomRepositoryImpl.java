package com.weison.sbj.repository.custom;

import com.weison.sbj.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class AddressCustomRepositoryImpl implements AddressCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    PlatformTransactionManager platformTransactionManager;

    @Autowired
    TransactionDefinition transactionDefinition;
    @Override
    public List<Address> getAddress(String nameLike) {
        Query query = entityManager.createNativeQuery("SELECT u.* FROM Address as u WHERE u.city like", Address.class);
        query.setParameter(1, nameLike + "%");
        return query.getResultList();
    }


    public List<Address> getUserAddress(String nameLike) {
        Query query = entityManager.createNativeQuery("SELECT u.* FROM Address as u WHERE u.city like", Address.class);
        query.setParameter(1, nameLike + "%");
        return query.getResultList();
    }

    /*public Optional<Account> updateLastAccountByTask(TaskLog taskLog) {
        TransactionStatus transactionStatus = null;
        Address newAccount = null;
        try {
            transactionStatus = platformTransactionManager.getTransaction(transactionDefinition);

            String note = taskLog.getNote();
            Long userId = taskLog.getUid();
            Account.AccountType accountType = Account.AccountType.POINTS;
            Account.TransType transType = note.contains("学习中奖励") ?
                    Account.TransType.LEARNING_AWARD : Account.TransType.TASK;

            TypedQuery<Account> query = entityManager.createQuery("select a from Account a " +
                    "where a.uid =:uid and a.accountType =:accountType order by a.utime desc", Account.class);
            query.setHint("javax.persistence.cache.storeMode", CacheStoreMode.REFRESH);
            query.setParameter("uid", userId);
            query.setParameter("accountType", accountType);
            query.setFirstResult(0);    //设置从第几个结果开始
            query.setMaxResults(1);        //设置显示几个结果
            Optional<Account> first = query.getResultList().stream().findFirst();
            log.debug("==thread:{}== first:{}", Thread.currentThread().getName(), first);

            newAccount = first.map(account -> account.newAddAccountByTaskLog(taskLog).setNote(note).setTransType(transType))
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
    }*/
}
