package pl.rembol.librarian.borrows;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.books.BookRepository;
import pl.rembol.librarian.clients.Client;

@Service
public class BorrowService {

    private static final Logger logger = LoggerFactory.getLogger(BorrowService.class);

    private final BookRepository bookRepository;
    private final BorrowRepository borrowRepository;

    @Autowired
    public BorrowService(BookRepository bookRepository, BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;
        this.borrowRepository = borrowRepository;
    }

    public void returnBook(Client client, Book book) {
        logger.info("Client \""+client.getName()+"\" returns \""+book.getName()+"\"");
        borrowRepository.getByClientAndBook(client, book).ifPresent(borrow -> {
            book.returned();
            bookRepository.save(book);
            borrowRepository.delete(borrow);
        });
    }

    public void borrow(Client client, Book book) {
        logger.info("Client \""+client.getName()+"\" borrows \""+book.getName()+"\"");
        book.borrowed();
        bookRepository.save(book);
        borrowRepository.save(new Borrow(book, client));
    }
}
