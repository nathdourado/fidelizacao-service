package br.com.bakeflow.bakeflow.service;

import br.com.bakeflow.bakeflow.model.Cliente;
import br.com.bakeflow.bakeflow.repository.ClienteRepository;
import br.com.bakeflow.bakeflow.repository.EnderecoRepository;
import br.com.bakeflow.bakeflow.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteService {

    private final ClienteRepository repository;
    private final EnderecoRepository enderecoRepository;
    private final PedidoRepository pedidoRepository;

    public ClienteService(ClienteRepository repository,
                          EnderecoRepository enderecoRepository,
                          PedidoRepository pedidoRepository) {
        this.repository = repository;
        this.enderecoRepository = enderecoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    public List<Cliente> findAll() {
        return repository.findAll();
    }

    public Cliente findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Cliente cliente) {


        if (cliente.getIdCliente() != null) {
            Cliente clienteBD = repository.findById(cliente.getIdCliente()).orElse(null);

            if (clienteBD != null) {

                cliente.getEndereco().setId(
                        clienteBD.getEndereco().getId()
                );
            }
        }

        var enderecoSalvo = enderecoRepository.save(cliente.getEndereco());
        cliente.setEndereco(enderecoSalvo);

        repository.save(cliente);
    }




    @Transactional
    public void excluir(Long id) {

        Cliente cliente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        if (pedidoRepository.existsByClienteIdCliente(id)) {
            throw new RuntimeException("Cliente possui pedidos e não pode ser excluído.");
        }


        Long idEndereco = cliente.getEndereco().getId();

        repository.delete(cliente);
        enderecoRepository.deleteById(idEndereco);
    }

}