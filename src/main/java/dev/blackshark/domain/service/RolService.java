package dev.blackshark.domain.service;

import dev.blackshark.persistance.entity.Rol;
import dev.blackshark.persistance.entity.RolNombre;
import dev.blackshark.persistance.repository.crud.IRolCrudRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
public class RolService {

    @Autowired
    private final IRolCrudRepository rolRepository;

    public RolService(IRolCrudRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public Optional<Rol> getByRolNombre(RolNombre rolNombre) {
        try {
            return rolRepository.findByRolNombre(rolNombre);
        } catch (Exception e) {
            throw e;
        }
    }

    public Rol save(Rol rol) {
        try {
            return rolRepository.save(rol);
        } catch (Exception e) {
            throw e;
        }
    }
}
