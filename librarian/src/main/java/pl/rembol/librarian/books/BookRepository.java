package pl.rembol.librarian.books;

import org.springframework.data.repository.CrudRepository;
import pl.rembol.librarian.clients.Client;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    Book findByName(String name);
}
