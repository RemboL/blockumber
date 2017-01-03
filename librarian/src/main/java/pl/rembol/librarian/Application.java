package pl.rembol.librarian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import pl.rembol.librarian.books.Book;
import pl.rembol.librarian.books.BookRepository;
import pl.rembol.librarian.borrows.BorrowService;
import pl.rembol.librarian.clients.Client;
import pl.rembol.librarian.clients.ClientRepository;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        logger.info("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            logger.info(beanName);
        }

        initDemoData(ctx);
    }

    private static void initDemoData(ApplicationContext ctx) {
        initDemoBooks(ctx.getBean(BookRepository.class));
        initDemoClients(ctx.getBean(ClientRepository.class));
        initDemoBorrows(ctx.getBean(BorrowService.class),
                ctx.getBean(ClientRepository.class),
                ctx.getBean(BookRepository.class));
    }

    private static void initDemoBooks(BookRepository bookRepository) {
        bookRepository.save(new Book("On Basilisk Station", "David Weber"));
        bookRepository.save(new Book("The Honor of the Queen", "David Weber"));
        bookRepository.save(new Book("The Short Victorious War", "David Weber"));
        bookRepository.save(new Book("Field of Dishonor", "David Weber"));
        bookRepository.save(new Book("Flag in Exile", "David Weber"));
        bookRepository.save(new Book("Honor Among Enemies", "David Weber"));
        bookRepository.save(new Book("In Enemy Hands", "David Weber"));
        bookRepository.save(new Book("Echoes of Honor", "David Weber"));
        bookRepository.save(new Book("Ashes of Victory", "David Weber"));
        bookRepository.save(new Book("War of Honor", "David Weber"));
        bookRepository.save(new Book("At All Costs", "David Weber"));
        bookRepository.save(new Book("Mission of Honor", "David Weber"));
        bookRepository.save(new Book("A Rising Thunder", "David Weber"));
        bookRepository.save(new Book("Dune", "Frank Herbert"));
        bookRepository.save(new Book("Dune Messiah", "Frank Herbert"));
        bookRepository.save(new Book("Children of Dune", "Frank Herbert"));
        bookRepository.save(new Book("God Emperor of Dune", "Frank Herbert"));
        bookRepository.save(new Book("Heretics of Dune", "Frank Herbert"));
        bookRepository.save(new Book("Chapterhouse: Dune", "Frank Herbert"));
        bookRepository.save(new Book("Usagi Yojimbo, vol. 1", "Stan Sakai"));
        bookRepository.save(new Book("Usagi Yojimbo, vol. 2", "Stan Sakai"));
    }

    private static void initDemoClients(ClientRepository clientRepository) {
        clientRepository.save(new Client("Ann"));
        clientRepository.save(new Client("Bill"));
        clientRepository.save(new Client("Charlie"));
    }

    private static void initDemoBorrows(BorrowService borrowService,
                                        ClientRepository clientRepository,
                                        BookRepository bookRepository) {
        borrowService.borrow(
                clientRepository.findByName("Ann"),
                bookRepository.findByName("On Basilisk Station")
        );
        borrowService.borrow(
                clientRepository.findByName("Ann"),
                bookRepository.findByName("The Honor of the Queen")
        );
        borrowService.borrow(
                clientRepository.findByName("Bill"),
                bookRepository.findByName("War of Honor")
        );
        borrowService.borrow(
                clientRepository.findByName("Bill"),
                bookRepository.findByName("Children of Dune")
        );
        borrowService.borrow(
                clientRepository.findByName("Bill"),
                bookRepository.findByName("God Emperor of Dune")
        );
        borrowService.borrow(
                clientRepository.findByName("Charlie"),
                bookRepository.findByName("At All Costs")
        );
        borrowService.borrow(
                clientRepository.findByName("Charlie"),
                bookRepository.findByName("Usagi Yojimbo, vol. 1")
        );
    }
}
