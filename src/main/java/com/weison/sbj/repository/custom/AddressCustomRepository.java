package com.weison.sbj.repository.custom;

import com.weison.sbj.entity.Address;

import java.util.List;


public interface AddressCustomRepository {

    List<Address> getAddress(String nameLike);

}
