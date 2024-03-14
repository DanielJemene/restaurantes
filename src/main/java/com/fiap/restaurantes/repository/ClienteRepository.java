package com.fiap.restaurantes.repository;

import com.fiap.restaurantes.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c ORDER BY c.nome ASC")
    Page<Cliente> obterTodosClientes(Pageable pageable);
}