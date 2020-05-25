package com.weison.sbj.repository.custom;

import com.weison.sbj.entity.Address;
import org.springframework.stereotype.Repository;
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
}
