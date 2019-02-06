package org.ormfux.springbootsample.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "book")
public class Book {
    
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "genre")
    private Genre genre;

    
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

    
    public Genre getGenre() {
        return genre;
    }

    
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

}
