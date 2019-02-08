package org.ormfux.springbootsample.crud;

import java.util.List;

import org.ormfux.springbootsample.domain.Book;
import org.ormfux.springbootsample.domain.Genre;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

//Using automatic query generation:
//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories.query-methods.details
public interface BookRepository extends JpaRepository<Book, Long> {
    
    @Query("select book from Book book where book.title like concat('%', :title, '%') order by book.title asc")
    public List<Book> findAllByTitleOrderByTitleAsc(final String title);
    
    public List<Book> findAllByGenreOrderByTitleAsc(final Genre genre);
    
    @EntityGraph(type = EntityGraph.EntityGraphType.LOAD)
    public Book findById(final long id);
    
}
