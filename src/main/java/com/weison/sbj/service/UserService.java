package com.weison.sbj.service;

import com.weison.sbj.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    User save(User user);

    User getUserByName(String name);

    User getUserById(Long id);

    User getUserByIdAndVersion(Long id, Long version);

    List<User> getUsers();

    Page<User> getPageUserByCondition(User user);

    List<User> findGreaterThanAge(int min);

    Integer updateNameById(Long id, String name);

}
