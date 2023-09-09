package dev.blackshark.domain.service;

import dev.blackshark.persistance.entity.Producto;
import dev.blackshark.persistance.repository.crud.IProductoCrudRepository;
import dev.blackshark.persistance.repository.pagin.IProductoPaginRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service    @Transactional
public class ProductoService {

    @Autowired
    private final IProductoPaginRepository productoPaginRepository;
    @Autowired
    private final IProductoCrudRepository productoRepository;

    public ProductoService(IProductoPaginRepository productoPaginRepository, IProductoCrudRepository productoRepository) {
        this.productoPaginRepository = productoPaginRepository;
        this.productoRepository = productoRepository;
    }

    public Page<Producto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        try {
            return this.productoPaginRepository.findAll(pageable);
        } catch (Exception e) {
            throw e;
        }
    }

    public Producto getById(Long id) {

        if(!existById(id)) {
            throw new RuntimeException("No existe el producto con el id: " + id);
        }

        try {
            return this.productoRepository.findById(id).get();
        } catch (Exception e) {
            throw e;
        }
    }

    public Optional<Producto> getByNombre(String nombre) {
        try {
            return this.productoRepository.findByNombreIgnoreCase(nombre);
        } catch (Exception e) {
            throw e;
        }
    }

    public Producto save(Producto producto) {
        try {
            return this.productoRepository.save(producto);
        } catch (Exception e) {
            throw e;
        }
    }
    public Producto update(Long id, Producto producto) {
        if(!existById(id)) {
            throw new RuntimeException("No existe el producto con el id: " + id);
        }

        try {
            producto.setId(id);
            return this.productoRepository.save(producto);
        } catch (Exception e) {
            throw e;
        }
    }


    public void deleteByid(Long id) {
        if(!existById(id)) {
            throw new RuntimeException("No existe el producto con el id: " + id);
        }

        try {
            this.productoRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    public Boolean existById(Long id) {
        try {
            return this.productoRepository.existsById(id);
        } catch (Exception e) {
            throw e;
        }
    }
    public Boolean existeByNombre(String nombre) {
        try {
            return this.productoRepository.existsByNombre(nombre);
        } catch (Exception e) {
            throw e;
        }
    }
}
