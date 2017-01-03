package pl.rembol.librarian.borrows;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.clients.Client;

import javax.validation.ValidationException;

@Service
public class BorrowValidator {

    private final BorrowRepository borrowRepository;

    @Autowired
    public BorrowValidator(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public void canBorrow(Client client, Book book) {
        if (book.getAvailable() <= 0) {
            throw new ValidationException("book \"" + book.getName() + "\" is not available");
        }

        if (borrowRepository.getByClientAndBook(client, book).isPresent()) {
            throw new ValidationException("client \"" + client.getName() + "\" already borrowed book \"" + book.getName() + "\"");
        }

        if (borrowRepository.getByClient(client).size() >= 3) {
            throw new ValidationException("client \"" + client.getName() + "\" already borrowed three books");
        }
    }

    public void canReturn(Client client, Book book) {
        if (!borrowRepository.getByClientAndBook(client, book).isPresent()) {
            throw new ValidationException("client \"" + client.getName() +"\" doesn't have book \"" + book.getName() + "\"");
        }
    }
}
