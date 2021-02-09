package com.sakadel.salon.entity;

import java.sql.Time;

public class Record {
    private Long id;
    private Long user_id;
    private Long master_has_service_id;
    private Long status_id;
    private Time time;

    public Record(Long id, Long user_id, Long master_has_service_id, Long status_id, Time time) {
        this.id = id;
        this.user_id = user_id;
        this.master_has_service_id = master_has_service_id;
        this.status_id = status_id;
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getMaster_has_service_id() {
        return master_has_service_id;
    }

    public void setMaster_has_service_id(Long master_has_service_id) {
        this.master_has_service_id = master_has_service_id;
    }

    public Long getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Long status_id) {
        this.status_id = status_id;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }
}
