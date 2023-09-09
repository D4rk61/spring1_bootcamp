package dev.blackshark.domain.service;

import dev.blackshark.persistance.entity.Usuario;
import dev.blackshark.persistance.repository.crud.IUsuarioCrudRepository;
import dev.blackshark.persistance.repository.pagin.IUsuarioPaginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private final IUsuarioCrudRepository usuarioRepository;

    @Autowired
    private final IUsuarioPaginRepository usuarioPaginRepository;

    public UsuarioService(IUsuarioCrudRepository usuarioRepository, IUsuarioPaginRepository usuarioPaginRepository) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioPaginRepository = usuarioPaginRepository;
    }

    public Page<Usuario> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        try {
            return this.usuarioPaginRepository.findAll(pageable);
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<Usuario> getByUsername(String username) {
        try {
            return this.usuarioRepository.findByUsername(username);
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean existsByUsername(String username) {
        try {
            return this.usuarioRepository.existsByUsername(username);
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean existsByEmail(String email) {
        try {
            return this.usuarioRepository.existsByEmail(email);
        } catch (Exception e) {
            throw e;
        }
    }

    public Usuario save(Usuario usuario) {
        try {
            return this.usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw e;
        }
    }
}
