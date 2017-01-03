package pl.rembol.librarian.borrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.books.BookRepository;
import pl.rembol.librarian.clients.Client;
import pl.rembol.librarian.clients.ClientRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/borrows")
public class BorrowsController {

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

    @RequestMapping(path = "/byClient/{clientId}", method = RequestMethod.GET)
    public List<Borrow> getByClient(@PathVariable("clientId") Long clientId) {
        return borrowRepository.getByClient(clientRepository.findOne(clientId));
    }

    @RequestMapping(path = "/byBook/{bookId}", method = RequestMethod.GET)
    public List<Borrow> getByBook(@PathVariable("bookId") Long bookId) {
        return borrowRepository.getByBook(bookRepository.findOne(bookId));
    }

    @RequestMapping(method = RequestMethod.POST)
    public void borrow(@RequestBody BorrowRequest borrowRequest) {
        Book book = bookRepository.findByName(borrowRequest.getBookName());
        Client client = clientRepository.findByName(borrowRequest.getClientName());

        borrowValidator.canBorrow(client, book);
        borrowService.borrow(client, book);
    }

    @RequestMapping(path = "/return", method = RequestMethod.POST)
    public void returnBook(@RequestBody BorrowRequest borrowRequest) {
        Book book = bookRepository.findByName(borrowRequest.getBookName());
        Client client = clientRepository.findByName(borrowRequest.getClientName());

        borrowValidator.canReturn(client, book);
        borrowService.returnBook(client, book);
    }
}
