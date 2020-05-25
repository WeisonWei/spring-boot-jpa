package com.weison.sbj.repository.custom;

import com.weison.sbj.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserCustomRepository extends JpaSpecificationExecutor<User>, JpaRepository<User, Long> {
}
