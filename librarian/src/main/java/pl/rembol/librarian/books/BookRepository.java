package pl.rembol.librarian.books;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByAuthor(String author);

    List<Book> findByName(String name);

    Book findByNameAndAuthor(String name, String author);
}
