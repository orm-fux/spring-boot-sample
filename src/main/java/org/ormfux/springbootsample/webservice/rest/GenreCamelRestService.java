package org.ormfux.springbootsample.webservice.rest;

import javax.annotation.Priority;

import org.apache.camel.model.rest.RestParamType;
import org.ormfux.springbootsample.webservice.rest.dto.GenreDTO;
import org.springframework.stereotype.Component;

@Component
@Priority(1000)
public class GenreCamelRestService extends AbstractCamelRestConfiguration {
    
    @Override
    public void configure() throws Exception {
        rest(config("restservice.path.genre.root"))
                      .description("REST service for genre management")
                                                   
                      .get(config("camel.rest.service.genre.all"))
                            .description("List all genres.")
                            .outType(GenreDTO[].class) //has no f-ing effect
                            .route()
                            .to("bean:genreService?method=getAll")
                            .process(exchange -> convertListTo(exchange, GenreDTO.class))
                            .endRest()
                            
                      .post(config("camel.rest.service.genre.create"))
                              .param().name("body")
                                      .type(RestParamType.body)
                                      .description("The genre to create.")
                                      .endParam()
                              .type(GenreDTO.class) //request body type
                              .outType(GenreDTO.class) //has no f-ing effect
                              .route()
                              .to("bean:genreService?method=createGenre")
                              .process(exchange -> convertTo(exchange, GenreDTO.class))
                              .endRest()
                      
                      .get(config("camel.rest.service.genre.byid") + "/{id}")
                               .param().name("id")
                                       .type(RestParamType.path)
                                       .description("The genre id.")
                                       .endParam()
                               .outType(GenreDTO.class) //has no f-ing effect
                               .route()
                               .to("bean:genreService?method=getGenre(${header.id})")
                               .process(exchange -> convertTo(exchange, GenreDTO.class))
                               .endRest()
                       
                       .get(config("camel.rest.service.genre.byname") + "/{name}")
                                .param().name("name")
                                        .type(RestParamType.path)
                                        .description("The genre name.")
                                        .endParam()
                                .outType(GenreDTO.class) //has no f-ing effect
                                .route()
                                .to("bean:genreService?method=getByName(${header.name})")
                                .process(exchange -> convertTo(exchange, GenreDTO.class))
                                .endRest()
                       
                       .post(config("camel.rest.service.genre.delete") + "/{id}")
                                .param().name("id")
                                        .type(RestParamType.path)
                                        .description("The genre id.")
                                        .endParam()
                                .to("bean:genreService?method=deleteGenre(${header.id})")
                                           
                       .post(config("camel.rest.service.genre.update"))
                                 .param().name("body")
                                         .type(RestParamType.body)
                                         .description("The genre to create.")
                                         .endParam()
                                 .type(GenreDTO.class) //request body type
                                 .route()
                                 .to("bean:genreService?method=updateGenre")
                                 .endRest();
    }
    
}
