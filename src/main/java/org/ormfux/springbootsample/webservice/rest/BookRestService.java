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
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/${restservice.path.root}${restservice.path.book.root}")
@Secured({"ROLE_ADMIN", "ROLE_LIBRARIAN"})
public class BookRestService {
    
    @Autowired
    private BookService bookService;
    
    @Autowired
    private GenreService genreService;

    @RequestMapping(method = RequestMethod.POST, produces = {"application/json"}, consumes = {"application/json"}, path = "${restservice.path.book.create}")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody BookDTO createBook(@RequestBody final BookDTO bookDTO) {
        return toDTO(bookService.createBook(toEntity(bookDTO)));
    }
    
    @RequestMapping(method = RequestMethod.POST, consumes = {"application/json"}, path = "${restservice.path.book.update}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public @ResponseBody BookDTO updateBook(@RequestBody final BookDTO bookDTO,
                                            final HttpServletResponse response) {
        bookService.updateBook(toEntity(bookDTO));
        
        return getBookById(bookDTO.getId(), response);
    }
    
    @RequestMapping(method = RequestMethod.DELETE, path = "${restservice.path.book.delete}/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteBook(@PathVariable("id") final long id) {
        bookService.deleteBook(id);
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<BookDTO> getAllBooks() {
        return toDTOs(bookService.getAll());
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path = "${restservice.path.book.byid}/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody BookDTO getBookById(@PathVariable("id") final Long id, 
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
    public @ResponseBody List<BookDTO> getAllBooksByTitle(@PathVariable("title") final String title, final HttpServletResponse response) {
        return toDTOs(bookService.getAllByTitle(title));
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = {"application/json"}, path= "${restservice.path.book.bygenre}/{genreId}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody List<BookDTO> getAllBooksByGenre(@PathVariable("genreId") final long genreId, final HttpServletResponse response) {
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
