package org.ormfux.springbootsample.webservice.rest.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "book", description = "A book.")
public class BookDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @ApiModelProperty(value = "Book id.")
    private long id;

    @ApiModelProperty(value = "Book title.")
    private String title;

    @ApiModelProperty(value = "Id of the book's genre.")
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
