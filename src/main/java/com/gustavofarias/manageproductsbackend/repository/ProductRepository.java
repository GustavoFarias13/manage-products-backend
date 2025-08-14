package com.gustavofarias.manageproductsbackend.repository;

import com.gustavofarias.manageproductsbackend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Busca produtos pelo nome, ignorando maiúsculas/minúsculas.
     *
     * @param nome parte do nome do produto para busca.
     * @return lista de produtos encontrados.
     */
    List<Product> findByNomeContainingIgnoreCase(String nome);
}
