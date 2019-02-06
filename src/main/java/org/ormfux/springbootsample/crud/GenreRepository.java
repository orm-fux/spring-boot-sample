package org.ormfux.springbootsample.crud;

import org.ormfux.springbootsample.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

//Using automatic query generation:
//https://docs.spring.io/spring-data/data-jpa/docs/current/reference/html/#repositories.query-methods.details
public interface GenreRepository extends JpaRepository<Genre, Long> {
    
    public Genre findByName(String name);
    
}
