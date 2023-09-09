package dev.blackshark.persistance.repository.crud;

import dev.blackshark.persistance.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IProductoCrudRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByNombreIgnoreCase(String nombre);

    Boolean existsByNombre(String nombre);
}
