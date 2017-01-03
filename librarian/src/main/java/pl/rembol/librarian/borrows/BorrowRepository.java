package pl.rembol.librarian.borrows;

import org.springframework.data.repository.CrudRepository;
import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.clients.Client;

import java.util.List;
import java.util.Optional;

public interface BorrowRepository  extends CrudRepository<Borrow, Long> {

    List<Borrow> getByClient(Client client);

    List<Borrow> getByBook(Book book);

    Optional<Borrow> getByClientAndBook(Client client, Book book);
}
