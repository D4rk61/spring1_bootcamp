package dev.blackshark.persistance.repository.pagin;

import dev.blackshark.persistance.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUsuarioPaginRepository extends ListPagingAndSortingRepository<Usuario, Long> {

    Page<Usuario> findAll(Pageable pageable);
}
