package pl.rembol.librarian.books;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BookRepository bookRepository;

    @Autowired
    public BooksController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Book> getAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Book createBook(@RequestBody CreateBookRequest createBookRequest) {
        return bookRepository.save(new Book(createBookRequest.getName(), createBookRequest.getAuthor()));
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Book getBook(@PathVariable(value = "id") Long id) {
        return bookRepository.findOne(id);
    }

    @RequestMapping(path = "/byAuthor/{author}", method = RequestMethod.GET)
    public List<Book> getByAuthor(@PathVariable(value = "author") String author) {
        return bookRepository.findByAuthor(author);
    }


}
