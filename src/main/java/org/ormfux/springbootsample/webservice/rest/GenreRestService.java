package org.ormfux.springbootsample.webservice.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.ormfux.springbootsample.domain.Genre;
import org.ormfux.springbootsample.service.GenreService;
import org.ormfux.springbootsample.webservice.rest.dto.GenreDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping(value = "${restservice.path.root}${restservice.path.genre.root}")
@Api("REST access for bok genre management.")
public class GenreRestService {
    
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.POST, produces = {"application/json"}, consumes = {"application/json"}, path = "${restservice.path.genre.create}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a new genre.")
    public @ResponseBody GenreDTO createGenre(@RequestBody 
                                              @ApiParam(name = "genre", value = "genre", required = true) final GenreDTO genreDTO) {
        return toDTO(genreService.createGenre(toEntity(genreDTO)));
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, path = "${restservice.path.genre.update}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Creates a new genre.")
    public @ResponseBody GenreDTO updateGenre(@RequestBody
                                                @ApiParam(name = "genre", value = "genre", required = true) final GenreDTO genreDTO,
                                              final HttpServletResponse response) {
        genreService.updateGenre(toEntity(genreDTO));
        
        return getGenreById(genreDTO.getId(), response);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "${restservice.path.genre.delete}/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Deletes an existing genre.")
    public void deleteGenre(@PathVariable("id") 
                                @ApiParam(value = "The id of the genre to delete.", required = true) final long id) {
        genreService.deleteGenre(id);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "All available genres.", responseContainer = "List")
    public @ResponseBody List<GenreDTO> getAllGenres() {
        return genreService.getAll().stream().map(genre -> toDTO(genre)).collect(Collectors.toList());
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path = "${restservice.path.genre.byid}/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("A specific genre for the genre id.")
    public @ResponseBody GenreDTO getGenreById(@PathVariable("id") 
                                                   @ApiParam(value = "The id of the genre.", required = true) final Long id, 
                                               final HttpServletResponse response) {
        try {
            return toDTO(genreService.getGenre(id));
        } catch (final EntityNotFoundException e) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path= "${restservice.path.genre.byname}/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("Finds the genre with the provided name.")
    public @ResponseBody GenreDTO getGenreByName(@PathVariable("name") 
                                                     @ApiParam(value = "The genre name", required = true) final String name, 
                                                 final HttpServletResponse response) {
        final Genre genre = genreService.getByName(name);
        
        if (genre == null) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        } else {
            return toDTO(genre);
        }
    }
    
    private Genre toEntity(final GenreDTO genreDTO) {
        final Genre genre = new Genre();
        genre.setDescription(genreDTO.getDescription());
        genre.setName(genreDTO.getName());
        genre.setId(genreDTO.getId());
        
        return genre;
    }
    
    private GenreDTO toDTO(final Genre genre) {
        final GenreDTO genreDTO = new GenreDTO();
        genreDTO.setDescription(genre.getDescription());
        genreDTO.setName(genre.getName());
        genreDTO.setId(genre.getId());
        
        return genreDTO;
    }
    
}
