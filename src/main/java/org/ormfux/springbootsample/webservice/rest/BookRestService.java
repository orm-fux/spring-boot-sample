package org.ormfux.springbootsample.webservice.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

import org.ormfux.springbootsample.domain.Book;
import org.ormfux.springbootsample.service.BookService;
import org.ormfux.springbootsample.service.GenreService;
import org.ormfux.springbootsample.webservice.rest.dto.BookDTO;
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
@RequestMapping(value = "/${restservice.path.root}${restservice.path.book.root}")
@Api("REST service for book management.")
public class BookRestService {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.POST, produces = {"application/json"}, consumes = {"application/json"}, path = "${restservice.path.book.create}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Creates a new book.")
    public @ResponseBody BookDTO createBook(@RequestBody 
                                                @ApiParam(type = "book", name = "book", value = "The book data.") 
                                                final BookDTO bookDTO) {
        return toDTO(bookService.createBook(toEntity(bookDTO)));
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, path = "${restservice.path.book.update}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Updates an existing book.")
    public @ResponseBody BookDTO updateBook(@RequestBody 
                                                @ApiParam(type = "book", name = "book", value = "The book data.")  
                                                final BookDTO bookDTO,
                                            final HttpServletResponse response) {
        bookService.updateBook(toEntity(bookDTO));
        
        return getBookById(bookDTO.getId(), response);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "${restservice.path.book.delete}/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiOperation("Deletes a book.")
    public void deleteBook(@PathVariable("id") 
                            @ApiParam("The book id.") final long id) {
        bookService.deleteBook(id);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "All books.", responseContainer = "List")
    public @ResponseBody List<BookDTO> getAllBooks() {
        return toDTOs(bookService.getAll());
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path = "${restservice.path.book.byid}/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation("A specific book.")
    public @ResponseBody BookDTO getBookById(@PathVariable("id") 
                                                @ApiParam("The book id.") final Long id, 
                                             final HttpServletResponse response) {
        try {
            return toDTO(bookService.getBook(id));
        } catch (final EntityNotFoundException e) {
            response.setStatus(HttpStatus.NO_CONTENT.value());
            return null;
        }
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path= "${restservice.path.book.bytitle}/{name}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "All books where the title contains a provided phrase.", responseContainer = "List")
    public @ResponseBody List<BookDTO> getAllBooksByTitle(@PathVariable("title") 
                                                            @ApiParam("The phrase in the title") final String title) {
        return toDTOs(bookService.getAllByTitle(title));
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path= "${restservice.path.book.bygenre}/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "All books of a genre.", responseContainer = "List")
    public @ResponseBody List<BookDTO> getAllBooksByGenre(@PathVariable("genreId") 
                                                            @ApiParam("The genre id") final long genreId) {
        return toDTOs(bookService.getAllByGenre(genreService.getGenre(genreId)));
    }
    
    private Book toEntity(final BookDTO bookDTO) {
        final Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setGenre(genreService.getGenre(bookDTO.getGenreId()));
        book.setId(bookDTO.getId());
        
        return book;
    }
    
    private List<BookDTO> toDTOs(final List<Book> books) {
        return books.stream()
                    .map(book -> toDTO(book))
                    .collect(Collectors.toList());
    }
    
    private BookDTO toDTO(final Book book) {
        final BookDTO bookDTO = new BookDTO();
        bookDTO.setTitle(book.getTitle());
        bookDTO.setGenreId(book.getGenre().getId());
        bookDTO.setId(book.getId());
        
        return bookDTO;
    }
    
}
