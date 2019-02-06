package org.ormfux.springbootsample.webservice.rest.dto;

import java.io.Serializable;

public class BookDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    private long id;

    private String title;

    private long genreId;

    
    public long getId() {
        return id;
    }

    
    public void setId(long id) {
        this.id = id;
    }

    
    public String getTitle() {
        return title;
    }

    
    public void setTitle(String title) {
        this.title = title;
    }

    
    public long getGenreId() {
        return genreId;
    }

    
    public void setGenreId(long genreId) {
        this.genreId = genreId;
    }

}
