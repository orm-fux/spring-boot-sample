package org.ormfux.springbootsample.webservice.rest;

import org.apache.camel.Converter;
import org.apache.camel.TypeConverters;
import org.ormfux.springbootsample.domain.Book;
import org.ormfux.springbootsample.domain.Genre;
import org.ormfux.springbootsample.service.GenreService;
import org.ormfux.springbootsample.webservice.rest.dto.BookDTO;
import org.ormfux.springbootsample.webservice.rest.dto.GenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityAndDTOConverters implements TypeConverters {
    
    @Autowired
    private GenreService genreService;
    
    @Converter
    public Book toEntity(final BookDTO bookDTO) {
        final Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setGenre(genreService.getGenre(bookDTO.getGenreId()));
        book.setId(bookDTO.getId());
        
        return book;
    }
    
    @Converter
    public BookDTO toDTO(final Book book) {
        final BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenreId(book.getGenre().getId());
        bookDTO.setId(book.getId());
        
        return bookDTO;
    }
    
    @Converter
    public Genre toEntity(final GenreDTO genreDTO) {
        final Genre genre = new Genre();
        genre.setDescription(genreDTO.getDescription());
        genre.setName(genreDTO.getName());
        genre.setId(genreDTO.getId());
        
        return genre;
    }
    
    @Converter
    public GenreDTO toDTO(final Genre genre) {
        final GenreDTO genreDTO = new GenreDTO();
        genreDTO.setDescription(genre.getDescription());
        genreDTO.setName(genre.getName());
        genreDTO.setId(genre.getId());
        
        return genreDTO;
    }
}
