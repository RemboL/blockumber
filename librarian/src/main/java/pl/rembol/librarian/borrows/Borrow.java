package pl.rembol.librarian.borrows;

import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.clients.Client;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    private Borrow() {
    }

    public Borrow(Book book, Client client) {
        this.book = book;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public Book getBook() {
        return book;
    }

    public Client getClient() {
        return client;
    }
}
