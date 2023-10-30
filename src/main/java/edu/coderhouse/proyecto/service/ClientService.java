package edu.coderhouse.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.coderhouse.proyecto.Repository.ClientRepository;
import edu.coderhouse.proyecto.entity.Client;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public void delete(int id) {
        clientRepository.deleteById(id);
    }

    public Optional<Client> findClientById(int id) {
        return clientRepository.findById(id);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
