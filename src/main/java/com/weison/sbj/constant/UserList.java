package com.weison.sbj.constant;

import com.weison.sbj.entity.User;
import com.weison.sbj.exception.UserException;
import com.weison.sbj.repository.UserRepository;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public enum UserList {

    FEMALE((Sex, type) -> User.Sex.FEMALE == Sex && 0 == type,
            (page, repository) -> getPageBySex(page, repository),
            (repository) -> getFemaleList(repository)),
    MALE((Sex, type) -> User.Sex.MALE == Sex && 1 == type,
            (page, repository) -> getMalePage(page, repository),
            (repository) -> getMaleList(repository));

    /**
     * 前端偏爱 0 1
     * 0为支出 1为收入
     */
    @Getter
    private BiPredicate<User.Sex, Integer> biPredicate;

    @Getter
    private BiFunction<Integer, UserRepository, Page<User>> pageFunction;

    @Getter
    private Function<UserRepository, List<User>> listFunction;


    UserList(BiPredicate<User.Sex, Integer> biPredicate,
             BiFunction<Integer, UserRepository, Page<User>> pageFunction,
             Function<UserRepository, List<User>> listFunction) {
        this.biPredicate = biPredicate;
        this.pageFunction = pageFunction;
        this.listFunction = listFunction;
    }

    public static Page<User> getAmountPage(User.Sex sex, Integer type, int page, UserRepository repository) {
        return Arrays.stream(UserList.values())
                .filter(userList -> userList.getBiPredicate().test(sex, type))
                .findAny()
                .map(userList -> userList.getPageFunction().apply(page, repository))
                .orElseThrow(() -> new UserException(""));
    }

    public static List<User> getAmountList(User.Sex sex, Integer type, UserRepository repository) {
        return Arrays.stream(UserList.values())
                .filter(userList -> userList.getBiPredicate().test(sex, type))
                .findAny()
                .map(userList -> userList.getListFunction().apply(repository))
                .orElseThrow(() -> new UserException(""));
    }

    private static Pageable getPageAble(Integer page) {
        return PageRequest.of(page, 15);
    }

    private static Page<User> getPageBySex(Integer page, UserRepository repository) {
        return getPageBySex(page, User.Sex.FEMALE, repository);
    }

    private static List<User> getFemaleList(UserRepository repository) {
        return getListBySex(User.Sex.FEMALE, repository);
    }

    private static Page<User> getMalePage(Integer page, UserRepository repository) {
        return getPageBySex(page, User.Sex.MALE, repository);
    }

    private static List<User> getMaleList(UserRepository repository) {
        return getListBySex(User.Sex.MALE, repository);
    }

    private static Page<User> getPageBySex(Integer page, User.Sex Sex, UserRepository repository) {
        return repository.findAllBySex(Sex, getPageAble(page));
    }

    private static Page<User> getPageBySexList(Integer page, List<User.Sex> sexes, UserRepository repository) {
        return repository.findAllBySexIn(sexes, getPageAble(page));
    }

    private static List<User> getListBySex(User.Sex Sex, UserRepository repository) {
        return repository.findAllBySex(Sex);
    }

    private static List<User> getListBySexList(Integer page, List<User.Sex> sexes, UserRepository repository) {
        return repository.findAllBySexIn(sexes);
    }
}
