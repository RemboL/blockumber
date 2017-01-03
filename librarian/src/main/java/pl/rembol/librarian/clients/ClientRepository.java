package pl.rembol.librarian.clients;

import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Long> {
    Client findByName(String name);
}
