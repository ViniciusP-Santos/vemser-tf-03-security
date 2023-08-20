package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteCreateDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClienteDadosCompletosDTO;
import br.com.dbc.vemser.ecommerce.dto.cliente.ClientePaginadoDTO;
import br.com.dbc.vemser.ecommerce.dto.endereco.EnderecoDTO;
import br.com.dbc.vemser.ecommerce.dto.pedido.PedidoDTO;
import br.com.dbc.vemser.ecommerce.entity.ClienteEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.ClienteRepository;
import br.com.dbc.vemser.ecommerce.utils.ConverterEnderecoParaDTOutil;
import br.com.dbc.vemser.ecommerce.utils.ConverterPedidoParaDTOutil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClienteService {
    private final ObjectMapper objectMapper;
    private final ClienteRepository clienteRepository;

    private final ConverterEnderecoParaDTOutil converterEnderecoParaDTOutil;
    private final ConverterPedidoParaDTOutil converterPedidoParaDTOutil;

    public Map<String, String> validarNovoCliente(ClienteCreateDTO clienteCreateDTO) {
        Map<String, String> existe = new HashMap<>();
        if (clienteRepository.existsClienteEntitieByEmail(clienteCreateDTO.getEmail())) {
            existe.put("email", "já cadastrado");
        }
        if (clienteRepository.existsClienteEntitieByCpf(clienteCreateDTO.getCpf())) {
            existe.put("cpf", "já cadastrado");
        }
        if (clienteRepository.existsClienteEntitieByTelefone(clienteCreateDTO.getTelefone())) {
            existe.put("telefone", "já cadastrado");
        }

        return existe;
    }

    public ClienteDTO save(ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        Map<String, String> campo = validarNovoCliente(clienteCreateDTO);

        if (campo.size() != 0) {
            throw new RegraDeNegocioException(campo);
        }

        return convertToDto(clienteRepository.save(convertToEntity(clienteCreateDTO)));
    }

    public List<ClienteDadosCompletosDTO> listarClientesComTodosOsDados() {

        return clienteRepository.findAll()
                .stream().map(this::converterClienteParaDTO).toList();

    }

    public List<ClienteDTO> findAll(Integer idCliente) {
        return clienteRepository.buscarTodosOptionalId(idCliente)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public Page<ClientePaginadoDTO> clientePaginado(Pageable pageable) {

        return clienteRepository.buscarTodosClientesPaginados(pageable);
    }

    public ClienteDTO getByid(Integer idCliente) throws RegraDeNegocioException {
        ClienteDTO clienteDTO = convertToDto(findById(idCliente));
        return clienteDTO;
    }

    public ClienteDTO update(Integer idCliente, ClienteCreateDTO clienteCreateDTO) throws RegraDeNegocioException {
        ClienteEntity findedClient = findById(idCliente);
        findedClient.setCpf(clienteCreateDTO.getCpf());
        findedClient.setNome(clienteCreateDTO.getNome());
        findedClient.setTelefone(clienteCreateDTO.getTelefone());
        findedClient.setEmail(clienteCreateDTO.getEmail());
        ClienteDTO updatedClient = convertToDto(clienteRepository.save(findedClient));
        return updatedClient;
    }

    public void delete(Integer idCliente) {
        ClienteEntity clienteEntity = clienteRepository.getById(idCliente);
        clienteRepository.delete(clienteEntity);

    }


    //metodos auxiliares
    public ClienteEntity findById(Integer idcliente) throws RegraDeNegocioException {
        return clienteRepository.findById(idcliente).orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado"));
    }

    public ClienteDTO convertToDto(ClienteEntity clienteEntity) {
        return objectMapper.convertValue(clienteEntity, ClienteDTO.class);
    }

    public ClienteEntity convertToEntity(ClienteCreateDTO clienteCreateDTO) {
        return objectMapper.convertValue(clienteCreateDTO, ClienteEntity.class);
    }

    private ClienteDadosCompletosDTO converterClienteParaDTO(ClienteEntity clienteEntity) {

        Set<EnderecoDTO> enderecoDTO = clienteEntity.getEnderecoEntities()
                .stream().map(converterEnderecoParaDTOutil::converterByEnderecoDTO)
                .collect(Collectors.toSet());

        Set<PedidoDTO> pedidoDTO = clienteEntity.getPedidoEntities()
                .stream().map(c -> converterPedidoParaDTOutil.converterPedidooParaDTO(c))
                .collect(Collectors.toSet());


        ClienteDadosCompletosDTO clienteDadosCompletosDTO =
                objectMapper.convertValue(clienteEntity, ClienteDadosCompletosDTO.class);

        clienteDadosCompletosDTO.setEnderecoEntities(enderecoDTO);
        clienteDadosCompletosDTO.setPedidoEntities(pedidoDTO);

        return clienteDadosCompletosDTO;
    }
}