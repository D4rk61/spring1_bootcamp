package dev.blackshark.persistance.repository.pagin;

import dev.blackshark.persistance.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductoPaginRepository extends ListPagingAndSortingRepository<Producto, Long> {

    Page<Producto> findAll(Pageable pageable);
}
