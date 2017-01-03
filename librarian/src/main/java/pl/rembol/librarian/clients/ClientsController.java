package pl.rembol.librarian.clients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/clients")
public class ClientsController {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientsController(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Client> getAllClients() {
        return StreamSupport.stream(clientRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.POST)
    public Client createClient(@RequestBody CreateClientRequest createClientRequest) {
        return clientRepository.save(new Client(createClientRequest.getName()));
    }


    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Client getClient(@PathVariable(value = "id") Long id) {
        return clientRepository.findOne(id);
    }

}
