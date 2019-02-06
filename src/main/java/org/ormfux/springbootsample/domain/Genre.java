package org.ormfux.springbootsample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "genre")
public class Genre {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column
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
