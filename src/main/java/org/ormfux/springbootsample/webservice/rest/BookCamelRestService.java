package org.ormfux.springbootsample.webservice.rest;

import javax.annotation.Priority;

import org.apache.camel.model.rest.RestParamType;
import org.ormfux.springbootsample.webservice.rest.dto.BookDTO;
import org.springframework.stereotype.Component;

@Component
@Priority(1000)
public class BookCamelRestService extends AbstractCamelRestConfiguration {
    
    @Override
    public void configure() throws Exception {
        rest(config("restservice.path.book.root"))
                      .description("REST service for book management")
                                                   
                      .get(config("camel.rest.service.book.all"))
                            .description("List all books.")
                            .outType(BookDTO[].class) //has no f-ing effect
                            .route()
                            .to("bean:bookService?method=getAll")
                            .process(exchange -> convertListTo(exchange, BookDTO.class))
                            .endRest()
                            
                      .post(config("camel.rest.service.book.create"))
                              .param().name("body")
                                      .type(RestParamType.body)
                                      .description("The book to create.")
                                      .endParam()
                              .type(BookDTO.class) //request body type
                              .outType(BookDTO.class) //has no f-ing effect
                              .route()
                              .to("bean:bookService?method=createBook")
                              .process(exchange -> convertTo(exchange, BookDTO.class))
                              .endRest()
                      
                      .get(config("camel.rest.service.book.byid") + "/{id}")
                               .param().name("id")
                                       .type(RestParamType.path)
                                       .description("The book id.")
                                       .endParam()
                               .outType(BookDTO.class) //has no f-ing effect
                               .route()
                               .to("bean:bookService?method=getBook(${header.id})")
                               .process(exchange -> convertTo(exchange, BookDTO.class))
                               .endRest()
                       
                       .get(config("camel.rest.service.book.bytitle") + "/{title}")
                                .param().name("title")
                                        .type(RestParamType.path)
                                        .description("The book title.")
                                        .endParam()
                                .outType(BookDTO.class) //has no f-ing effect
                                .route()
                                .to("bean:bookService?method=getAllByTitle(${header.title})")
                                .process(exchange -> convertListTo(exchange, BookDTO.class))
                                .endRest()
                                
                        .get(config("camel.rest.service.book.bygenre") + "/{genreId}")
                                 .param().name("genreId")
                                         .type(RestParamType.path)
                                         .description("The book title.")
                                         .endParam()
                                 .outType(BookDTO.class) //has no f-ing effect
                                 .route()
                                 .to("bean:bookService?method=getAllByGenre(${header.genreId})")
                                 .process(exchange -> convertListTo(exchange, BookDTO.class))
                                 .endRest()
                       
                       .post(config("camel.rest.service.book.delete") + "/{id}")
                                .param().name("id")
                                        .type(RestParamType.path)
                                        .description("The book id.")
                                        .endParam()
                                .to("bean:bookService?method=deleteBook(${header.id})")
                                           
                       .post(config("camel.rest.service.book.update"))
                                 .param().name("body")
                                         .type(RestParamType.body)
                                         .description("The book to create.")
                                         .endParam()
                                 .type(BookDTO.class) //request body type
                                 .route()
                                 .to("bean:bookService?method=updateBook")
                                 .endRest();
    }
    
}
