package com.gustavofarias.manageproductsbackend.service;

import com.gustavofarias.manageproductsbackend.entity.Product;
import com.gustavofarias.manageproductsbackend.entity.SortOrder;
import com.gustavofarias.manageproductsbackend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product save(Product product) {
        validateProduct(product);
        return repository.save(product);
    }

    public List<Product> getAll(String name, Double minPrice, Double maxPrice, SortOrder priceOrder) {
        // Filtra produtos pelo nome (se fornecido)
        List<Product> products = (name != null && !name.isBlank()) ?
                repository.findByNomeContainingIgnoreCase(name) :
                repository.findAll();

        // Filtra produtos por preço mínimo e máximo (se fornecido)
        products = products.stream()
                .filter(p -> (minPrice == null || p.getPreco() >= minPrice))
                .filter(p -> (maxPrice == null || p.getPreco() <= maxPrice))
                .toList();

        // Ordena por preço se solicitado
        if (priceOrder != null) {
            if (priceOrder == SortOrder.ASC) {
                products.sort(Comparator.comparing(Product::getPreco));
            } else if (priceOrder == SortOrder.DESC) {
                products.sort(Comparator.comparing(Product::getPreco).reversed());
            }
        }

        return products;
    }

    public Product getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado com id: " + id));
    }

    public Product update(Long id, Product product) {
        Product existing = getById(id);
        existing.setNome(product.getNome());
        existing.setDescricao(product.getDescricao());
        existing.setPreco(product.getPreco());
        existing.setQuantidadeEstoque(product.getQuantidadeEstoque());
        return repository.save(existing);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Produto não encontrado com id: " + id);
        }
        repository.deleteById(id);
    }

    private void validateProduct(Product product) {
        if (product.getPreco() == null || product.getPreco() <= 0) {
            throw new RuntimeException("Preço deve ser maior que zero");
        }
        if (product.getQuantidadeEstoque() == null || product.getQuantidadeEstoque() < 0) {
            throw new RuntimeException("Quantidade em estoque inválida");
        }
        if (product.getNome() == null || product.getNome().isBlank()) {
            throw new RuntimeException("Nome é obrigatório");
        }
    }
}
