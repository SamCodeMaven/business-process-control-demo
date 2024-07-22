package uz.xnarx.businessprocesscontroldemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.xnarx.businessprocesscontroldemo.Entity.Client;
import uz.xnarx.businessprocesscontroldemo.exception.NotFoundException;
import uz.xnarx.businessprocesscontroldemo.payload.ClientDto;
import uz.xnarx.businessprocesscontroldemo.repository.ClientRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional
    public void saveOrUpdateClient(ClientDto clientDto) {
        Client client = new Client();
        if (clientDto.getId() != null) {
            client = clientRepository.findById(clientDto.getId()).orElseThrow(() -> new NotFoundException("Client not found"));
            client.setModifiedDate(LocalDateTime.now());
        }

        client.setName(clientDto.getName());
        client.setEmail(clientDto.getEmail());
        client.setPhone(clientDto.getPhone());
        client.setAddress(clientDto.getAddress());
        client.setTotalDebt(clientDto.getTotalDebt());
        client.setTotalCredit(clientDto.getTotalCredit());
        client.setManagerId(clientDto.getManagerId());
        clientRepository.save(client);
    }

    @Transactional
    public List<ClientDto> getAllClients() {
        List<Client> clients = clientRepository.findAll();
        return clients.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ClientDto> getAllClientsByManagerId(Long managerId) {
        List<Client> clients = clientRepository.findAllByManagerId(managerId);
        return clients.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Transactional
    public List<ClientDto> searchClientByName(String name) {
        List<Client> clients = clientRepository.findAllByNameContainingIgnoreCase(name);
        return clients.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ClientDto mapToDto(Client client) {
        return ClientDto.builder()
                .id(client.getId())
                .Name(client.getName())
                .email(client.getEmail())
                .phone(client.getPhone())
                .address(client.getAddress())
                .createdDate(client.getCreatedDate())
                .modifiedDate(client.getModifiedDate())
                .totalDebt(client.getTotalDebt())
                .totalCredit(client.getTotalCredit())
                .managerId(client.getManagerId())
                .build();
    }


}
