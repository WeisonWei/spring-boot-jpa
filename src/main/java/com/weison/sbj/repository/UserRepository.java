package com.weison.sbj.repository;

import com.weison.sbj.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;


@Repository
public interface UserRepository extends JpaRepository<User, Long>, BaseRepository<User, Long> {

    Stream<User> getAllBySex(User.Sex sex);

    List<User> findAllBySex(User.Sex sex);

    Page<User> findAllBySex(User.Sex sex, Pageable pageable);

    List<User> findAllBySexIn(List<User.Sex> sex);

    Page<User> findAllBySexIn(List<User.Sex> sex, Pageable pageable);

    Optional<User> findTopByName(String name);

    @Transactional
    Optional<User> findById(Long id);

    Optional<User> findByIdAndVersion(Long id, Long version);

    @Async
    CompletableFuture<Optional<User>> findTop1ByName(String name);

    Boolean existsBySex(User.Sex sex);

    @Modifying
    @Query(value = "UPDATE User set name=?2 WHERE id=?1")
    Integer updateNameById(Long oid, String name);

}
