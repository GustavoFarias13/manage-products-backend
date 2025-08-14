package com.gustavofarias.manageproductsbackend.service;

import com.gustavofarias.manageproductsbackend.entity.Produto;
import com.gustavofarias.manageproductsbackend.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public Produto salvar(Produto produto) {
        if (produto.getPreco() <= 0) throw new RuntimeException("Preço deve ser maior que zero");
        if (produto.getQuantidadeEstoque() < 0) throw new RuntimeException("Quantidade inválida");
        return repository.save(produto);
    }

    public List<Produto> listar(String nome, String ordemPreco) {
        List<Produto> produtos = (nome != null) ?
                repository.findByNomeContainingIgnoreCase(nome) :
                repository.findAll();

        if ("asc".equalsIgnoreCase(ordemPreco)) {
            produtos.sort((a, b) -> Double.compare(a.getPreco(), b.getPreco()));
        } else if ("desc".equalsIgnoreCase(ordemPreco)) {
            produtos.sort((a, b) -> Double.compare(b.getPreco(), a.getPreco()));
        }

        return produtos;
    }

    public Produto buscarPorId(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    public Produto atualizar(Long id, Produto produto) {
        Produto existente = buscarPorId(id);
        existente.setNome(produto.getNome());
        existente.setDescricao(produto.getDescricao());
        existente.setPreco(produto.getPreco());
        existente.setQuantidadeEstoque(produto.getQuantidadeEstoque());
        return repository.save(existente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
