package com.example.projeto.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Compra {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "Comanda")
    private User comanda;

    @Column(name = "Nome do cliente")
    private User name;

    @Column(name = "Nome do produto")
    private Product description;

    @Column(name = "Valor do produto")
    private Product value;

    @Column(name = "quantidade")
    private Product quantity;

    @Column(name = "Valor Total")
    private float valorfinalCompra;
}
