package com.sakadel.salon.service;

import com.sakadel.salon.dao.MasterDAO;
import com.sakadel.salon.entity.Master;
import com.sakadel.salon.entity.Service;
import org.apache.log4j.Logger;

import java.util.List;

public class MasterService {
    private static final Logger LOGGER = Logger.getLogger(MasterService.class);

    private MasterDAO masterDAO;

    public MasterService(MasterDAO masterDAO) {
        LOGGER.info("Initializing ServiceServiceImpl");
        this.masterDAO = masterDAO;
    }

    public boolean addMaster(Long id){
        LOGGER.info("Add new service");
        return id != null && masterDAO.setMaster(id).getId() != null;
    }

    public Master findMasterById(Long id){
        LOGGER.info("Finding a service by id = " + id);
        if(id == null) {
            return null;
        }
        return masterDAO.findMasterById(id);
    }

    public List<Master> findAllWithName() {
        LOGGER.info("Getting all masters");

        return masterDAO.findAllWithName();
    }
}
