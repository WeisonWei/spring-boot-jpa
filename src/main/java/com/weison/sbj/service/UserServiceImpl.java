package com.weison.sbj.service;

import com.weison.sbj.entity.User;
import com.weison.sbj.repository.UserRepository;
import com.weison.sbj.repository.custom.UserCustomRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserCustomRepositoryImpl userCustomRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserByName(String name) {
        return userRepository.findTopByName(name).orElseGet(() -> null);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseGet(() -> null);
    }

    @Override
    public User getUserByIdAndVersion(Long id, Long version) {
        return userRepository.findByIdAndVersion(id, version).get();
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }


    @Override
    public Page<User> getPageUserByCondition(User user) {
        return userCustomRepository.findByCondition(user, PageRequest.of(0, 15));
    }

    @Override
    public List<User> findGreaterThanAge(int min) {
        return userCustomRepository.findGreaterThanAge(min);
    }

    @Override
    public Integer updateNameById(Long id, String name) {
        return userRepository.updateNameById(id,name);
    }
}
