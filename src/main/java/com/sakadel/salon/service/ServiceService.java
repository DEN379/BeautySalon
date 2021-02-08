package com.sakadel.salon.service;

import com.sakadel.salon.dao.ServiceDAO;
import com.sakadel.salon.entity.Service;
import org.apache.log4j.Logger;

public class ServiceService {

    private static final Logger LOGGER = Logger.getLogger(ServiceService.class);

    private ServiceDAO serviceDAO;

    public ServiceService(ServiceDAO serviceDAO) {
        LOGGER.info("Initializing ServiceServiceImpl");

        this.serviceDAO = serviceDAO;
    }

    public boolean addService(Service service){
        LOGGER.info("Add new service");
        return service != null && serviceDAO.createService(service).getId() != null;
    }

    public Service findServiceById(Long id){
        LOGGER.info("Finding a service by id = " + id);
        if(id == null) {
            return null;
        }
        return serviceDAO.findService(id);
    }

}
