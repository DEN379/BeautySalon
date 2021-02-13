package com.sakadel.salon.service;

import com.sakadel.salon.dao.RecordDAO;
import com.sakadel.salon.entity.Record;
import com.sakadel.salon.entity.Status;
import org.apache.log4j.Logger;

import java.util.List;

public class RecordService {
    private static final Logger LOGGER = Logger.getLogger(RecordService.class);

    private RecordDAO recordDAO;

    public RecordService(RecordDAO recordDAO) {
        LOGGER.info("Initializing RecordService");

        this.recordDAO = recordDAO;
    }

    public boolean addRecord(Record record){
        LOGGER.info("Add new record");
        return record != null && recordDAO.createRecord(record).getId() != null;
    }

    public Record findRecord(Long id){
        LOGGER.info("Finding a record by id = " + id);
        if(id == null) {
            return null;
        }
        return recordDAO.findRecord(id);
    }

    public List<Record> findAllRecord(){
        LOGGER.info("Finding all records");

        return recordDAO.findAllRecords();
    }

    public boolean updateStatus(Long id, Status status){
        LOGGER.info("Update status");

        return recordDAO.updateStatus(id, status);
    }

}
