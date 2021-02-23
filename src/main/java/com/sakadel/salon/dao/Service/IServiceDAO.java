package com.sakadel.salon.dao.Service;

import com.sakadel.salon.model.Service;

import java.util.List;

public interface IServiceDAO {
    Service createService(Service service);

    List<Service> findAll();

    Service findService(Long id);

}
