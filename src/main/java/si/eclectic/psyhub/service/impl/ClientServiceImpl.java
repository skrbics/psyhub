package si.eclectic.psyhub.service.impl;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import si.eclectic.psyhub.domain.Client;
import si.eclectic.psyhub.repository.ClientRepository;
import si.eclectic.psyhub.service.ClientService;

/**
 * Service Implementation for managing {@link si.eclectic.psyhub.domain.Client}.
 */
@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client save(Client client) {
        log.debug("Request to save Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Client update(Client client) {
        log.debug("Request to update Client : {}", client);
        return clientRepository.save(client);
    }

    @Override
    public Optional<Client> partialUpdate(Client client) {
        log.debug("Request to partially update Client : {}", client);

        return clientRepository
            .findById(client.getId())
            .map(existingClient -> {
                if (client.getFirstName() != null) {
                    existingClient.setFirstName(client.getFirstName());
                }
                if (client.getMiddleName() != null) {
                    existingClient.setMiddleName(client.getMiddleName());
                }
                if (client.getLastName() != null) {
                    existingClient.setLastName(client.getLastName());
                }
                if (client.getEmail() != null) {
                    existingClient.setEmail(client.getEmail());
                }
                if (client.getPhone() != null) {
                    existingClient.setPhone(client.getPhone());
                }

                return existingClient;
            })
            .map(clientRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Client> findAll(Pageable pageable) {
        log.debug("Request to get all Clients");
        return clientRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        clientRepository.deleteById(id);
    }
}
