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
    public List<Record> findRecordsWithLimit(int offset, int limit){
        LOGGER.info("Finding records with limit "+limit+" and offset "+offset);

        return recordDAO.findRecordsWithLimit(offset,limit);
    }
    public int getCountRecords(){
        LOGGER.info("Getting count of records");

        return recordDAO.getCountRecords();
    }
    public List<Record> findAllRecordsByUserId(Long id){
        LOGGER.info("Finding all records by user id " + id);

        return recordDAO.findRecordsByUserId(id);
    }
    public List<Record> findAllRecordsTime(Long id, String date, boolean isReady){
        LOGGER.info("Finding all records with date");

        return recordDAO.findAllRecordsByDate(id, date, isReady);
    }

    public boolean updateStatus(Long id, Status status){
        LOGGER.info("Update status");

        return recordDAO.updateStatus(id, status);
    }
    public boolean updateTime(Long id, String date){
        LOGGER.info("Update time");

        return recordDAO.updateTime(id, date);
    }

}
