package org.ormfux.springbootsample.webservice.rest.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "genre", description = "A genre.")
public class GenreDTO implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "The id of the genre.", required = false, example = "0", allowEmptyValue = true)
    private long id;

    @ApiModelProperty("The genre name.")
    private String name;

    @ApiModelProperty("The genre description.")
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
