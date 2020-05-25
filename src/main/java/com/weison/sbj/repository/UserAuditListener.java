package com.weison.sbj.repository;

import com.weison.sbj.entity.User;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;

@Slf4j
public class UserAuditListener {
    @PostPersist
    private void postPersist(User entity) {
        log.info("User [ " + entity.getId() + " ]被更新");
    }

    @PostRemove
    private void PostRemove(User entity) {
        log.info("User [ " + entity.getId() + " ]被删除");
    }

    @PostUpdate
    private void PostUpdate(User entity) {
        log.info("User [ " + entity.getId() + " ]被更新");
    }
}
