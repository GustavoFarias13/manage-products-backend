package com.gustavofarias.manageproductsbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // só leitura no JSON
    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    private String descricao;

    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser maior que zero")
    @Column(nullable = false)
    private Double preco;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @PositiveOrZero(message = "Quantidade deve ser zero ou positiva")
    @Column(nullable = false)
    private Integer quantidadeEstoque;

    @PastOrPresent
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // só leitura no JSON
    private LocalDateTime dataCriacao = LocalDateTime.now();
}
