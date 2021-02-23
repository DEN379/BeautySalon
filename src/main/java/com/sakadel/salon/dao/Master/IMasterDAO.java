package com.sakadel.salon.dao.Master;

import com.sakadel.salon.model.Master;

import java.util.List;

public interface IMasterDAO {

    Master setMaster(Long id);

    List<Master> findAllWithName();

    List<Master> findAllWithNameOrder(boolean isByRate, boolean isDescending);

    Master findMasterWithNameById(Long id);

    List<Master> findAll();

    boolean updateMasterRate(Long id, float rate);

    Master findMasterByUserId(Long id);

    Master findMasterById(Long id);

}
