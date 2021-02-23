package com.sakadel.salon.entity;

public class Service {
    private Long id;
    private String name;
    private String description;
    private String nameUkr;
    private String descriptionUkr;

    public Service(){
    }
    public Service(String name, String description) {
        this.name = name;
        this.description = description;
    }
    public Service(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNameUkr() {
        return nameUkr;
    }

    public void setNameUkr(String nameUkr) {
        this.nameUkr = nameUkr;
    }

    public String getDescriptionUkr() {
        return descriptionUkr;
    }

    public void setDescriptionUkr(String descriptionUkr) {
        this.descriptionUkr = descriptionUkr;
    }
}
