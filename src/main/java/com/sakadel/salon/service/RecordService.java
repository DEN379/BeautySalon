package com.sakadel.salon.service;

import com.sakadel.salon.dao.Record.RecordDAO;
import com.sakadel.salon.model.Record;
import com.sakadel.salon.model.Status;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class RecordService {
    private static final Logger LOGGER = Logger.getLogger(RecordService.class);

    private RecordDAO recordDAO;

    public RecordService(RecordDAO recordDAO) {
        LOGGER.info("Initializing RecordService");
        this.recordDAO = recordDAO;
    }

    public boolean addRecord(Record record) {
        LOGGER.info("Add new record");
        return record != null && recordDAO.createRecord(record).getId() != null;
    }

    public Record findRecord(Long id) {
        LOGGER.info("Finding a record by id = " + id);
        if (id == null) {
            return null;
        }
        return recordDAO.findRecord(id);
    }

    public List<Record> findAllRecord() {
        LOGGER.info("Finding all records");
        return recordDAO.findAllRecords();
    }

    public List<Record> findRecordsWithLimit(int offset, int limit) {
        LOGGER.info("Finding records with limit " + limit + " and offset " + offset);

        if (offset < 0 || limit < 1) return new ArrayList<>();
        return recordDAO.findRecordsWithLimit(offset, limit);
    }

    public int getCountRecords() {
        LOGGER.info("Getting count of records");
        return recordDAO.getCountRecords();
    }

    public float getAvgRecords(List<Long> ms) {
        LOGGER.info("Getting avg of mark");
        return recordDAO.getAvgRecords(ms);
    }

    public List<Record> findAllRecordsByUserId(Long id) {
        LOGGER.info("Finding all records by user id " + id);
        if (id == null) {
            return new ArrayList<>();
        }
        return recordDAO.findRecordsByUserId(id);
    }

    public List<Record> findAllRecordsTime(Long id, String date, boolean isReady) {
        LOGGER.info("Finding all records with date => " + date);
        if (id == null) {
            return new ArrayList<>();
        }
        return recordDAO.findAllRecordsByDate(id, date, isReady);
    }

    public boolean updateStatus(Long id, Status status) {
        LOGGER.info("Update status to " + status.value());
        if (id == null) {
            return false;
        }
        return recordDAO.updateStatus(id, status);
    }

    public boolean updateTime(Long id, String date) {
        LOGGER.info("Update time to " + date);
        if (id == null) {
            return false;
        }
        return recordDAO.updateTime(id, date);
    }

    public boolean updateMark(Long id, int mark) {
        LOGGER.info("Update mark => " + mark);
        if (id == null) {
            return false;
        }
        return recordDAO.updateMark(id, mark);
    }

}
