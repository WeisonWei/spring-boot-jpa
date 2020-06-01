package com.weison.sbj.repository;

import com.weison.sbj.entity.Account;
import com.weison.sbj.repository.custom.AccountCustomRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>, BaseRepository<Account, Long>
        , AccountCustomRepository {

    Optional<Account> findFirst1ByUidAndAccountTypeOrderByUtimeDesc(Long userId, Account.AccountType accountType);

}
