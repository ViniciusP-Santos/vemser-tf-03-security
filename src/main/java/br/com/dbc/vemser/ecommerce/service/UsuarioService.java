package br.com.dbc.vemser.ecommerce.service;

import br.com.dbc.vemser.ecommerce.dto.usuario.LoginDTO;
import br.com.dbc.vemser.ecommerce.entity.CargoEntity;
import br.com.dbc.vemser.ecommerce.entity.UsuarioEntity;
import br.com.dbc.vemser.ecommerce.exceptions.RegraDeNegocioException;
import br.com.dbc.vemser.ecommerce.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

    public Optional<UsuarioEntity> findByLoginAndSenha(String login, String senha) {
        return usuarioRepository.findByLoginAndSenha(login, senha);
    }

    public Optional<UsuarioEntity> findByLogin(String login) {
        return usuarioRepository.findByLogin(login);
    }

    public Integer getIdLoggedUser() {
        Integer findUserId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return findUserId;
    }

    public UsuarioEntity getLoggedUser() throws RegraDeNegocioException {
        return findById(getIdLoggedUser());
    }

    public UsuarioEntity findById(Integer idUsuario) throws RegraDeNegocioException {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new RegraDeNegocioException("Usuário não encontrado!"));
    }

    public LoginDTO cadastro(LoginDTO user, Integer role) throws RegraDeNegocioException {
        if (role == null){
            role = 2;
        }
        if (role < 1 || role > 3){
            throw new RegraDeNegocioException("Cargo não existente");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String senhaCript = bCryptPasswordEncoder.encode(user.getSenha());

        UsuarioEntity novoUser = new UsuarioEntity();
        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(role);
        novoUser.getCargos().add(cargo);
        novoUser.setSenha(senhaCript);
        novoUser.setLogin(user.getLogin());
        usuarioRepository.save(novoUser);
        return user;
    }
}