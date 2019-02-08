package org.ormfux.springbootsample.service;

import java.util.List;

import org.ormfux.springbootsample.crud.GenreRepository;
import org.ormfux.springbootsample.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class GenreService {
    
    @Autowired
    private GenreRepository genreRepository;
    
    public Genre createGenre(Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre getGenre(long id) {
        return genreRepository.findById(id);
    }

    public void updateGenre(Genre genre) {
        genreRepository.save(genre);
    }

    public void deleteGenre(Long id) {
        genreRepository.deleteById(id);
    }
    
    public List<Genre> getAll() {
        return genreRepository.findAll(Sort.by(Order.asc("name")));
    }
    
    public Genre getByName(final String name) {
        return genreRepository.findByName(name);
    }
}
