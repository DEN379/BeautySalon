package com.sakadel.salon.service;

import com.sakadel.salon.dao.ServiceMasterDAO;
import com.sakadel.salon.entity.ServiceMaster;
import org.apache.log4j.Logger;

public class ServiceMasterService {
    private static final Logger LOGGER = Logger.getLogger(ServiceMasterService.class);

    private ServiceMasterDAO serviceMasterDAO;

    public ServiceMasterService(ServiceMasterDAO serviceMasterDAO) {
        LOGGER.info("Initializing ServiceMasterServiceImpl");

        this.serviceMasterDAO = serviceMasterDAO;
    }

    public boolean addServiceMaster(ServiceMaster serviceMaster){
        LOGGER.info("Add new service-master");
        return serviceMaster != null && serviceMasterDAO.createServiceMaster(serviceMaster).getId() != null;
    }

    public ServiceMaster findServiceMasterById(Long id){
        LOGGER.info("Finding a service by id = " + id);
        if(id == null) {
            return null;
        }
        return serviceMasterDAO.findServiceMaster(id);
    }
}
