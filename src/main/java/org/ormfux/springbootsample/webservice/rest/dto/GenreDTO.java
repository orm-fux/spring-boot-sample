package org.ormfux.springbootsample.webservice.rest.dto;

import java.io.Serializable;

public class GenreDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long id;

    private String name;

    private String description;

    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
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
}
