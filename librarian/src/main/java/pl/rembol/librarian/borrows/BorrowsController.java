package pl.rembol.librarian.borrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.books.BookRepository;
import pl.rembol.librarian.clients.Client;
import pl.rembol.librarian.clients.ClientRepository;

import javax.validation.ValidationException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/borrows")
public class BorrowsController {

    private static final Logger logger = LoggerFactory.getLogger(BorrowsController.class);

    private final ClientRepository clientRepository;

    private final BookRepository bookRepository;

    private final BorrowRepository borrowRepository;

    private final BorrowValidator borrowValidator;

    private final BorrowService borrowService;

    @Autowired
    public BorrowsController(
            ClientRepository clientRepository,
            BookRepository bookRepository,
            BorrowRepository borrowRepository,
            BorrowValidator borrowValidator,
            BorrowService borrowService) {
        this.clientRepository = clientRepository;
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
        this.borrowValidator = borrowValidator;
        this.borrowService = borrowService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Borrow> getAll() {
        return StreamSupport.stream(borrowRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/byClient/{clientName}", method = RequestMethod.GET)
    public List<Borrow> getByClient(@PathVariable("clientName") String clientName) {
        return borrowRepository.getByClient(clientRepository.findByName(clientName));
    }

    @RequestMapping(path = "/byBook/{bookName}", method = RequestMethod.GET)
    public List<Borrow> getByBook(@PathVariable("bookName") String bookName) {
        return borrowRepository.getByBook(bookRepository.findByName(bookName).get(0));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void borrow(@RequestBody BorrowRequest borrowRequest) {
        Book book = bookRepository.findByName(borrowRequest.getBookName()).get(0);
        Client client = clientRepository.findByName(borrowRequest.getClientName());

        borrowValidator.canBorrow(client, book);
        borrowService.borrow(client, book);
    }

    @RequestMapping(path = "/return", method = RequestMethod.POST)
    public void returnBook(@RequestBody BorrowRequest borrowRequest) {
        Book book = bookRepository.findByName(borrowRequest.getBookName()).get(0);
        Client client = clientRepository.findByName(borrowRequest.getClientName());

        borrowValidator.canReturn(client, book);
        borrowService.returnBook(client, book);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void clearAll() {
        borrowService.clearAll();
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> validationException(ValidationException e) {
        logger.error("caught validation error: "+e.getLocalizedMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
