package com.gustavofarias.manageproductsbackend.controller;

import com.gustavofarias.manageproductsbackend.entity.Produto;
import com.gustavofarias.manageproductsbackend.service.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping
    public Produto criar(@RequestBody @Valid Produto produto) {
        return service.salvar(produto);
    }

    @GetMapping
    public List<Produto> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String ordemPreco) {
        return service.listar(nome, ordemPreco);
    }

    @GetMapping("/{id}")
    public Produto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @RequestBody @Valid Produto produto) {
        return service.atualizar(id, produto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
