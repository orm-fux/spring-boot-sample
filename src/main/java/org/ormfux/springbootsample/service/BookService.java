package org.ormfux.springbootsample.service;

import java.util.List;

import org.ormfux.springbootsample.crud.BookRepository;
import org.ormfux.springbootsample.domain.Book;
import org.ormfux.springbootsample.domain.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;
    
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book getBook(long id) {
        return bookRepository.getOne(id);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    
    public List<Book> getAll() {
        return bookRepository.findAll(Sort.by(Order.asc("name")));
    }
    
    public List<Book> getAllByTitle(final String name) {
        return bookRepository.findAllByTitleOrderByTitleAsc(name);
    }
    
    public List<Book> getAllByGenre(final Genre genre) {
        return bookRepository.findAllByGenreOrderByTitleAsc(genre);
    }
    
}
